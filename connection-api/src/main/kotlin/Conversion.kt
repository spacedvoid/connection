/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("IfThenToElvis")

package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.utils.naturalOrdering
import io.github.spacedvoid.connection.utils.reverseOrdering

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
 *
 * If this set uses a different comparator than the natural order, this set is copied into a new set.
 */
fun <T: Comparable<T>> CollectionView<T>.toNavigableSet(): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isApparently naturalOrder<T>()) this else navigableSetOf(*toGenericArray())

/**
 * Returns a [NavigableSet] converted from this collection, using the [comparator].
 *
 * If this set uses a different comparator than the given one, this set is copied into a new set.
 */
fun <T> CollectionView<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isApparently comparator) this else navigableSetOf(comparator, *toGenericArray())

/**
 * Returns a [SequencedMap] converted from this map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> MapView<K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else sequencedMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [natural order][naturalOrder].
 *
 * If this map uses a different comparator than the natural order, this map is copied into a new map.
 */
fun <K: Comparable<K>, V> MapView<K, V>.toNavigableMap(): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isApparently naturalOrder<K>()) this else navigableMapOf(*toArray())

/**
 * Returns a [NavigableMap] converted from this map, using the [comparator].
 *
 * If this map uses a different comparator than the given one, this map is copied into a new map.
 */
fun <K, V> MapView<K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isApparently comparator) this else navigableMapOf(comparator, *toArray())

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

@Suppress("UNCHECKED_CAST", "KotlinConstantConditions")
internal fun <T> CollectionView<T>.toGenericArray(): Array<T> {
	val iterator = this.iterator()
	return Array<Any?>(size()) { iterator.next() } as Array<T>
}

private val naturalOrderings: kotlin.collections.Set<Comparator<*>> = kotlin.collections.setOf(
	java.util.Comparator.naturalOrder<Comparable<Any>>(),
	naturalOrder<Comparable<Any>>(),
	naturalOrdering<Any>()
)

private val reverseOrderings: kotlin.collections.Set<Comparator<*>> = kotlin.collections.setOf(
	java.util.Comparator.reverseOrder<Comparable<Any>>(),
	reverseOrder<Comparable<Any>>(),
	reverseOrdering<Any>()
)

private infix fun Comparator<*>.isApparently(other: Comparator<*>): Boolean =
	(this in naturalOrderings && other in naturalOrderings) || (this in reverseOrderings && other in reverseOrderings) || this == other
