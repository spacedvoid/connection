/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.spacedvoid.connection.gen.dsl.Adapter
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration.ConnectionType.ConnectionTypeKind
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind
import io.github.spacedvoid.connection.gen.dsl.Conversion
import io.github.spacedvoid.connection.gen.dsl.DslInternal
import io.github.spacedvoid.connection.gen.dsl.KotlinType
import io.github.spacedvoid.connection.gen.dsl.qualifiedName

object ConversionAdapterGenerator {
	fun generate(resolver: Resolver, files: GeneratingFiles, collected: ConnectionGeneration) {
		ConversionsAndAdapters(files).use { files ->
			collected.connections.values.forEach { generatePerType(files, resolver, it) }
		}
	}

	/**
	 * Generates sources based on the [collected][collect] Connection [type].
	 */
	private fun generatePerType(files: ConversionsAndAdapters, resolver: Resolver, type: ConnectionGeneration.ConnectionType) {
		// Conversions
		// View
		type.conversions.view?.let {
			val (from, view) = conversionTarget(type, ConnectionKind.VIEW) ?: return@let
			files.view.attachSources(resolver, from.qualifiedName, view.qualifiedName)
			files.view.generateView(it, type.typeArgs, from.name, view.name)
		}
		// RemoveOnly
		type.conversions.removeOnly?.let {
			val (from, removeOnly) = conversionTarget(type, ConnectionKind.REMOVE_ONLY) ?: return@let
			files.removeOnly.attachSources(resolver, from.qualifiedName, removeOnly.qualifiedName)
			files.removeOnly.generateRemoveOnly(it, type.typeArgs, from.name, removeOnly.name)
		}
		type.kinds.values.forEach { kind: ConnectionTypeKind ->
			// Adapters
			// CollectionAsConnection
			(kind.adapters.asConnection.default + kind.adapters.asConnection.extra).forEach {
				if(it == null) return@forEach
				val kotlin = it.kotlin ?: kind.kotlin ?: throw IllegalArgumentException("`kotlin` not set for ColAsCon adapter in $it")
				files.colAsCon.attachSources(resolver, kind.qualifiedName)
				files.colAsCon.generateColAsCon(it, kind, kotlin)
			}
			// ConnectionAsCollection
			(kind.adapters.asKotlin.default + kind.adapters.asKotlin.extra).forEach {
				if(it == null) return@forEach
				val kotlin = it.kotlin ?: kind.kotlin ?: throw IllegalArgumentException("`kotlin` not set for ConAsCol adapter in $it")
				val apiClass = resolver.getClassDeclarationByName(kind.qualifiedName) ?: throw IllegalArgumentException("Class ${kind.qualifiedName} does not exist")
				if(alreadyGenerated(apiClass, kotlin) && it === kind.adapters.asKotlin.default) return@forEach
				apiClass.containingFile?.let { files.conAsCol.attach(it) }
				files.conAsCol.generateConAsCol(it, kind, kotlin, it.unchecked)
			}
		}
	}

	private class ConversionsAndAdapters(generator: GeneratingFiles): AutoCloseable {
		val view: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "View")

		val removeOnly: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "RemoveOnly")

		val colAsCon: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "CollectionAsConnection").also {
			it += """
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
		}

		val conAsCol: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "ConnectionAsCollection").also {
			it += """
				
				import io.github.spacedvoid.connection.impl.*
				import io.github.spacedvoid.connection.impl.kotlin.*
				
			""".trimIndent()
		}

		override fun close() {
			this.view.close()
			this.removeOnly.close()
			this.colAsCon.close()
			this.conAsCol.close()
		}
	}

	/**
	 * Returns the desired conversion as `(from, to)`, or `null` if no conversions should be generated.
	 *
	 * This method is used when conversions that change the kind (maintaining the type) are not needed,
	 * such as the requested [kind] is [ConnectionKind.MUTABLE], or the target [kind] is not present on the [type].
	 * In most cases, the returned pair is just `{`[type][type]`, `[type][type]`}`.
	 */
	private fun conversionTarget(
		type: ConnectionGeneration.ConnectionType,
		kind: ConnectionKind
	): Pair<ConnectionTypeKind, ConnectionTypeKind>? {
		val from = type.kinds[kind] ?: return null
		val view = if(type.kinds.keys.any { it > kind }) from else return null
		return from to view
	}

	private val defaultViewDoc = """
		/**
		 * Returns a collection view, converted from this collection.
		 */
	""".trimIndent()

	private val defaultRemoveOnlyDoc = """
		/**
		 * Returns a remove-only collection, converted from this collection.
		 */
	""".trimIndent()

	private val defaultConAsColDocs = """
		/**
		 * Returns a Kotlin collection that delegates to this collection.
		 */
	""".trimIndent()

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

	private fun defaultColAsConName(kind: ConnectionKind) = when(kind) {
		ConnectionKind.VIEW -> "asViewConnection"
		ConnectionKind.IMMUTABLE -> "asConnection"
		ConnectionKind.REMOVE_ONLY -> "asRemoveOnlyConnection"
		ConnectionKind.MUTABLE -> "asMutableConnection"
	}

	private val generatedAsKotlin = mutableMapOf<String, MutableSet<KSClassDeclaration>>()

	/**
	 * Returns whether the `asKotlin` adapter is not required to be generated
	 * because another adapter that returns the same type but accepts a supertype has already been generated.
	 */
	private fun alreadyGenerated(apiClass: KSClassDeclaration, kotlin: KotlinType): Boolean {
		val sourceTypes = this.generatedAsKotlin.computeIfAbsent(kotlin.qualifiedName) { mutableSetOf() }
		return apiClass.getAllSuperTypes().any { it.declaration in sourceTypes }.also { if(!it) sourceTypes += apiClass }
	}

	private operator fun <T> T.plus(list: List<T>): List<T> = mutableListOf(this).apply { addAll(list) }

	@DslInternal
	val ConnectionGeneration.ConnectionType.typeArgs: String
		get() = when(this.typeArgCount) {
			1 -> "<T>"
			2 -> "<K, V>"
			else -> throw IllegalStateException("Undefined type argument count ${this.typeArgCount}")
		}

	/**
	 * Shortcut for generating Kotlin collection to Connection adapters.
	 */
	fun GeneratingFiles.GeneratingFile.generateColAsCon(adapter: Adapter, kind: ConnectionTypeKind, kotlin: KotlinType) {
		check(!this.closed)
		val typeParams = kind.type.typeArgs
		this += "\n"
		this += adapter.docs ?: defaultColAsConDocs(kind.kind)
		this += "\nfun $typeParams ${kotlin.qualifiedName}$typeParams.${adapter.name ?: defaultColAsConName(kind.kind)}(): ${kind.name}$typeParams = ${kind.impl.name}(this)\n"
	}

	/**
	 * Shortcut for generating Connection to Kotlin collection adapters.
	 *
	 * [unchecked] controls whether the collection object needs unchecked casting.
	 * Setting it to `true` additionally inserts `@Suppress("UNCHECKED_CAST")` and a cast to the [kotlin] type.
	 */
	fun GeneratingFiles.GeneratingFile.generateConAsCol(adapter: Adapter, kind: ConnectionTypeKind, kotlin: KotlinType, unchecked: Boolean) {
		check(!this.closed)
		val typeParams = kind.type.typeArgs
		this += "\n"
		this += adapter.docs ?: defaultConAsColDocs
		if(unchecked) this += "\n@Suppress(\"UNCHECKED_CAST\")"
		this += "\nfun $typeParams ${kind.name}$typeParams.${adapter.name ?: "asKotlin"}(): ${kotlin.qualifiedName}$typeParams = if(this is ${kind.impl.name}$typeParams) this.kotlin "
		if(unchecked) this += "as ${kotlin.qualifiedName}$typeParams "
		this += "else ${kotlin.impl.name}(this)\n"
	}

	/**
	 * Shortcut for generating view conversions.
	 */
	fun GeneratingFiles.GeneratingFile.generateView(conversion: Conversion, typeParams: String, from: String, to: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultViewDoc
		this += "\nfun $typeParams $from$typeParams.${conversion.name ?: "asView"}(): $to$typeParams = object: $to$typeParams by this {}\n"
	}

	/**
	 * Shortcut for generating remove-only conversions.
	 */
	fun GeneratingFiles.GeneratingFile.generateRemoveOnly(conversion: Conversion, typeParams: String, from: String, to: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultRemoveOnlyDoc
		this += "\nfun $typeParams $from$typeParams.${conversion.name ?: "asRemoveOnly"}(): $to$typeParams = object: $to$typeParams by this {}\n"
	}
}
