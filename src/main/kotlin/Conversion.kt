package io.github.spacedvoid.connection

inline fun <reified T> Collection<T>.toTypedArray(): Array<T> {
	val iterator = iterator()
	return Array(size()) {
		iterator.next()
	}
}

fun <T> Collection<T>.toCollection(): Collection<T> = collectionOf(*toGenericArray())

fun <T> Collection<T>.toSequencedCollection(): SequencedCollection<T> = sequencedCollectionOf(*toGenericArray())

fun <T> Collection<T>.toList(): List<T> = listOf(*toGenericArray())

fun <T> Collection<T>.toSet(): Set<T> = setOf(*toGenericArray())

fun <T> Collection<T>.toSequencedSet(): SequencedSet<T> = sequencedSetOf(*toGenericArray())

fun <T: Comparable<T>> Collection<T>.toSortedSet(): SortedSet<T> = sortedSetOf(*toGenericArray())

fun <T> Collection<T>.toSortedSet(comparator: Comparator<in T>): SortedSet<T> = sortedSetOf(comparator, *toGenericArray())

fun <T: Comparable<T>> Collection<T>.toNavigableSet(): NavigableSet<T> = navigableSetOf(*toGenericArray())

fun <T> Collection<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> = navigableSetOf(comparator, *toGenericArray())

fun <K, V> Map<K, V>.toSequencedMap(): SequencedMap<K, V> = sequencedMapOf(*toGenericArray())

fun <K: Comparable<K>, V> Map<K, V>.toSortedMap(): SortedMap<K, V> = sortedMapOf(*toGenericArray())

fun <K, V> Map<K, V>.toSortedMap(comparator: Comparator<in K>): SortedMap<K, V> = sortedMapOf(comparator, *toGenericArray())

fun <K: Comparable<K>, V> Map<K, V>.toNavigableMap(): NavigableMap<K, V> = navigableMapOf(*toGenericArray())

fun <K, V> Map<K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> = navigableMapOf(comparator, *toGenericArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> Collection<T>.toGenericArray(): Array<T> {
	val iterator = this.iterator()
	return Array<Any?>(this.size()) { iterator.next() } as Array<T>
}

internal fun <K, V> Map<K, V>.toGenericArray(): Array<Pair<K, V>> {
	val iterator = this.entries.iterator()
	return Array(this.size()) { iterator.next().let { it.key to it.value } }
}
