/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.DequeImpl
import io.github.spacedvoid.connection.impl.QueueImpl
import io.github.spacedvoid.connection.impl.StackImpl
import java.util.TreeMap
import kotlin.collections.asList as kotlinAsList

/**
 * Returns an empty [List].
 */
fun <T> listOf(): List<T> = emptyList

/**
 * Creates a [List] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> listOf(vararg elements: T): List<T> = elements.kotlinAsList().asConnection()

/**
 * Creates a [MutableList] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> mutableListOf(vararg elements: T): MutableList<T> = elements.toMutableList()

/**
 * Returns an empty set.
 */
fun <T> setOf(): Set<T> = EmptySequencedSet

/**
 * Creates a [Set] with the given [elements].
 */
fun <T> setOf(vararg elements: T): Set<T> = HashSet(elements.kotlinAsList()).asConnection()

/**
 * Creates a [MutableSet] with the given [elements].
 */
fun <T> mutableSetOf(vararg elements: T): MutableSet<T> = HashSet(elements.kotlinAsList()).asMutableConnection()

/**
 * Returns an empty sequenced set.
 */
fun <T> sequencedSetOf(): SequencedSet<T> = EmptySequencedSet

/**
 * Creates a [SequencedSet] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> sequencedSetOf(vararg elements: T): SequencedSet<T> =
	LinkedHashSet(elements.kotlinAsList()).asConnection()

/**
 * Creates a [MutableSequencedSet] with the given [elements].
 *
 * The iteration order is defined as the encounter order of the given [elements],
 * and also the addition order of further elements.
 */
@Deprecated(
	"This method will be replaced with a better version in 0.2.0. Use a LinkedHashSet instead.",
	ReplaceWith("LinkedHashSet<T>().asMutableConnection().apply { addAll(elements) }", "java.util.LinkedHashSet")
)
fun <T> mutableSequencedSetOf(vararg elements: T): MutableSequencedSet<T> =
	LinkedHashSet(elements.kotlinAsList()).asMutableConnection()

/**
 * Creates a [NavigableSet] with the given [elements], using the [natural ordering][naturalOrder].
 */
fun <T: Comparable<T>> navigableSetOf(vararg elements: T): NavigableSet<T> =
	java.util.TreeSet<T>(naturalOrder()).apply { addAll(elements) }.asConnection()

/**
 * Creates a [NavigableSet] with the given [elements] using the [comparator].
 */
fun <T> navigableSetOf(comparator: Comparator<in T>, vararg elements: T): NavigableSet<T> =
	java.util.TreeSet(comparator).apply { addAll(elements) }.asConnection()

/**
 * Creates a [MutableNavigableSet] with the given [elements], using the [natural ordering][naturalOrder].
 */
fun <T: Comparable<T>> mutableNavigableSetOf(vararg elements: T): MutableNavigableSet<T> =
	java.util.TreeSet<T>(naturalOrder()).apply { addAll(elements) }.asMutableConnection()

/**
 * Creates a [MutableNavigableSet] with the given [elements] using the [comparator].
 */
fun <T> mutableNavigableSetOf(comparator: Comparator<in T>, vararg elements: T): MutableNavigableSet<T> =
	java.util.TreeSet(comparator).apply { addAll(elements) }.asMutableConnection()

/**
 * Returns an empty map.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> mapOf(): Map<K, V> = EmptySequencedMap as Map<K, V>

/**
 * Creates a [Map] with the given [entries].
 */
fun <K, V> mapOf(vararg entries: Pair<K, V>): Map<K, V> =
	HashMap.newHashMap<K, V>(entries.size).apply { putAll(entries) }.asConnection()

/**
 * Creates a [MutableMap] with the given [entries].
 */
fun <K, V> mutableMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> =
	HashMap.newHashMap<K, V>(entries.size).apply { putAll(entries) }.asMutableConnection()

/**
 * Returns an empty sequenced map.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> sequencedMapOf(): SequencedMap<K, V> = EmptySequencedMap as SequencedMap<K, V>

/**
 * Creates a [SequencedMap] with the given [entries].
 *
 * The iteration order is defined as the encounter order.
 */
fun <K, V> sequencedMapOf(vararg entries: Pair<K, V>): SequencedMap<K, V> =
	LinkedHashMap.newLinkedHashMap<K, V>(entries.size).apply { putAll(entries) }.asConnection()

/**
 * Creates a [MutableSequencedMap] with the given [entries].
 *
 * The iteration order is defined as the encounter order of the given [entries],
 * and also the addition order of further entries.
 */
@Deprecated(
	"This method will be replaced with a better version in 0.2.0. Use a LinkedHashMap instead.",
	ReplaceWith("LinkedHashMap<K, V>().asMutableConnection().apply { putAll(entries) }", "java.util.LinkedHashMap")
)
fun <K, V> mutableSequencedMapOf(vararg entries: Pair<K, V>): MutableSequencedMap<K, V> =
	LinkedHashMap.newLinkedHashMap<K, V>(entries.size).apply { putAll(entries) }.asMutableConnection()

/**
 * Creates a [NavigableMap] with the given [entries], using the [natural ordering][naturalOrder].
 */
fun <K: Comparable<K>, V> navigableMapOf(vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { putAll(entries) }.asConnection()

/**
 * Creates a [NavigableMap] with the given [entries] using the [comparator].
 */
fun <K, V> navigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asConnection()

/**
 * Creates a [MutableNavigableMap] with the given [entries], using the [natural ordering][naturalOrder].
 */
fun <K: Comparable<K>, V> mutableNavigableMapOf(vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { putAll(entries) }.asMutableConnection()

/**
 * Creates a [MutableNavigableMap] with the given [entries] using the [comparator].
 */
fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asMutableConnection()

/**
 * Creates a [Stack] with the given [elements].
 *
 * The iteration order is defined as the reversed encounter order;
 * the first element will be the bottom, and the last element will be the top.
 * This behavior is consistent with creating stacks with collections, such as [ArrayDeque]`(listOf(1, 2, 3))`.
 */
fun <T> stackOf(vararg elements: T): Stack<T> = StackImpl(java.util.ArrayDeque(elements.kotlinAsList()))

/**
 * Creates a [Queue] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> queueOf(vararg elements: T): Queue<T> = QueueImpl(java.util.ArrayDeque(elements.kotlinAsList()))

/**
 * Creates a [Deque] with the given [elements].
 *
 * The iteration order is defined as the encounter order.
 */
fun <T> dequeOf(vararg elements: T): Deque<T> = DequeImpl(java.util.ArrayDeque(elements.kotlinAsList()))

//TODO: Should enum collections implement Navigable-collection types, or at least Sequenced-collection types?

/**
 * Creates a specialized [Set] for enum entries.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified T: Enum<T>> enumSetOf(vararg elements: T): Set<T> {
	// Might not be possible, but validating is never wrong.
	require(T::class != Enum::class) { "Class kotlin.Enum cannot be the element type for enum sets" }
	return java.util.EnumSet.noneOf(T::class.java).apply { addAll(elements) }.asConnection()
}

/**
 * Creates a specialized [MutableSet] for enum entries.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified T: Enum<T>> mutableEnumSetOf(vararg elements: T): MutableSet<T> {
	require(T::class != Enum::class) { "Class kotlin.Enum cannot be the element type for enum sets" }
	return java.util.EnumSet.noneOf(T::class.java).apply { addAll(elements) }.asMutableConnection()
}

/**
 * Creates a specialized [Map] for enum entries.
 */
inline fun <reified K: Enum<K>, V> enumMapOf(vararg entries: Pair<K, V>): Map<K, V> {
	require(K::class != Enum::class) { "Class kotlin.Enum cannot be the key type for enum maps" }
	return java.util.EnumMap<K, V>(K::class.java).apply { putAll(entries) }.asConnection()
}

/**
 * Creates a specialized [MutableMap] for enum entries.
 */
inline fun <reified K: Enum<K>, V> mutableEnumMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> {
	require(K::class != Enum::class) { "Class kotlin.Enum cannot be the key type for enum maps" }
	return java.util.EnumMap<K, V>(K::class.java).apply { putAll(entries) }.asMutableConnection()
}
