/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * An LIFO(last in, first out) collection, commonly named as a [stack](https://en.wikipedia.org/wiki/Stack_(abstract_data_type)).
 * Elements are always added to the top of this stack, and removal is only supported for the topmost element.
 *
 * Because a [sequenced collection][SequencedCollectionView] implicitly requires it to be [reversible][SequencedCollectionView.reversed],
 * this stack does not inherit from [MutableSequencedCollection].
 */
interface Stack<T>: MutableCollection<T> {
	/**
	 * Returns a new iterator, iterating from top to bottom.
	 */
	override fun iterator(): MutableIterator<T>

	/**
	 * Returns the size of this stack.
	 */
	override fun size(): Int

	/**
	 * Returns `true` if this stack is empty, `false` otherwise.
	 */
	override fun isEmpty(): Boolean

	/**
	 * Returns the topmost element of this stack.
	 * Throws [NoSuchElementException] if this stack is empty.
	 */
	fun top(): T

	/**
	 * Returns the topmost element of this stack, or `null` if this stack is empty.
	 */
	fun peek(): T?

	/**
	 * Adds the given [element] to the top of this stack.
	 *
	 * This method is equivalent with [add].
	 */
	fun push(element: T) {
		add(element)
	}

	/**
	 * Adds all elements from the given [collection] to the top of this stack, by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 *
	 * This method is equivalent with [addAll].
	 */
	fun pushAll(collection: CollectionView<T>): Boolean = addAll(collection)

	/**
	 * Removes and returns the topmost element of this stack.
	 * Throws [NoSuchElementException] if this stack is empty.
	 */
	fun pop(): T

	/**
	 * Removes and returns the topmost element of this stack, or returns `null` if this stack is empty.
	 */
	fun poll(): T?

	/**
	 * Removes all elements from this stack.
	 */
	override fun clear()

	// MutableCollection overrides

	/**
	 * Returns a new spliterator for this stack.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this stack ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this stack is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Adds the given [element] to the top of this stack, and returns `true`.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to the top of this stack by their encounter order.
	 * Returns `true` if any elements were added, `false` otherwise.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes the first occurrence of the given [element], searching from the top of this stack.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this stack matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean
}
