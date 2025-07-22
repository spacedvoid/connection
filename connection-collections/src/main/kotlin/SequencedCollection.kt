/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A collection view that additionally defines the iteration order, which is also consistent.
 */
interface SequencedCollectionView<out T>: CollectionView<T> {
	/**
	 * Returns a new iterator for this collection.
	 *
	 * The iteration order is defined by the specification, and is consistent.
	 */
	override fun iterator(): Iterator<T>

	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this collection ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this collection is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	/**
	 * Returns a reverse-ordered collection of this collection.
	 *
	 * Operations on the returned collection delegates to this collection.
	 */
	fun reversed(): SequencedCollectionView<T>

	/**
	 * Returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun first(): T

	/**
	 * Returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun last(): T
}

/**
 * An immutable sequenced collection.
 */
interface SequencedCollection<out T>: SequencedCollectionView<T>, Collection<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE], and [Spliterator.ORDERED] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	override fun reversed(): SequencedCollection<T>
}

/**
 * A mutable sequenced collection that only supports element removal operations.
 */
interface RemoveOnlySequencedCollection<T>: SequencedCollectionView<T>, RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T>

	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this collection ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this collection is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	override fun reversed(): RemoveOnlySequencedCollection<T>

	/**
	 * Removes a single occurrence of the given [element] from this collection.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 *
	 * The removal is not strictly determined; it may remove the first, last, or any occurrence.
	 */
	override fun remove(element: T): Boolean

	/**
	 * Removes and returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeFirst(): T

	/**
	 * Removes and returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeLast(): T
}

/**
 * A mutable sequenced collection.
 *
 * Note that element additions, mutations, or deletions might not depend on or affect the iteration order.
 */
interface MutableSequencedCollection<T>: RemoveOnlySequencedCollection<T>, MutableCollection<T> {
	/**
	 * Adds the given [element] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 *
	 * The addition is not strictly determined; it may add to the first, last, or any position.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 *
	 * The additions are not strictly determined;
	 * it may add to the first, last, or any position, even for consecutive elements in the given [collection].
	 */
	override fun addAll(collection: CollectionView<T>): Boolean

	override fun reversed(): MutableSequencedCollection<T>
}
