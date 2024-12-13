package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.util.Locale

object ConversionGenerator {
	private val factoryAlternatives: Map<ConnectionType, ConnectionType> = mapOf(
		CollectionTypes.COLLECTION to CollectionTypes.LIST,
		CollectionTypes.SEQUENCED_COLLECTION to CollectionTypes.LIST,
		CollectionTypes.SORTED_NAVIGABLE_SET to CollectionTypes.NAVIGABLE_SET,
		MapTypes.SORTED_NAVIGABLE_MAP to MapTypes.NAVIGABLE_MAP
	)

	private val sortedTypes = setOf(
		CollectionTypes.SORTED_NAVIGABLE_SET,
		CollectionTypes.NAVIGABLE_SET,
		MapTypes.SORTED_NAVIGABLE_MAP,
		MapTypes.NAVIGABLE_MAP
	)

	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		environment.codeGenerator.generateView(resolver)
		environment.codeGenerator.generateRemoveOnly(resolver)
		environment.codeGenerator.generateMutable(resolver)
		environment.codeGenerator.generateSnapshot(resolver)
	}

	private fun CodeGenerator.generateView(resolver: Resolver) {
		val viewCollections = (CollectionTypes.entries + MapTypes.entries).asSequence()
			.map { ConnectionTypeKind(it, ConnectionKind.VIEW) }
			.filter { it !in ConnectionTypeKind.nonExistent }
			.toList()
		val dependencies = viewCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("View", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			viewCollections.forEach {
				out += """
					
					/**
					 * Returns a collection view, converted from this collection.
					 */
					 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asView(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					
				""".trimIndent()
			}
		}
	}

	private fun CodeGenerator.generateRemoveOnly(resolver: Resolver) {
		val removeOnlyCollections = CollectionTypes.entries.asSequence()
			.map { ConnectionTypeKind(it, ConnectionKind.REMOVE_ONLY) }
			.filter { it !in ConnectionTypeKind.nonExistent }
			.toList()
		val dependencies = removeOnlyCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("RemoveOnly", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			removeOnlyCollections.forEach {
				out += """
					
					/**
					 * Returns a remove-only collection, converted from this collection.
					 */
					 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asRemoveOnly(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					 
				""".trimIndent()
			}
		}
	}

	private fun CodeGenerator.generateMutable(resolver: Resolver) {
		val immutableToMutable = (CollectionTypes.entries + MapTypes.entries).associateBy(
			{ ConnectionTypeKind(it, ConnectionKind.IMMUTABLE) },
			{ ConnectionTypeKind(it, ConnectionKind.MUTABLE) }
		).filter { it.key !in ConnectionTypeKind.nonExistent && it.value !in ConnectionTypeKind.nonExistent }
		val dependencies = immutableToMutable.asSequence()
			.flatMap { sequenceOf(it.key, it.value) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("Mutable", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += "package io.github.spacedvoid.connection\n"
			immutableToMutable.forEach { (from, to) ->
				val conversionName = "mutable${(factoryAlternatives[to.type] ?: to.type).typeName}Of"
				val optionalComparator = if(from.type in sortedTypes) "this.comparator, " else ""
				out += """
					
					/**
					 * Returns a new mutable collection, copying the elements from this collection.
					 */
					 fun ${from.typeParamDeclaration()} ${from.fullIdentifier}.toMutable(): ${to.fullIdentifier} = $conversionName($optionalComparator*toGenericArray())
					 
				""".trimIndent()
			}
		}
	}

	private fun CodeGenerator.generateSnapshot(resolver: Resolver) {
		val mutableToImmutable = (CollectionTypes.entries + MapTypes.entries).associateBy(
			{ ConnectionTypeKind(it, ConnectionKind.VIEW) },
			{ ConnectionTypeKind(it, ConnectionKind.IMMUTABLE) }
		)
		val dependencies = mutableToImmutable.asSequence()
			.flatMap { sequenceOf(it.key, it.value) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("Snapshot", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += "package io.github.spacedvoid.connection\n"
			mutableToImmutable.forEach { (from, to) ->
				val conversionName = (factoryAlternatives[to.type] ?: to.type).typeName.replaceFirstChar { it.lowercase(Locale.US) } + "Of"
				val optionalComparator = if(from.type in sortedTypes) "this.comparator, " else ""
				out += """
					
					/**
					 * Returns a new immutable collection, copying the elements from this collection.
					 */
					 fun ${from.typeParamDeclaration()} ${from.fullIdentifier}.snapshot(): ${to.fullIdentifier} = $conversionName($optionalComparator*toGenericArray())
					 
				""".trimIndent()
			}
			out += """
				
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
				
			""".trimIndent()
		}
	}
}
