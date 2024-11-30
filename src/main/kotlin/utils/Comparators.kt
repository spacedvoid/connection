package io.github.spacedvoid.connection.utils

@Suppress("UNCHECKED_CAST")
fun <T> naturalOrdering(): Comparator<T> = NaturalOrdering as Comparator<T>

private object NaturalOrdering : Comparator<Any> {
	@Suppress("UNCHECKED_CAST")
	override fun compare(o1: Any, o2: Any): Int {
		if(o1 !is Comparable<*> || o2 !is Comparable<*>) throw TypeCastException("Non-comparable objects: $o1 and $o2")
		return (o1 as Comparable<Any>).compareTo(o2)
	}
}
