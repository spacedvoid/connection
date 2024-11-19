package io.github.spacedvoid.connection

inline fun <reified T> Collection<T>.toTypedArray(): Array<T> {
	val iterator = iterator()
	return Array(size()) {
		iterator.next()
	}
}

fun <K, V> Map<K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = this.entries.iterator()
	return Array(this.size()) { iterator.next().let { it.key to it.value } }
}

fun <T> Collection<T>.toSequencedCollection(): SequencedCollection<T> = if(this is SequencedCollection<T>) this else sequencedCollectionOf(*toGenericArray())

fun <T> Collection<T>.toList(): List<T> = if(this is List<T>) this else listOf(*toGenericArray())

fun <T> Collection<T>.toSet(): Set<T> = if(this is Set<T>) this else setOf(*toGenericArray())

fun <T> Collection<T>.toSequencedSet(): SequencedSet<T> = if(this is SequencedSet<T>) this else sequencedSetOf(*toGenericArray())

fun <T: Comparable<T>> Collection<T>.toNavigableSet(): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(*toGenericArray())

fun <T> Collection<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(comparator, *toGenericArray())

fun <K, V> Map<K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else sequencedMapOf(*toArray())

fun <K: Comparable<K>, V> Map<K, V>.toNavigableMap(): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(*toArray())

fun <K, V> Map<K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(comparator, *toArray())

fun <T> Collection<T>.toMutableCollection(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

fun <T> Collection<T>.toMutableSequencedCollection(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

fun <T> Collection<T>.toMutableList(): MutableList<T> = mutableListOf(*toGenericArray())

fun <T> Collection<T>.toMutableSet(): MutableSet<T> = mutableSetOf(*toGenericArray())

fun <T> Collection<T>.toMutableSequencedSet(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

fun <T: Comparable<T>> Collection<T>.toMutableNavigableSet(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

fun <T> Collection<T>.toMutableNavigableSet(comparator: Comparator<in T>): MutableNavigableSet<T> = mutableNavigableSetOf(comparator, *toGenericArray())

fun <K, V> Map<K, V>.toMutableMap(): MutableMap<K, V> = mutableMapOf(*toArray())

fun <K, V> Map<K, V>.toMutableSequencedMap(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toArray())

fun <K: Comparable<K>, V> Map<K, V>.toMutableNavigableMap(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toArray())

fun <K, V> Map<K, V>.toMutableNavigableMap(comparator: Comparator<in K>): MutableNavigableMap<K, V> = mutableNavigableMapOf(comparator, *toArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> Collection<T>.toGenericArray(): Array<T> {
	val iterator = this.iterator()
	return Array<Any?>(this.size()) { iterator.next() } as Array<T>
}
