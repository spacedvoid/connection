package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.util.Locale

object ConversionGenerator {
	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		val factoryAlternatives: Map<ConnectionType, ConnectionType> = mapOf(
			CollectionTypes.SORTED_NAVIGABLE_SET to CollectionTypes.NAVIGABLE_SET,
			MapTypes.SORTED_NAVIGABLE_MAP to MapTypes.NAVIGABLE_MAP
		)
		val sortedTypes = setOf(
			CollectionTypes.SORTED_NAVIGABLE_SET,
			CollectionTypes.NAVIGABLE_SET,
			MapTypes.SORTED_NAVIGABLE_MAP,
			MapTypes.NAVIGABLE_MAP
		)
		environment.codeGenerator.generateView()
		environment.codeGenerator.generateRemoveOnly()
		environment.codeGenerator.generateMutable(factoryAlternatives, sortedTypes)
		environment.codeGenerator.generateSnapshot(factoryAlternatives, sortedTypes)
	}

	private fun CodeGenerator.generateView() {
		createNewFile("View").use { out ->
			out.write("""
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent())
			(CollectionTypes.entries + MapTypes.entries).asSequence()
				.map { ConnectionTypeKind(it, ConnectionKind.VIEW) }
				.forEach {
					out.write("""
						
						/**
						 * Returns a collection view, converted from this collection.
						 */
						 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asView(): ${it.fullIdentifier} = ${it.implName}(this.kotlin)
						 
					""".trimIndent())
				}
		}
	}

	private fun CodeGenerator.generateRemoveOnly() {
		createNewFile("RemoveOnly").use { out ->
			out.write("""
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent())
			CollectionTypes.entries.asSequence()
				.map { ConnectionTypeKind(it, ConnectionKind.REMOVE_ONLY) }
				.filter { it !in ConnectionTypeKind.nonExistent }
				.forEach {
					out.write("""
						
						/**
						 * Returns a remove-only collection, converted from this collection.
						 */
						 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asRemoveOnly(): ${it.fullIdentifier} = ${it.implName}(this.kotlin)
						 
					""".trimIndent())
				}
		}
	}

	private fun CodeGenerator.generateMutable(factoryAlternatives: Map<ConnectionType, ConnectionType>, sortedTypes: Set<Any>) {
		createNewFile("Mutable").use { out ->
			out.write("package io.github.spacedvoid.connection\n")
			(CollectionTypes.entries + MapTypes.entries)
				.associateBy(
					{ ConnectionTypeKind(it, ConnectionKind.IMMUTABLE) },
					{ ConnectionTypeKind(it, ConnectionKind.MUTABLE) }
				).forEach { (from, to) ->
					val conversionName = "mutable${(if(to.type in factoryAlternatives) factoryAlternatives.getValue(to.type) else to.type).typeName}Of"
					out.write("""
						
						/**
						 * Returns a new mutable collection, copying the elements from this collection.
						 */
						 fun ${from.typeParamDeclaration()} ${from.fullIdentifier}.toMutable(): ${to.fullIdentifier} = $conversionName(${if(from.type in sortedTypes) "this.comparator, " else ""}*toGenericArray())
						 
					""".trimIndent())
				}
		}
	}

	private fun CodeGenerator.generateSnapshot(factoryAlternatives: Map<ConnectionType, ConnectionType>, sortedTypes: Set<Any>) {
		createNewFile("Snapshot").use { out ->
			out.write("package io.github.spacedvoid.connection\n")
			(CollectionTypes.entries + MapTypes.entries)
				.associateBy(
					{ ConnectionTypeKind(it, ConnectionKind.VIEW) },
					{ ConnectionTypeKind(it, ConnectionKind.IMMUTABLE) }
				).forEach { (from, to) ->
					val conversionName = when(to.type) {
						in factoryAlternatives -> factoryAlternatives.getValue(to.type)
						else -> to.type
					}.typeName.replaceFirstChar { it.lowercase(Locale.US) } + "Of"
					out.write(
						"""
						
						/**
						 * Returns a new immutable collection, copying the elements from this collection.
						 */
						 fun ${from.typeParamDeclaration()} ${from.fullIdentifier}.snapshot(): ${to.fullIdentifier} = $conversionName(${if(from.type in sortedTypes) "this.comparator, " else ""}*toGenericArray())
						 
					""".trimIndent()
					)
				}
			out.write("""
				
				@Suppress("UNCHECKED_CAST")
				internal fun <T> CollectionView<T>.toGenericArray(): Array<T> {
					val iterator = this.kotlin.iterator()
					return this.size().let { size ->
						Array<Any?>(size) {
							try {
								if(size != size()) throw concurrentModification()
								return@Array iterator.next()
							}
							catch(e: NoSuchElementException) {
								throw concurrentModification(e)
							}
						} as Array<T>
					}
				}
				
				internal fun <K, V> MapView<K, V>.toGenericArray(): Array<Pair<K, V>> {
					val iterator = this.kotlin.iterator()
					return this.size().let { size ->
						Array(size) {
							try {
								if(size != size()) throw concurrentModification()
								return@Array iterator.next().let { it.key to it.value }
							}
							catch(e: NoSuchElementException) {
								throw concurrentModification(e)
							}
						}
					}
				}
				
				private fun concurrentModification(cause: Throwable? = null): ConcurrentModificationException =
					ConcurrentModificationException("Failed to snapshot mutating collection", cause)
				
			""".trimIndent())
		}
	}
}
