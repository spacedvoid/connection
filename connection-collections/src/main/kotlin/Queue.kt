/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * An FIFO(first in, first out) collection, commonly named as a [queue](https://en.wikipedia.org/wiki/Queue_(abstract_data_type)).
 * Elements are added to the tail of this queue and removed from the head.
 *
 * Unlike how [java.util.Collections.asLifoQueue] does, this collection does not inherit from [Stack], strictly being an FIFO collection.
 *
 * Because a [sequenced collection][SequencedCollectionView] implicitly requires it to be [reversible][SequencedCollectionView.reversed],
 * this queue does not inherit from [MutableSequencedCollection].
 */
interface Queue<T>: MutableCollection<T> {
	/**
	 * Returns a new iterator, iterating from head to tail.
	 *
	 * The iterator may, but is not required to, throw [ConcurrentModificationException]
	 * if this collection is modified while it is in use.
	 */
	override fun iterator(): MutableIterator<T>

	/**
	 * Returns `true` if this queue is empty, `false` otherwise.
	 */
	override fun isEmpty(): Boolean

	/**
	 * Returns the head of this queue.
	 * Throws [NoSuchElementException] if this queue is empty.
	 */
	fun element(): T

	/**
	 * Returns the head of this queue, or `null` if this queue is empty.
	 */
	fun peek(): T?

	/**
	 * Adds the given [element] to this queue, and returns `true`.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this queue by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes and returns the head of this queue.
	 * Throws [NoSuchElementException] if this queue is empty.
	 */
	fun remove(): T

	/**
	 * Removes and returns the head of this queue, or returns `null` if this queue is empty.
	 */
	fun poll(): T?

	/**
	 * Removes all elements from this queue.
	 */
	override fun clear()

	// MutableCollection overrides

	/**
	 * Returns a new spliterator for this queue.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this queue is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Removes the first occurrence of the given [element], searching from the head of this queue.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this queue matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean

	/**
	 * Returns whether the given object is equal to this queue.
	 *
	 * The given object is equal to this queue if the object is also a [Queue],
	 * the two queues have the same size,
	 * and this queue contains equal elements with the given queue in the same iteration order.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns a hash code for this queue.
	 *
	 * For consistency between implementations, the result must be equal to
	 * ```kotlin
	 * fold(1) { r, e -> r * 31 + e.hashCode() }
	 * ```
	 */
	override fun hashCode(): Int
}
