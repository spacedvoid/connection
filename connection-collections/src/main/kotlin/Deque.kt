/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A double-ended queue, which supports element addition and removal from both head and tail,
 * commonly named as a [deque](https://en.wikipedia.org/wiki/Double-ended_queue).
 *
 * Unlike how [java.util.Deque] does, this type does not inherit from [Queue], since the FIFO manner is not well-defined for a deque.
 */
interface Deque<T>: MutableSequencedCollection<T> {
	/**
	 * Returns a new iterator, iterating from head to tail.
	 */
	override fun iterator(): MutableIterator<T>

	/**
	 * Returns `true` if this deque is empty, `false` otherwise.
	 */
	override fun isEmpty(): Boolean

	/**
	 * Returns the head of this deque.
	 * Throws [NoSuchElementException] if this deque is empty.
	 */
	override fun first(): T

	/**
	 * Returns the tail of this deque.
	 * Throws [NoSuchElementException] if this deque is empty.
	 */
	override fun last(): T

	/**
	 * Returns the head of this deque, or `null` if this deque is empty.
	 */
	fun peekFirst(): T?

	/**
	 * Returns the tail of this deque, or `null` if this deque is empty.
	 */
	fun peekLast(): T?

	/**
	 * Adds the given [element] to the head of this deque.
	 */
	fun addFirst(element: T)

	/**
	 * Adds the given [element] to the tail of this deque.
	 *
	 * This method is equivalent with [add].
	 */
	fun addLast(element: T) {
		add(element)
	}

	/**
	 * Adds all elements from the given [collection] to the head of this deque, by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 */
	fun addAllFirst(collection: CollectionView<T>): Boolean

	/**
	 * Adds all elements from the given [collection] to the tail of this deque, by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 *
	 * This method is equivalent with [addAll].
	 */
	fun addAllLast(collection: CollectionView<T>): Boolean = addAll(collection)

	/**
	 * Removes and returns the head of this deque.
	 * Throws [NoSuchElementException] if this deque is empty.
	 */
	override fun removeFirst(): T

	/**
	 * Removes and returns the tail of this deque.
	 * Throws [NoSuchElementException] if this deque is empty.
	 */
	override fun removeLast(): T

	/**
	 * Removes and returns the head of this deque, or returns `null` if this deque is empty.
	 */
	fun pollFirst(): T?

	/**
	 * Removes and returns the tail of this deque, or returns `null` if this deque is empty.
	 */
	fun pollLast(): T?

	/**
	 * Removes the first occurrence of the given [element] from this deque, searching from the head of this deque.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this deque matches the given [element] is determined via [Any.equals].
	 *
	 * This method is equivalent with [remove].
	 */
	fun removeFirst(element: T): Boolean = remove(element)

	/**
	 * Removes the first occurrence of the given [element] from this deque, searching from the tail of this deque.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this deque matches the given [element] is determined via [Any.equals].
	 */
	fun removeLast(element: T): Boolean

	// MutableSequencedCollection overrides

	/**
	 * Returns a new spliterator for this deque.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this deque ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this deque is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Returns a reverse-ordered deque of this deque.
	 *
	 * The returned deque will have its head mapped to the tail of this deque, and vice versa.
	 * Operations on the returned deque delegates to this deque.
	 */
	override fun reversed(): Deque<T>

	/**
	 * Adds the given [element] to the tail of this deque, and returns `true`.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to the tail of this deque, by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes the first occurrence of the given [element] from this deque, searching from the head of this deque.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this deque matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean

	/**
	 * Returns whether the given object is equal to this deque.
	 *
	 * The given object is equal to this deque if the object is also a [Deque],
	 * and the elements of the given deque are equal to the elements in this deque, by their iteration order.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns a hash code for this deque.
	 *
	 * The hash is computed based on the contained objects' hash codes, by their iteration order.
	 */
	override fun hashCode(): Int
}
