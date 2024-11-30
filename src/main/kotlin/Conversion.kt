package io.github.spacedvoid.connection

/**
 * Returns an array that contains all elements copied from the collection.
 *
 * The order is defined as the encounter order.
 */
inline fun <reified T> Collection<T>.toTypedArray(): Array<T> {
	val iterator = iterator()
	return Array(size()) {
		iterator.next()
	}
}

/**
 * Returns an array that contains all entries copied from the map.
 *
 * The order is defined as the encounter order.
 */
fun <K, V> Map<K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = this.entries.iterator()
	return Array(this.size()) { iterator.next().let { it.key to it.value } }
}

/**
 * Returns a [SequencedCollection] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toSequencedCollection(): SequencedCollection<T> = if(this is SequencedCollection<T>) this else sequencedCollectionOf(*toGenericArray())

/**
 * Returns a [List] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toList(): List<T> = if(this is List<T>) this else listOf(*toGenericArray())

/**
 * Returns a [Set] converted from this collection.
 */
fun <T> Collection<T>.toSet(): Set<T> = if(this is Set<T>) this else setOf(*toGenericArray())

/**
 * Returns a [SequencedSet] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toSequencedSet(): SequencedSet<T> = if(this is SequencedSet<T>) this else sequencedSetOf(*toGenericArray())

/**
 * Returns a [NavigableSet] converted from this collection, using the [natural order][naturalOrder].
 */
fun <T: Comparable<T>> Collection<T>.toNavigableSet(): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(*toGenericArray())

/**
 * Returns a [NavigableSet] converted from this collection, using the [comparator].
 */
fun <T> Collection<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(comparator, *toGenericArray())

/**
 * Returns a [SequencedMap] converted from this map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> Map<K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else sequencedMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [natural order][naturalOrder].
 */
fun <K: Comparable<K>, V> Map<K, V>.toNavigableMap(): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [comparator].
 */
fun <K, V> Map<K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(comparator, *toArray())

/**
 * Returns a [MutableCollection] converted from this collection.
 */
fun <T> Collection<T>.toMutableCollection(): MutableCollection<T> = mutableCollectionOf(*toGenericArray())

/**
 * Returns a [MutableSequencedCollection] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toMutableSequencedCollection(): MutableSequencedCollection<T> = mutableSequencedCollectionOf(*toGenericArray())

/**
 * Returns a [MutableList] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toMutableList(): MutableList<T> = mutableListOf(*toGenericArray())

/**
 * Returns a [MutableSet] converted from this collection.
 */
fun <T> Collection<T>.toMutableSet(): MutableSet<T> = mutableSetOf(*toGenericArray())

/**
 * Returns a [MutableSequencedSet] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Collection<T>.toMutableSequencedSet(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

/**
 * Returns a [MutableNavigableSet] converted from this collection, using the [natural ordering][naturalOrder].
 */
fun <T: Comparable<T>> Collection<T>.toMutableNavigableSet(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

/**
 * Returns a [MutableNavigableSet] converted from this collection, using the given [comparator].
 */
fun <T> Collection<T>.toMutableNavigableSet(comparator: Comparator<in T>): MutableNavigableSet<T> = mutableNavigableSetOf(comparator, *toGenericArray())

/**
 * Returns a [MutableMap] converted from this map.
 */
fun <K, V> Map<K, V>.toMutableMap(): MutableMap<K, V> = mutableMapOf(*toArray())

/**
 * Returns a [MutableSequencedMap] converted from this map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> Map<K, V>.toMutableSequencedMap(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toArray())

/**
 * Returns a [MutableNavigableMap] converted from this map, using the [natural ordering][naturalOrder].
 */
fun <K: Comparable<K>, V> Map<K, V>.toMutableNavigableMap(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toArray())

/**
 * Returns a [MutableNavigableMap] converted from this map, using the [comparator].
 */
fun <K, V> Map<K, V>.toMutableNavigableMap(comparator: Comparator<in K>): MutableNavigableMap<K, V> = mutableNavigableMapOf(comparator, *toArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> Collection<T>.toGenericArray(): Array<T> {
	val iterator = this.iterator()
	return Array<Any?>(this.size()) { iterator.next() } as Array<T>
}
