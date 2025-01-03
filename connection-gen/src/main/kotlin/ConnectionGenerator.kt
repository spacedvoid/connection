package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
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
			val from = type.kinds[ConnectionKind.VIEW] ?: return@let
			val view = when(val requested = it.to) {
				null -> from
				else -> requested.kinds[ConnectionKind.VIEW] ?: throw IllegalArgumentException("Type ${requested.name} does not have a view kind")
			}
			generatingFiles.view.attach(
				sequenceOf(from.qualifiedName, view.qualifiedName, view.impl.qualifiedName)
					.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
					.mapNotNull { it.containingFile }
					.toList()
			)
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
			val from = type.kinds[ConnectionKind.REMOVE_ONLY] ?: return@let
			val removeOnly = when(val requested = it.to) {
				null -> from
				else -> requested.kinds[ConnectionKind.REMOVE_ONLY] ?: throw IllegalArgumentException("Type ${requested.name} does not have a remove-only kind")
			}
			generatingFiles.removeOnly.attach(
				sequenceOf(from.qualifiedName, removeOnly.qualifiedName, removeOnly.impl.qualifiedName)
					.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
					.mapNotNull { it.containingFile }
					.toList()
			)
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
			(kind.adapters.asConnection.extra + kind.adapters.asConnection.default).forEach next@{
				if(it == null) return@next
				generatingFiles.colAsCon.attach(
					sequenceOf(kind.qualifiedName, kind.impl.qualifiedName)
						.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
						.mapNotNull { it.containingFile }
						.toList()
				)
				generatingFiles.generateColAsCon(
					it.name ?: when(kind.kind) {
						ConnectionKind.VIEW -> "asViewConnection"
						ConnectionKind.IMMUTABLE -> "asConnection"
						ConnectionKind.REMOVE_ONLY -> "asRemoveOnlyConnection"
						ConnectionKind.MUTABLE -> "asMutableConnection"
					},
					it.docs ?: when(kind.kind) {
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
					},
					type.typeArgs,
					it.kotlin ?: kotlin,
					kind.name,
					kind.impl.name
				)
			}
			// ConnectionAsCollection
			(kind.adapters.asKotlin.extra + kind.adapters.asKotlin.default).forEach next@{
				if(it == null) return@next
				generatingFiles.conAsCol.attach(
					sequenceOf(kind.qualifiedName, kind.impl.qualifiedName)
						.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
						.mapNotNull { it.containingFile }
						.toList()
				)
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
}

val ConnectionGeneration.ConnectionType.typeArgs: String
	get() = when(this.typeArgCount) {
		1 -> "<T>"
		2 -> "<K, V>"
		else -> throw IllegalStateException("Undefined type argument count ${this.typeArgCount}")
	}
