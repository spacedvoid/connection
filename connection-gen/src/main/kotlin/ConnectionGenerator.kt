/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration.ConnectionType.ConnectionTypeKind
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind
import io.github.spacedvoid.connection.gen.dsl.DslInternal
import io.github.spacedvoid.connection.gen.dsl.qualifiedName

/**
 * The [SymbolProcessorProvider] for [ConnectionGenerator].
 */
class ConnectionGeneratorProvider: SymbolProcessorProvider {
	override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = ConnectionGenerator(environment)
}

/**
 * The main generator.
 */
@OptIn(DslInternal::class)
class ConnectionGenerator(private val environment: SymbolProcessorEnvironment): SymbolProcessor {
	override fun process(resolver: Resolver): List<KSAnnotated> {
		if(resolver.getAllFiles().find { it.fileName == "CollectionAsConnection.kt" } != null) return listOf()
		val generation = ConnectionGeneration()
		generation.collect()
		GeneratingFiles(this.environment.codeGenerator).use { files ->
			generation.connections.values.forEach { generatePerType(files, resolver, it) }
		}
		return listOf()
	}

	/**
	 * Generates sources based on the [collected][collect] Connection [type].
	 */
	private fun generatePerType(generatingFiles: GeneratingFiles, resolver: Resolver, type: ConnectionGeneration.ConnectionType) {
		// Conversions
		// View
		type.conversions.view?.let {
			val (from, view) = conversionTarget(type, ConnectionKind.VIEW, it.to) ?: return@let
			sequenceOf(from.qualifiedName, view.qualifiedName, view.impl.qualifiedName)
				.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
				.mapNotNull { it.containingFile }
				.toList()
				.let { generatingFiles.view.attach(it) }
			generatingFiles.generateView(
				it.name ?: "asView",
				it.docs ?: """
					/**
					 * Returns a collection view, converted from this collection.
					 */
				""".trimIndent(),
				type.typeArgs,
				from.name,
				view.name,
				view.impl.name
			)
		}
		// RemoveOnly
		type.conversions.removeOnly?.let {
			val (from, removeOnly) = conversionTarget(type, ConnectionKind.REMOVE_ONLY, it.to) ?: return@let
			sequenceOf(from.qualifiedName, removeOnly.qualifiedName, removeOnly.impl.qualifiedName)
				.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
				.mapNotNull { it.containingFile }
				.toList()
				.let { generatingFiles.removeOnly.attach(it) }
			generatingFiles.generateRemoveOnly(
				it.name ?: "asRemoveOnly",
				it.docs ?: """
					/**
					 * Returns a remove-only collection, converted from this collection.
					 */
				""".trimIndent(),
				type.typeArgs,
				from.name,
				removeOnly.name,
				removeOnly.impl.name
			)
		}
		// Adapters
		type.kinds.values.forEach { kind ->
			val kindClass = resolver.getClassDeclarationByName(kind.qualifiedName) ?: throw IllegalArgumentException("Class ${kind.qualifiedName} does not exist")
			val kotlin = kindClass.getAllProperties()
				.find { it.simpleName.asString() == "kotlin" }
				?.type
				?.resolve()
				?.declaration
				?.qualifiedName
				?.asString()
				?: throw IllegalArgumentException("Kotlin property of class ${kind.qualifiedName} cannot be resolved")
			// CollectionAsConnection
			(kind.adapters.asConnection.default + kind.adapters.asConnection.extra).forEach next@{
				if(it == null) return@next
				sequenceOf(kind.qualifiedName, kind.impl.qualifiedName)
					.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
					.mapNotNull { it.containingFile }
					.toList()
					.let { generatingFiles.colAsCon.attach(it) }
				generatingFiles.generateColAsCon(
					it.name ?: when(kind.kind) {
						ConnectionKind.VIEW -> "asViewConnection"
						ConnectionKind.IMMUTABLE -> "asConnection"
						ConnectionKind.REMOVE_ONLY -> "asRemoveOnlyConnection"
						ConnectionKind.MUTABLE -> "asMutableConnection"
					},
					it.docs ?: defaultColAsConDocs(kind.kind),
					type.typeArgs,
					it.kotlin ?: kotlin,
					kind.name,
					kind.impl.name
				)
			}
			// ConnectionAsCollection
			(kind.adapters.asKotlin.default + kind.adapters.asKotlin.extra).forEach next@{
				if(it == null) return@next
				val apiClass = resolver.getClassDeclarationByName(kind.qualifiedName) ?: throw IllegalArgumentException("Class ${kind.qualifiedName} does not exist")
				if(alreadyGenerated(apiClass, it.kotlin ?: kotlin) && it === kind.adapters.asKotlin.default) return@next
				val implClass = resolver.getClassDeclarationByName(kind.impl.qualifiedName) ?: throw IllegalArgumentException("Class ${kind.impl.qualifiedName} does not exist")
				generatingFiles.conAsCol.attach(listOfNotNull(apiClass.containingFile, implClass.containingFile))
				generatingFiles.generateConAsCol(
					it.name ?: "asKotlin",
					it.docs ?: """
						/**
						 * Returns a Kotlin collection that delegates to this collection.
						 */
					""".trimIndent(),
					type.typeArgs,
					it.kotlin ?: kotlin,
					kind.name,
					it.unchecked
				)
			}
		}
	}

	/**
	 * Returns the desired conversion as `(from, to)`, or `null` if no conversions should be generated.
	 */
	private fun conversionTarget(
		type: ConnectionGeneration.ConnectionType,
		kind: ConnectionKind,
		requested: ConnectionGeneration.ConnectionType?
	): Pair<ConnectionTypeKind, ConnectionTypeKind>? {
		val from = type.kinds[kind] ?: return null
		val view = when(requested) {
			null -> run {
				if(type.kinds.keys.none { it > kind }) return@conversionTarget null
				return@run from
			}
			else -> requested.kinds[kind] ?: throw IllegalArgumentException("Type ${requested.name} does not have the kind ${kind.name}")
		}
		return from to view
	}

	/**
	 * Default documentation for `asConnection` adapters.
	 */
	private fun defaultColAsConDocs(kind: ConnectionKind): String = when(kind) {
		ConnectionKind.VIEW -> """
			/**
			 * Returns a collection view that delegates to the Kotlin collection.
			 */
		""".trimIndent()
		ConnectionKind.IMMUTABLE -> """
			/**
			 * Returns an immutable collection that delegates to the Kotlin collection.
			 *
			 * @apiNote
			 * Do not use this extension unless it is absolutely sure that it is an immutable collection, or will never be modified elsewhere.
			 * The [Iterator] might throw [ConcurrentModificationException] if the collection is modified.
			 */
		""".trimIndent()
		ConnectionKind.REMOVE_ONLY -> """
			/**
			 * Returns a remove-only collection that delegates to the Kotlin collection.
			 *
			 * @apiNote	
			 * Do not use this extension unless it is absolutely sure that it is a remove-only collection.
			 * Some operations might throw [UnsupportedOperationException].
			 */
		""".trimIndent()
		ConnectionKind.MUTABLE -> """
			/**
			 * Returns a mutable collection that delegates to the Kotlin collection.
			 *
			 * @apiNote
			 * Do not use this extension unless it is absolutely sure that it is a mutable collection.
			 * Some operations might throw [UnsupportedOperationException].
			 */
		""".trimIndent()
	}

	private val generatedAsKotlin = mutableMapOf<String, MutableSet<KSClassDeclaration>>()

	/**
	 * Returns whether the `asKotlin` adapter is not required to be generated
	 * because another adapter that returns the same type but accepts a supertype has already been generated.
	 */
	private fun alreadyGenerated(apiClass: KSClassDeclaration, kotlin: String): Boolean {
		val sourceTypes = this.generatedAsKotlin.computeIfAbsent(kotlin) { mutableSetOf() }
		return apiClass.getAllSuperTypes().any { it.declaration in sourceTypes }.also { if(it) sourceTypes += apiClass }
	}

	private operator fun <T> T.plus(list: List<T>): List<T> = mutableListOf(this).apply { addAll(list) }
}

@OptIn(DslInternal::class)
val ConnectionGeneration.ConnectionType.typeArgs: String
	get() = when(this.typeArgCount) {
		1 -> "<T>"
		2 -> "<K, V>"
		else -> throw IllegalStateException("Undefined type argument count ${this.typeArgCount}")
	}
