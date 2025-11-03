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
 * Collects all elements to a list by their encounter order.
 */
fun <T> Iterable<T>.toList(): List<T> = if(this is List<T>) this else buildList { addAll(this@toList) }

/**
 * Collects all elements to a set by their encounter order.
 */
fun <T> Iterable<T>.toSet(): Set<T> = if(this is Set<T>) this else buildSet { addAll(this@toSet) }

/**
 * Collects all elements to a sequenced set by their encounter order.
 *
 * The iteration order will be equivalent with adding all elements to a [LinkedHashSet].
 */
fun <T> Iterable<T>.toSequencedSet(): SequencedSet<T> = if(this is SequencedSet<T>) this else buildSequencedSet { addAll(this@toSequencedSet) }

/**
 * Collects all elements to a navigable set by their encounter order, using the [natural order][naturalOrder].
 *
 * If this immutable navigable set uses a different comparator than the natural order, this set is copied into a new set.
 */
fun <T: Comparable<T>> Iterable<T>.toNavigableSet(): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isEssentially naturalOrder<T>()) this
	else NavigableSetImpl(sortedSetOf<T>(naturalOrder()).apply { addAll(this@toNavigableSet) })

/**
 * Collects all elements to a navigable set by their encounter order, using the given [comparator].
 *
 * If this immutable navigable set uses a different comparator than the given one, this set is copied into a new set.
 */
fun <T> Iterable<T>.toNavigableSet(comparator: Comparator<in T>): NavigableSet<T> =
	if(this is NavigableSet<T> && this.comparator isEssentially comparator) this
	else NavigableSetImpl(sortedSetOf(comparator).apply { addAll(this@toNavigableSet) })

/**
 * Collects all entries to a sequenced map by their encounter order.
 *
 * The iteration order will be equivalent with adding all entries to an insertion-ordered [LinkedHashMap].
 */
fun <K, V> MapView<out K, V>.toSequencedMap(): SequencedMap<K, V> = if(this is SequencedMap<K, V>) this else buildSequencedMap { putAll(this@toSequencedMap) }

/**
 * Collects all entries to a navigable map, using the [natural order][naturalOrder].
 *
 * If this immutable navigable map uses a different comparator than the natural order, this map is copied into a new map.
 */
fun <K: Comparable<K>, V> MapView<out K, V>.toNavigableMap(): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isEssentially naturalOrder<K>()) this
	else NavigableMapImpl(MutableNavigableMapImpl<K, V>(TreeMap(naturalOrder<K>())).apply { putAll(this@toNavigableMap) }.kotlin)

/**
 * Collects all entries to a navigable map, using the given [comparator].
 *
 * If this immutable navigable map uses a different comparator than the given one, this map is copied into a new map.
 */
fun <K, V> MapView<out K, V>.toNavigableMap(comparator: Comparator<in K>): NavigableMap<K, V> =
	if(this is NavigableMap<K, V> && this.comparator isEssentially comparator) this
	else NavigableMapImpl(MutableNavigableMapImpl<K, V>(TreeMap(comparator)).apply { putAll(this@toNavigableMap) }.kotlin)

/**
 * Collects all elements to a new mutable list by their encounter order.
 */
fun <T> Iterable<T>.toMutableList(): MutableList<T> = mutableListOf<T>().apply { addAll(this@toMutableList) }

/**
 * Collects all elements to a new mutable set by their encounter order.
 */
fun <T> Iterable<T>.toMutableSet(): MutableSet<T> = mutableSetOf<T>().apply { addAll(this@toMutableSet) }

/**
 * Collects all elements to a new linked set by their encounter order.
 */
fun <T> Iterable<T>.toLinkedSet(): LinkedSet<T> = linkedSetOf<T>().apply { addAll(this@toLinkedSet) }

/**
 * Collects all elements to a new mutable navigable set by their encounter order, using the [natural order][naturalOrder].
 */
fun <T: Comparable<T>> Iterable<T>.toMutableNavigableSet(): MutableNavigableSet<T> = mutableNavigableSetOf<T>().apply { addAll(this@toMutableNavigableSet) }

/**
 * Collects all elements to a new mutable navigable set by their encounter order, using the given [comparator].
 */
fun <T> Iterable<T>.toMutableNavigableSet(comparator: Comparator<in T>): MutableNavigableSet<T> = mutableNavigableSetOf(comparator).apply { addAll(this@toMutableNavigableSet) }

/**
 * Collects all entries to a new mutable map.
 */
fun <K, V> MapView<out K, V>.toMutableMap(): MutableMap<K, V> = mutableMapOf<K, V>().apply { putAll(this@toMutableMap) }

/**
 * Collects all entries to a new linked map by their encounter order.
 */
fun <K, V> MapView<out K, V>.toLinkedMap(): LinkedMap<K, V> = linkedMapOf<K, V>().apply { putAll(this@toLinkedMap) }

/**
 * Collects all entries to a new mutable navigable map, using the [natural order][naturalOrder].
 */
fun <K: Comparable<K>, V> MapView<out K, V>.toMutableNavigableMap(): MutableNavigableMap<K, V> = mutableNavigableMapOf<K, V>().apply { putAll(this@toMutableNavigableMap) }

/**
 * Collects all entries to a new mutable navigable map, using the given [comparator].
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
