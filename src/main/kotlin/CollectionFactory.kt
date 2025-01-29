/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.TreeMap
import kotlin.collections.sortedSetOf as kotlinSortedSetOf

/**
 * Creates a [List] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> listOf(vararg elements: T): List<T> = arrayListOf(*elements).asConnection()

/**
 * Creates a [MutableList] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> mutableListOf(vararg elements: T): MutableList<T> = arrayListOf(*elements).asMutableConnection()

/**
 * Creates a [Set] with the given [elements].
 */
fun <T> setOf(vararg elements: T): Set<T> = hashSetOf(*elements).asConnection()

/**
 * Creates a [MutableSet] with the given [elements].
 */
fun <T> mutableSetOf(vararg elements: T): MutableSet<T> = hashSetOf(*elements).asMutableConnection()

/**
 * Creates a [SequencedSet] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> sequencedSetOf(vararg elements: T): SequencedSet<T> = linkedSetOf(*elements).asConnection()

/**
 * Creates a [MutableSequencedSet] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> mutableSequencedSetOf(vararg elements: T): MutableSequencedSet<T> = linkedSetOf(*elements).asMutableConnection()

/**
 * Creates a [NavigableSet] with the given [elements], using the [natural ordering][naturalOrder].
 */
fun <T: Comparable<T>> navigableSetOf(vararg elements: T): NavigableSet<T> =
	kotlinSortedSetOf(naturalOrder(), *elements).asConnection()

/**
 * Creates a [NavigableSet] with the given [elements] using the [comparator].
 */
fun <T> navigableSetOf(comparator: Comparator<in T>, vararg elements: T): NavigableSet<T> =
	kotlinSortedSetOf(comparator, *elements).asConnection()

/**
 * Creates a [MutableNavigableSet] with the given [elements], using the [natural ordering][naturalOrder].
 */
fun <T: Comparable<T>> mutableNavigableSetOf(vararg elements: T): MutableNavigableSet<T> =
	kotlinSortedSetOf(naturalOrder(), *elements).asMutableConnection()

/**
 * Creates a [MutableNavigableSet] with the given [elements] using the [comparator].
 */
fun <T> mutableNavigableSetOf(comparator: Comparator<in T>, vararg elements: T): MutableNavigableSet<T> =
	kotlinSortedSetOf(comparator, *elements).asMutableConnection()

/**
 * Creates a [Map] with the given [entries].
 */
fun <K, V> mapOf(vararg entries: Pair<K, V>): Map<K, V> = hashMapOf(*entries).asConnection()

/**
 * Creates a [MutableMap] with the given [entries].
 */
fun <K, V> mutableMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> = hashMapOf(*entries).asMutableConnection()

/**
 * Creates a [SequencedMap] with the given [entries].
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> sequencedMapOf(vararg entries: Pair<K, V>): SequencedMap<K, V> = linkedMapOf(*entries).asConnection()

/**
 * Creates a [MutableSequencedMap] with the given [entries].
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> mutableSequencedMapOf(vararg entries: Pair<K, V>): MutableSequencedMap<K, V> = linkedMapOf(*entries).asMutableConnection()

/**
 * Creates a [NavigableMap] with the given [entries], using the [natural ordering][naturalOrder].
 */
fun <K: Comparable<K>, V> navigableMapOf(vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { entries.forEach { put(it.first, it.second) } }.asConnection()

/**
 * Creates a [NavigableMap] with the given [entries] using the [comparator].
 */
fun <K, V> navigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asConnection()

/**
 * Creates a [MutableNavigableMap] with the given [entries], using the [natural ordering][naturalOrder].
 */
fun <K: Comparable<K>, V> mutableNavigableMapOf(vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()

/**
 * Creates a [MutableNavigableMap] with the given [entries] using the [comparator].
 */
fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { entries.forEach { put(it.first, it.second) } }.asMutableConnection()
