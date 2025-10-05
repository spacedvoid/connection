/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * Returns a list that collects the elements from this sequence, in the iteration order.
 */
fun <T> Sequence<T>.toList(): List<T> = buildList { addAll(this@toList) }

/**
 * Returns a mutable list that collects the elements from this sequence, in the iteration order.
 */
fun <T> Sequence<T>.toMutableList(): MutableList<T> = mutableListOf<T>().apply { addAll(this@toMutableList) }

/**
 * Returns a set that collects the elements from this sequence.
 */
fun <T> Sequence<T>.toSet(): Set<T> = buildSet { addAll(this@toSet) }

/**
 * Returns a mutable set that collects the elements from this sequence.
 */
fun <T> Sequence<T>.toMutableSet(): MutableSet<T> = mutableSetOf<T>().apply { addAll(this@toMutableSet) }

/**
 * Returns a sequenced set that collects the elements from this sequence, in the iteration order.
 */
fun <T> Sequence<T>.toSequencedSet(): SequencedSet<T> = buildSequencedSet { addAll(this@toSequencedSet) }

/**
 * Returns a mutable sequenced set that collects the elements from this sequence, in the iteration order.
 */
@Deprecated(
	"This method will be replaced with a better version in 0.2.0. Use a LinkedHashSet instead.",
	ReplaceWith("LinkedHashSet<T>().asMutableConnection().apply { addAll(this@toSequencedSet) }", "java.util.LinkedHashSet")
)
fun <T> Sequence<T>.toMutableSequencedSet(): MutableSequencedSet<T> = mutableSequencedSetOf<T>().apply { addAll(this@toMutableSequencedSet) }

/**
 * Returns a map that collects the entries after the [transform] of each element from this sequence.
 */
inline fun <T, K, V> Sequence<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> = buildMap {
	for(e in this@associate) transform(e).let { put(it.first, it.second) }
}

/**
 * Returns a map that collects the entries by associating the [transform] of each element as the key and the element as the value.
 */
inline fun <T, K> Sequence<T>.associateBy(transform: (T) -> K): Map<K, T> = buildMap {
	for(e in this@associateBy) put(transform(e), e)
}

/**
 * Returns a map that collects the entries by associating the element as the key and the [transform] of each element as the value.
 */
inline fun <T, V> Sequence<T>.associateWith(transform: (T) -> V): Map<T, V> = buildMap {
	for(e in this@associateWith) put(e, transform(e))
}
