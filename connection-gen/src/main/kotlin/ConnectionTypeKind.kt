package io.github.spacedvoid.connection.gen

data class ConnectionTypeKind(val type: ConnectionType, val kind: ConnectionKind) {
	companion object {
		val nonExistent: Set<ConnectionTypeKind> = setOf(
			ConnectionTypeKind(CollectionTypes.LIST, ConnectionKind.REMOVE_ONLY),
			ConnectionTypeKind(MapTypes.MAP, ConnectionKind.REMOVE_ONLY),
			ConnectionTypeKind(MapTypes.SEQUENCED_MAP, ConnectionKind.REMOVE_ONLY),
			ConnectionTypeKind(MapTypes.SORTED_NAVIGABLE_MAP, ConnectionKind.REMOVE_ONLY),
			ConnectionTypeKind(MapTypes.NAVIGABLE_MAP, ConnectionKind.REMOVE_ONLY)
		)
	}
}

sealed interface ConnectionType {
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
	IMMUTABLE({ it }), VIEW({ "${it}View" }), REMOVE_ONLY({ "RemoveOnly$it" }), MUTABLE({ "Mutable$it" })
}

val ConnectionTypeKind.name
	get() = this.kind.typeNameTransformer(this.type.typeName)

val ConnectionTypeKind.implName: String
	get() = this.name + "Impl"

val ConnectionTypeKind.typeParams: List<String>
	get() = when(this.type is CollectionTypes) {
		true -> listOf("T")
		false -> listOf("K", "V")
	}

fun ConnectionTypeKind.typeParamDeclaration(comparable: Boolean = false): String =
	this.typeParams.asSequence()
		.mapIndexed { index, it -> if(index == 0 && comparable) "$it: Comparable<$it>" else it }
		.joinToString(prefix = "<", postfix = ">")

val ConnectionTypeKind.fullIdentifier: String
	get() = "${this.name}${this.typeParamDeclaration()}"
