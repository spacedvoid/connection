/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("IfThenToElvis")

package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.MutableNavigableMapImpl
import io.github.spacedvoid.connection.impl.NavigableMapImpl
import io.github.spacedvoid.connection.impl.NavigableSetImpl
import io.github.spacedvoid.connection.utils.naturalOrdering
import io.github.spacedvoid.connection.utils.reverseOrdering
import java.util.TreeMap

/**
 * Returns a [List] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Iterable<T>.toList(): List<T> = if(this is List<T>) this else buildList { addAll(this@toList) }

/**
 * Returns a [Set] converted from this collection.
 */
fun <T> Iterable<T>.toSet(): Set<T> = if(this is Set<T>) this else buildSet { addAll(this@toSet) }

/**
 * Returns a [SequencedSet] converted from this collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Iterable<T>.toSequencedSet(): SequencedSet<T> = if(this is SequencedSet<T>) this else buildSequencedSet { addAll(this@toSequencedSet) }

/**
 * Returns a [NavigableSet] converted from this collection, using the [natural order][naturalOrder].
 *
 * If this set uses a different comparator than the natural order, this set is copied into a new set.
 */
fun <T: Comparable<T>> Iterable<T>.toNavigableSet(): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isEssentially naturalOrder<T>()) this
	else NavigableSetImpl(sortedSetOf<T>(naturalOrder()).apply { addAll(this@toNavigableSet) })

/**
 * Returns a [NavigableSet] converted from this collection, using the [comparator].
 *
 * If this set uses a different comparator than the given one, this set is copied into a new set.
 */
fun <T> Iterable<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isEssentially comparator) this
	else NavigableSetImpl(sortedSetOf(comparator).apply { addAll(this@toNavigableSet) })

/**
 * Returns a [SequencedMap] converted from this map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> MapView<out K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else buildSequencedMap { putAll(this@toSequencedMap) }

/**
 * Returns a [NavigableMap] converted from this map, using the [natural order][naturalOrder].
 *
 * If this map uses a different comparator than the natural order, this map is copied into a new map.
 */
fun <K: Comparable<K>, V> MapView<out K, V>.toNavigableMap(): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isEssentially naturalOrder<K>()) this
	else NavigableMapImpl(MutableNavigableMapImpl<K, V>(TreeMap(naturalOrder<K>())).apply { putAll(this@toNavigableMap) }.kotlin)

/**
 * Returns a [NavigableMap] converted from this map, using the [comparator].
 *
 * If this map uses a different comparator than the given one, this map is copied into a new map.
 */
fun <K, V> MapView<out K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isEssentially comparator) this
	else NavigableMapImpl(MutableNavigableMapImpl<K, V>(TreeMap(comparator)).apply { putAll(this@toNavigableMap) }.kotlin)

/**
 * Returns a new [MutableList] copied from the collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Iterable<T>.toMutableList(): MutableList<T> = mutableListOf<T>().apply { addAll(this@toMutableList) }

/**
 * Returns a new [MutableSet] copied from the collection.
 */
fun <T> Iterable<T>.toMutableSet(): MutableSet<T> = mutableSetOf<T>().apply { addAll(this@toMutableSet) }

/**
 * Returns a new [MutableSequencedSet] copied from the collection.
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> Iterable<T>.toMutableSequencedSet(): MutableSequencedSet<T> = mutableSequencedSetOf<T>().apply { addAll(this@toMutableSequencedSet) }

/**
 * Returns a new [MutableNavigableSet] copied from the collection.
 *
 * The [natural order][naturalOrder] is used.
 */
fun <T: Comparable<T>> Iterable<T>.toMutableNavigableSet(): MutableNavigableSet<T> = mutableNavigableSetOf<T>().apply { addAll(this@toMutableNavigableSet) }

/**
 * Returns a new [MutableNavigableSet] copied from the collection.
 *
 * The given [comparator] is used.
 */
fun <T> Iterable<T>.toMutableNavigableSet(comparator: Comparator<in T>): MutableNavigableSet<T> = mutableNavigableSetOf(comparator).apply { addAll(this@toMutableNavigableSet) }

/**
 * Returns a new [MutableMap] copied from the map.
 */
fun <K, V> MapView<out K, V>.toMutableMap(): MutableMap<K, V> = mutableMapOf<K, V>().apply { putAll(this@toMutableMap) }

/**
 * Returns a new [MutableSequencedMap] copied from the map.
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> MapView<out K, V>.toMutableSequencedMap(): MutableSequencedMap<K, V> = mutableSequencedMapOf<K, V>().apply { putAll(this@toMutableSequencedMap) }

/**
 * Returns a new [MutableNavigableMap] copied from the map.
 *
 * The [natural order][naturalOrder] is used.
 */
fun <K: Comparable<K>, V> MapView<out K, V>.toMutableNavigableMap(): MutableNavigableMap<K, V> = mutableNavigableMapOf<K, V>().apply { putAll(this@toMutableNavigableMap) }

/**
 * Returns a new [MutableNavigableMap] copied from the collection.
 *
 * The given [comparator] is used.
 */
fun <K, V> MapView<out K, V>.toMutableNavigableMap(comparator: Comparator<in K>): MutableNavigableMap<K, V> = mutableNavigableMapOf<K, V>(comparator).apply { putAll(this@toMutableNavigableMap) }

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

private infix fun Comparator<*>.isEssentially(other: Comparator<*>): Boolean =
	(this in naturalOrderings && other in naturalOrderings) || (this in reverseOrderings && other in reverseOrderings) || this == other
