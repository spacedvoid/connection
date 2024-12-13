package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

object AdapterGenerator {
	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		val collectionMap = (CollectionTypes.entries + MapTypes.entries).asSequence()
			.flatMap { type -> ConnectionKind.entries.map { ConnectionTypeKind(type, it) } }
			.filter { it !in ConnectionTypeKind.nonExistent }
			.associateWith {
				when(it.type) {
					CollectionTypes.COLLECTION, CollectionTypes.LIST, CollectionTypes.SET, MapTypes.MAP -> when(it.kind) {
						ConnectionKind.IMMUTABLE, ConnectionKind.VIEW -> "kotlin.collections.${it.type.typeName}"
						else -> "kotlin.collections.Mutable${it.type.typeName}"
					}

					CollectionTypes.SORTED_NAVIGABLE_SET -> "java.util.SortedSet"
					MapTypes.SORTED_NAVIGABLE_MAP -> "java.util.SortedMap"
					else -> "java.util.${it.type.typeName}"
				}
			}
		environment.codeGenerator.generateColAsCon(collectionMap)
		environment.codeGenerator.generateConAsCol(collectionMap)
	}

	private fun CodeGenerator.generateColAsCon(collectionMap: Map<ConnectionTypeKind, String>) {
		createNewFile("CollectionAsConnection").use { out ->
			out.write("""
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
			""")
			val history = mutableSetOf<ConnectionType>()
			collectionMap.entries.forEach { (typeKind, kotlin) ->
				if(history.add(typeKind.type)) out.write("\n// $kotlin -> ${typeKind.type.typeName}\n")
				out.write(
					when(typeKind.kind) {
						ConnectionKind.IMMUTABLE -> """
						
						/**
						 * Returns an immutable collection that delegates to the Kotlin collection.
						 *
						 * @apiNote
						 * Do not use this extension unless it is absolutely sure that it is an immutable collection, or will never be modified elsewhere.
						 * The [Iterator] might throw [ConcurrentModificationException] if the collection is modified.
						 */
						fun ${typeKind.typeParamDeclaration()} $kotlin${typeKind.typeParamDeclaration()}.asConnection(): ${typeKind.fullIdentifier} = ${typeKind.implName}(this)
						
					""".trimIndent()

						ConnectionKind.VIEW -> """
						
						/**
						 * Returns a collection view that delegates to the Kotlin collection.
						 */
						fun ${typeKind.typeParamDeclaration()} $kotlin${typeKind.typeParamDeclaration()}.asViewConnection(): ${typeKind.fullIdentifier} = ${typeKind.implName}(this)
						
					""".trimIndent()

						ConnectionKind.REMOVE_ONLY -> """
						
						/**
						 * Returns a remove-only collection that delegates to the Kotlin collection.
						 *
						 * @apiNote
						 * Do not use this extension unless it is absolutely sure that it is a remove-only collection.
						 * Some operations might throw [UnsupportedOperationException].
						 */
						fun ${typeKind.typeParamDeclaration()} $kotlin${typeKind.typeParamDeclaration()}.asRemoveOnlyConnection(): ${typeKind.fullIdentifier} = ${typeKind.implName}(this)
						
					""".trimIndent()

						ConnectionKind.MUTABLE -> """
						
						/**
						 * Returns a mutable collection that delegates to the Kotlin collection.
						 *
						 * @apiNote
						 * Do not use this extension unless it is absolutely sure that it is a mutable collection.
						 * Some operations might throw [UnsupportedOperationException].
						 */
						fun ${typeKind.typeParamDeclaration()} $kotlin${typeKind.typeParamDeclaration()}.${if(kotlin.startsWith("java")) "asMutableConnection" else "asConnection"}(): ${typeKind.fullIdentifier} = ${typeKind.implName}(this)
						
					""".trimIndent()
					}
				)
			}
		}
	}

	private fun CodeGenerator.generateConAsCol(collectionMap: Map<ConnectionTypeKind, String>) {
		createNewFile("ConnectionAsCollection").use { out ->
			out.write("package io.github.spacedvoid.connection\n")
			val history = mutableSetOf<ConnectionType>()
			collectionMap.entries.forEach { (typeKind, kotlin) ->
				if(history.add(typeKind.type)) {
					if(typeKind.type == CollectionTypes.SET) {
						out.write("\n// List -> java.util.SequencedCollection\n")
						listOf(
							ConnectionTypeKind(CollectionTypes.LIST, ConnectionKind.IMMUTABLE),
							ConnectionTypeKind(CollectionTypes.LIST, ConnectionKind.VIEW),
							ConnectionTypeKind(CollectionTypes.LIST, ConnectionKind.MUTABLE)
						).forEach {
							out.write(
								"""
								
								/**
								 * Returns a Kotlin collection that delegates to this collection.
								 *
								 * @apiNote
								 * This extension exists since [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].
								 */
								@Suppress("UNCHECKED_CAST")
								fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asSequencedKotlin(): java.util.SequencedCollection<T> = object: java.util.SequencedCollection<T> by this.kotlin as java.util.SequencedCollection<T> {}
								
							""".trimIndent()
							)
						}
					}
					out.write("\n// ${typeKind.type.typeName} -> $kotlin\n")
				}
				out.write(
					"""
					
					/**
					 * Returns a Kotlin collection that delegates to this collection.
					 */
					fun ${typeKind.typeParamDeclaration()} ${typeKind.fullIdentifier}.asKotlin(): $kotlin${typeKind.typeParamDeclaration()} = object: $kotlin${typeKind.typeParamDeclaration()} by this.kotlin {}
					
				""".trimIndent()
				)
			}
		}
	}

}
