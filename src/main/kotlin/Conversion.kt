package io.github.spacedvoid.connection

/**
 * Returns an array that contains all elements copied from the collection.
 *
 * The order is defined as the encounter order.
 */
inline fun <reified T> CollectionView<T>.toTypedArray(): Array<T> {
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
fun <K, V> MapView<K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = this.entries.iterator()
	return Array(this.size()) { iterator.next().let { it.key to it.value } }
}

/**
 * Returns a [List] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> CollectionView<T>.toList(): List<T> = if(this is List<T>) this else listOf(*toGenericArray())

/**
 * Returns a [Set] converted from this collection.
 */
fun <T> CollectionView<T>.toSet(): Set<T> = if(this is Set<T>) this else setOf(*toGenericArray())

/**
 * Returns a [SequencedSet] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> CollectionView<T>.toSequencedSet(): SequencedSet<T> = if(this is SequencedSet<T>) this else sequencedSetOf(*toGenericArray())

/**
 * Returns a [NavigableSet] converted from this collection, using the [natural order][naturalOrder].
 */
fun <T: Comparable<T>> CollectionView<T>.toNavigableSet(): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(*toGenericArray())

/**
 * Returns a [NavigableSet] converted from this collection, using the [comparator].
 */
fun <T> CollectionView<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> = if(this is NavigableSet<T>) this else navigableSetOf(comparator, *toGenericArray())

/**
 * Returns a [SequencedMap] converted from this map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> MapView<K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else sequencedMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [natural order][naturalOrder].
 */
fun <K: Comparable<K>, V> MapView<K, V>.toNavigableMap(): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [comparator].
 */
fun <K, V> MapView<K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> = if(this is NavigableMap<K, V>) this else navigableMapOf(comparator, *toArray())

/**
 * Returns a new [MutableList] copied from the collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> CollectionView<T>.toMutableList(): MutableList<T> = mutableListOf(*toGenericArray())

/**
 * Returns a new [MutableSet] copied from the collection.
 */
fun <T> CollectionView<T>.toMutableSet(): MutableSet<T> = mutableSetOf(*toGenericArray())

/**
 * Returns a new [MutableSequencedSet] copied from the collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> CollectionView<T>.toMutableSequencedSet(): MutableSequencedSet<T> = mutableSequencedSetOf(*toGenericArray())

/**
 * Returns a new [MutableNavigableSet] copied from the collection.
 *
 * The [natural order][naturalOrder] is used.
 */
fun <T: Comparable<T>> CollectionView<T>.toMutableNavigableSet(): MutableNavigableSet<T> = mutableNavigableSetOf(*toGenericArray())

/**
 * Returns a new [MutableNavigableSet] copied from the collection.
 *
 * The given [comparator] is used.
 */
fun <T> CollectionView<T>.toMutableNavigableSet(comparator: Comparator<in T>): MutableNavigableSet<T> = mutableNavigableSetOf(comparator, *toGenericArray())

/**
 * Returns a new [MutableMap] copied from the map.
 */
fun <K, V> MapView<K, V>.toMutableMap(): MutableMap<K, V> = mutableMapOf(*toArray())

/**
 * Returns a new [MutableSequencedMap] copied from the map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> MapView<K, V>.toMutableSequencedMap(): MutableSequencedMap<K, V> = mutableSequencedMapOf(*toArray())

/**
 * Returns a new [MutableNavigableMap] copied from the map.
 *
 * The [natural order][naturalOrder] is used.
 */
fun <K: Comparable<K>, V> MapView<K, V>.toMutableNavigableMap(): MutableNavigableMap<K, V> = mutableNavigableMapOf(*toArray())

/**
 * Returns a new [MutableNavigableMap] copied from the collection.
 *
 * The given [comparator] is used.
 */
fun <K, V> MapView<K, V>.toMutableNavigableMap(comparator: Comparator<in K>): MutableNavigableMap<K, V> = mutableNavigableMapOf(comparator, *toArray())

@Suppress("UNCHECKED_CAST")
internal fun <T> CollectionView<T>.toGenericArray(): Array<T> {
	val iterator = this.kotlin.iterator()
	return Array<Any?>(size()) { iterator.next() } as Array<T>
}
