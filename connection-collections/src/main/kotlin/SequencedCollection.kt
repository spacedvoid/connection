/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A collection view that additionally defines the iteration order, which is also consistent.
 */
interface SequencedCollectionView<T>: CollectionView<T> {
	/**
	 * Returns a new iterator for this collection.
	 *
	 * The iteration order is defined by the specification, and is consistent.
	 */
	override fun iterator(): Iterator<T>

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
interface SequencedCollection<T>: SequencedCollectionView<T>, Collection<T> {
	override fun reversed(): SequencedCollection<T>
}

/**
 * A mutable sequenced collection that only supports element removal operations.
 */
interface RemoveOnlySequencedCollection<T>: SequencedCollectionView<T>, RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T>

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
	override fun addAll(collection: CollectionView<out T>): Boolean

	override fun reversed(): MutableSequencedCollection<T>
}
