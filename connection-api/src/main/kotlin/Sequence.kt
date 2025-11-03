/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * Collects all elements from this sequence to a list by their iteration order.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toList(): List<T> = buildList { addAll(this@toList) }

/**
 * Collects all elements from this sequence to a mutable list by their iteration order.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toMutableList(): MutableList<T> = mutableListOf<T>().apply { addAll(this@toMutableList) }

/**
 * Collects all elements from this sequence to a set by their iteration order.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toSet(): Set<T> = buildSet { addAll(this@toSet) }

/**
 * Collects all elements from this sequence to a mutable set by their iteration order.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toMutableSet(): MutableSet<T> = mutableSetOf<T>().apply { addAll(this@toMutableSet) }

/**
 * Collects all elements from this sequence to a sequenced set by their iteration order.
 *
 * This operation is equivalent with adding all elements to a [LinkedHashSet].
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toSequencedSet(): SequencedSet<T> = buildSequencedSet { addAll(this@toSequencedSet) }

/**
 * Collects all elements from this sequence to a linked set by their iteration order.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
fun <T> Sequence<T>.toLinkedSet(): LinkedSet<T> = linkedSetOf<T>().apply { addAll(this@toLinkedSet) }

/**
 * Collects the entries to a map from applying the [transform] to each element by their encounter order.
 *
 * If multiple entries have the same key, the last one will be used.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
inline fun <T, K, V> Sequence<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> = buildMap {
	for(e in this@associate) transform(e).let { put(it.first, it.second) }
}

/**
 * Collects the entries to a map where keys are the extracted keys from applying the [keySelector] to each element by their encounter order,
 * and values are the element themselves.
 *
 * If multiple entries have the same key, the last one will be used.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
inline fun <T, K> Sequence<T>.associateBy(keySelector: (T) -> K): Map<K, T> = buildMap {
	for(e in this@associateBy) put(keySelector(e), e)
}

/**
 * Collects the entries to a map where keys are the element themselves,
 * and values are the extracted values from applying the [valueSelector] to each element by their encounter order.
 *
 * If multiple entries have the same key, the last one will be used.
 *
 * The behavior of this operation when this sequence is infinite is undefined.
 */
inline fun <T, V> Sequence<T>.associateWith(valueSelector: (T) -> V): Map<T, V> = buildMap {
	for(e in this@associateWith) put(e, valueSelector(e))
}
