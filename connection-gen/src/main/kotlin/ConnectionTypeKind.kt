package io.github.spacedvoid.connection.gen

import java.util.Locale
import java.util.Objects

data class AsConnectionAdapter(val docs: String, val customName: String? = null)

data class AsKotlinAdapter(val docs: String, val customName: String? = null, val uncheckedCast: UncheckedCast? = null)

data class UncheckedCast(val target: String)

interface TypeKind {
	val name: String
	val qualifiedName: String
}

class ConnectionTypeKind(val type: ConnectionType, val kind: ConnectionKind, val kotlin: String, val asConnectionAdapters: List<AsConnectionAdapter> = listOf(), val asKotlinAdapters: List<AsKotlinAdapter> = listOf()): TypeKind {
	companion object {
		val nonExistent: (ConnectionTypeKind) -> Boolean = lambda@{
			val types = setOf(CollectionTypes.LIST, *MapTypes.entries.toTypedArray())
			return@lambda it.type in types && it.kind == ConnectionKind.REMOVE_ONLY
		}

		val all: List<ConnectionTypeKind> = generateAllTypeKinds()

		operator fun get(type: ConnectionType, kind: ConnectionKind): ConnectionTypeKind? = all.find { it.type == type && it.kind == kind }
	}

	override val name: String = this.kind.typeNameTransformer(this.type.typeName)
	override val qualifiedName: String = "io.github.spacedvoid.connection." + this.name

	override fun equals(other: Any?): Boolean {
		if(other == null) return false
		if(other::class != ConnectionTypeKind::class) return false
		val that = other as ConnectionTypeKind
		return this.type == that.type && this.kind == that.kind
	}

	override fun hashCode(): Int = Objects.hash(this.type, this.kind)
}

data class ConnectionImpl(val typeKind: ConnectionTypeKind): TypeKind {
	override val name: String = this.typeKind.name + "Impl"
	override val qualifiedName: String = "io.github.spacedvoid.connection.impl." + this.name
}

sealed interface ConnectionType {
	companion object {
		val sortedTypes: Set<ConnectionType> = setOf(
			CollectionTypes.SORTED_NAVIGABLE_SET,
			CollectionTypes.NAVIGABLE_SET,
			MapTypes.SORTED_NAVIGABLE_MAP,
			MapTypes.NAVIGABLE_MAP
		)
	}

	val typeName: String
}

enum class CollectionTypes(override val typeName: String): ConnectionType {
	COLLECTION("Collection"), SEQUENCED_COLLECTION("SequencedCollection"), LIST("List"),
	SET("Set"), SEQUENCED_SET("SequencedSet"), SORTED_NAVIGABLE_SET("SortedNavigableSet"), NAVIGABLE_SET("NavigableSet")
}

enum class MapTypes(override val typeName: String): ConnectionType {
	MAP("Map"), SEQUENCED_MAP("SequencedMap"), SORTED_NAVIGABLE_MAP("SortedNavigableMap"), NAVIGABLE_MAP("NavigableMap")
}

enum class ConnectionKind(val typeNameTransformer: (String) -> String) {
	VIEW({ "${it}View" }), IMMUTABLE({ it }),REMOVE_ONLY({ "RemoveOnly$it" }), MUTABLE({ "Mutable$it" })
}

val ConnectionTypeKind.impl: ConnectionImpl
	get() = ConnectionImpl(this)

val ConnectionTypeKind.typeParams: String
	get() = when(this.type is CollectionTypes) {
		true -> "<T>"
		false -> "<K, V>"
	}

val ConnectionTypeKind.fullIdentifier: String
	get() = "${this.name}${this.typeParams}"

val ConnectionTypeKind.factory: String
	get() = when(this.kind) {
		ConnectionKind.IMMUTABLE -> (factoryAlternatives[this.type] ?: this.type).typeName.replaceFirstChar { it.lowercase(Locale.US) } + "Of"
		ConnectionKind.MUTABLE -> "mutable" + (factoryAlternatives[this.type] ?: this.type).typeName + "Of"
		else -> throw IllegalArgumentException("No factory method for $this")
	}

private val toConnectionAdapterDocs = mapOf(
	ConnectionKind.IMMUTABLE to """
		/**
		 * Returns an immutable collection that delegates to the Kotlin collection.
		 *
		 * @apiNote
		 * Do not use this extension unless it is absolutely sure that it is an immutable collection, or will never be modified elsewhere.
		 * The [Iterator] might throw [ConcurrentModificationException] if the collection is modified.
		 */
	""".trimIndent(),
	ConnectionKind.VIEW to """
		/**
		 * Returns a collection view that delegates to the Kotlin collection.
		 */
	""".trimIndent(),
	ConnectionKind.REMOVE_ONLY to """
		/**
		 * Returns a remove-only collection that delegates to the Kotlin collection.
		 *
		 * @apiNote	
		 * Do not use this extension unless it is absolutely sure that it is a remove-only collection.
		 * Some operations might throw [UnsupportedOperationException].
		 */
	""".trimIndent(),
	ConnectionKind.MUTABLE to """
		/**
		 * Returns a mutable collection that delegates to the Kotlin collection.
		 *
		 * @apiNote
		 * Do not use this extension unless it is absolutely sure that it is a mutable collection.
		 * Some operations might throw [UnsupportedOperationException].
		 */
	""".trimIndent()
)

private val toKotlinAdapterDoc = """
	/**
	 * Returns a Kotlin collection that delegates to this collection.
	 */
""".trimIndent()

private fun generateAllTypeKinds(): List<ConnectionTypeKind> = (CollectionTypes.entries + MapTypes.entries).asSequence()
	.flatMap { type -> ConnectionKind.entries.map { createConnectionTypeKind(type, it) } }
	.filterNot { ConnectionTypeKind.nonExistent(it) }
	.toList()

private fun createConnectionTypeKind(type: ConnectionType, kind: ConnectionKind) = ConnectionTypeKind(
	type,
	kind,
	when(type) {
		CollectionTypes.COLLECTION, CollectionTypes.LIST, CollectionTypes.SET, MapTypes.MAP -> when(kind) {
			ConnectionKind.IMMUTABLE, ConnectionKind.VIEW -> "kotlin.collections.${type.typeName}"
			else -> "kotlin.collections.Mutable${type.typeName}"
		}
		CollectionTypes.SORTED_NAVIGABLE_SET -> "java.util.SortedSet"
		MapTypes.SORTED_NAVIGABLE_MAP -> "java.util.SortedMap"
		else -> "java.util.${type.typeName}"
	},
	listOf(AsConnectionAdapter(toConnectionAdapterDocs.getValue(kind))),
	buildList {
		add(AsKotlinAdapter(toKotlinAdapterDoc))
		if(type == CollectionTypes.LIST && kind == ConnectionKind.VIEW) {
			add(AsKotlinAdapter("""
				/**
				 * Returns a Kotlin collection that delegates to this collection.
				 *
				 * @apiNote
				 * This extension exists since [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].
				 */
			""".trimIndent(), customName = "asSequencedKotlin", uncheckedCast = UncheckedCast("java.util.SequencedCollection")))
		}
	}
)

private val factoryAlternatives: Map<ConnectionType, ConnectionType> = mapOf(
	CollectionTypes.COLLECTION to CollectionTypes.LIST,
	CollectionTypes.SEQUENCED_COLLECTION to CollectionTypes.LIST,
	CollectionTypes.SORTED_NAVIGABLE_SET to CollectionTypes.NAVIGABLE_SET,
	MapTypes.SORTED_NAVIGABLE_MAP to MapTypes.NAVIGABLE_MAP
)
