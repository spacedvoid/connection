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
 * Returns an empty list.
 */
fun <T> listOf(): List<T> = emptyList

/**
 * Returns a list with the given [elements].
 */
fun <T> listOf(vararg elements: T): List<T> = elements.kotlinAsList().asConnection()

/**
 * Returns a new mutable list with the given [elements].
 */
fun <T> mutableListOf(vararg elements: T): MutableList<T> = elements.toMutableList()

/**
 * Returns an empty set.
 */
fun <T> setOf(): Set<T> = EmptySequencedSet

/**
 * Returns a set with the given [elements].
 */
fun <T> setOf(vararg elements: T): Set<T> = HashSet(elements.kotlinAsList()).asConnection()

/**
 * Returns a new mutable set with the given [elements].
 */
fun <T> mutableSetOf(vararg elements: T): MutableSet<T> = HashSet(elements.kotlinAsList()).asMutableConnection()

/**
 * Returns an empty sequenced set.
 */
fun <T> sequencedSetOf(): SequencedSet<T> = EmptySequencedSet

/**
 * Returns a sequenced set with the given [elements].
 *
 * The result is equivalent with adding all elements to a [LinkedHashSet].
 */
fun <T> sequencedSetOf(vararg elements: T): SequencedSet<T> =
	LinkedHashSet(elements.kotlinAsList()).asConnection()

/**
 * Returns a new mutable sequenced set with the given [elements].
 *
 * The result is equivalent with adding all elements to a [LinkedHashSet].
 */
@Deprecated(
	"This method will be replaced with a better version in 0.2.0. Use a LinkedHashSet instead.",
	ReplaceWith("LinkedHashSet<T>().asMutableConnection().apply { addAll(elements) }", "java.util.LinkedHashSet"),
	DeprecationLevel.ERROR
)
fun <T> mutableSequencedSetOf(vararg elements: T): MutableSequencedSet<T> =
	LinkedHashSet(elements.kotlinAsList()).asMutableConnection()

/**
 * Returns a navigable set with the given [elements], using the [natural order][naturalOrder].
 */
fun <T: Comparable<T>> navigableSetOf(vararg elements: T): NavigableSet<T> =
	java.util.TreeSet<T>(naturalOrder()).apply { addAll(elements) }.asConnection()

/**
 * Returns a navigable set with the given [elements] using the [comparator].
 */
fun <T> navigableSetOf(comparator: Comparator<in T>, vararg elements: T): NavigableSet<T> =
	java.util.TreeSet(comparator).apply { addAll(elements) }.asConnection()

/**
 * Returns a new mutable navigable set with the given [elements], using the [natural order][naturalOrder].
 */
fun <T: Comparable<T>> mutableNavigableSetOf(vararg elements: T): MutableNavigableSet<T> =
	java.util.TreeSet<T>(naturalOrder()).apply { addAll(elements) }.asMutableConnection()

/**
 * Returns a new mutable navigable set with the given [elements] using the [comparator].
 */
fun <T> mutableNavigableSetOf(comparator: Comparator<in T>, vararg elements: T): MutableNavigableSet<T> =
	java.util.TreeSet(comparator).apply { addAll(elements) }.asMutableConnection()

/**
 * Returns an empty map.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> mapOf(): Map<K, V> = EmptySequencedMap as Map<K, V>

/**
 * Returns a map with the given [entries].
 */
fun <K, V> mapOf(vararg entries: Pair<K, V>): Map<K, V> =
	HashMap.newHashMap<K, V>(entries.size).apply { putAll(entries) }.asConnection()

/**
 * Returns a new mutable map with the given [entries].
 */
fun <K, V> mutableMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> =
	HashMap.newHashMap<K, V>(entries.size).apply { putAll(entries) }.asMutableConnection()

/**
 * Returns an empty sequenced map.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> sequencedMapOf(): SequencedMap<K, V> = EmptySequencedMap as SequencedMap<K, V>

/**
 * Returns a sequenced map with the given [entries].
 *
 * The result is equivalent with adding all entries to an insertion-ordered [LinkedHashMap].
 */
fun <K, V> sequencedMapOf(vararg entries: Pair<K, V>): SequencedMap<K, V> =
	LinkedHashMap.newLinkedHashMap<K, V>(entries.size).apply { putAll(entries) }.asConnection()

/**
 * Returns a new mutable sequenced map with the given [entries].
 *
 * The result is equivalent with adding all entries to an insertion-ordered [LinkedHashMap].
 */
@Deprecated(
	"This method will be replaced with a better version in 0.2.0. Use a LinkedHashMap instead.",
	ReplaceWith("LinkedHashMap<K, V>().asMutableConnection().apply { putAll(entries) }", "java.util.LinkedHashMap"),
	DeprecationLevel.ERROR
)
fun <K, V> mutableSequencedMapOf(vararg entries: Pair<K, V>): MutableSequencedMap<K, V> =
	LinkedHashMap.newLinkedHashMap<K, V>(entries.size).apply { putAll(entries) }.asMutableConnection()

/**
 * Returns a navigable map with the given [entries], using the [natural order][naturalOrder].
 */
fun <K: Comparable<K>, V> navigableMapOf(vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { putAll(entries) }.asConnection()

/**
 * Returns a navigable map with the given [entries] using the [comparator].
 */
fun <K, V> navigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): NavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asConnection()

/**
 * Returns a new mutable navigable map with the given [entries], using the [natural order][naturalOrder].
 */
fun <K: Comparable<K>, V> mutableNavigableMapOf(vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(naturalOrder()).apply { putAll(entries) }.asMutableConnection()

/**
 * Returns a new mutable navigable map with the given [entries] using the [comparator].
 */
fun <K, V> mutableNavigableMapOf(comparator: Comparator<in K>, vararg entries: Pair<K, V>): MutableNavigableMap<K, V> =
	TreeMap<K, V>(comparator).apply { putAll(entries) }.asMutableConnection()

/**
 * Returns a stack with the given [elements].
 *
 * The iteration order is defined as the reversed encounter order:
 * the first element will be the bottom, and the last element will be the top.
 * This behavior is consistent with creating stacks using collections, such as `ArrayDeque(listOf(1, 2, 3))`.
 */
fun <T> stackOf(vararg elements: T): Stack<T> = StackImpl(java.util.ArrayDeque(elements.kotlinAsList()))

/**
 * Returns a queue with the given [elements].
 *
 * The head will be the first element, and the tail will be the last element.
 */
fun <T> queueOf(vararg elements: T): Queue<T> = QueueImpl(java.util.ArrayDeque(elements.kotlinAsList()))

/**
 * Returns a deque with the given [elements].
 *
 * The head will be the first element, and the tail will be the last element.
 */
fun <T> dequeOf(vararg elements: T): Deque<T> = DequeImpl(java.util.ArrayDeque(elements.kotlinAsList()))

//TODO: Should enum collections implement Navigable-collection types, or at least Sequenced-collection types?

/**
 * Returns a specialized set for enum entries.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified T: Enum<T>> enumSetOf(vararg elements: T): Set<T> {
	// Might not be possible, but validating is never wrong.
	require(T::class != Enum::class) { "Class kotlin.Enum cannot be the element type for enum sets" }
	return java.util.EnumSet.noneOf(T::class.java).apply { addAll(elements) }.asConnection()
}

/**
 * Returns a new specialized mutable set for enum entries.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified T: Enum<T>> mutableEnumSetOf(vararg elements: T): MutableSet<T> {
	require(T::class != Enum::class) { "Class kotlin.Enum cannot be the element type for enum sets" }
	return java.util.EnumSet.noneOf(T::class.java).apply { addAll(elements) }.asMutableConnection()
}

/**
 * Returns a new specialized mutable map for enum entry keys.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified K: Enum<K>, V> enumMapOf(vararg entries: Pair<K, V>): Map<K, V> {
	require(K::class != Enum::class) { "Class kotlin.Enum cannot be the key type for enum maps" }
	return java.util.EnumMap<K, V>(K::class.java).apply { putAll(entries) }.asConnection()
}

/**
 * Returns a new specialized mutable map for enum entry keys.
 *
 * The iteration order is defined as the natural order of the enum type.
 */
inline fun <reified K: Enum<K>, V> mutableEnumMapOf(vararg entries: Pair<K, V>): MutableMap<K, V> {
	require(K::class != Enum::class) { "Class kotlin.Enum cannot be the key type for enum maps" }
	return java.util.EnumMap<K, V>(K::class.java).apply { putAll(entries) }.asMutableConnection()
}
