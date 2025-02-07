/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A [CollectionView] that additionally defines the iteration order.
 */
interface SequencedCollectionView<T>: CollectionView<T> {
	/**
	 * Returns a new for this collection.
	 *
	 * The iteration order is defined by the specification.
	 */
	override fun iterator(): Iterator<T> =
		super.iterator()

	/**
	 * Returns a reverse-ordered collection of this collection.
	 *
	 * Operations on the returned collection delegates to this collection.
	 *
	 * @implNote
	 * Overrides should return the same type with the declaring class.
	 */
	fun reversed(): SequencedCollectionView<T>

	/**
	 * Returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun first(): T =
		this.kotlin.first

	/**
	 * Returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun last(): T =
		this.kotlin.last

	override val CollectionView<T>.kotlin: java.util.SequencedCollection<T>
}

/**
 * An immutable [SequencedCollectionView].
 */
interface SequencedCollection<T>: SequencedCollectionView<T>, Collection<T>{
	override fun reversed(): SequencedCollection<T>
}

/**
 * A [SequencedCollectionView] that additionally supports element removal operations.
 */
interface RemoveOnlySequencedCollection<T>: SequencedCollectionView<T>, RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T> =
		super<RemoveOnlyCollection>.iterator()

	override fun reversed(): RemoveOnlySequencedCollection<T>

	/**
	 * Removes and returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeFirst(): T =
		this.kotlin.removeFirst()

	/**
	 * Removes and returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeLast(): T =
		this.kotlin.removeLast()
}

/**
 * A [RemoveOnlySequencedCollection] that additionally supports element addition operations.
 *
 * @apiNote
 * This class does not define `addFirst` and `addLast` operations;
 * most subtypes of this collection(namely [MutableNavigableSet]) manages the iteration order by other criteria.
 */
interface MutableSequencedCollection<T>: RemoveOnlySequencedCollection<T>, MutableCollection<T> {
	override fun reversed(): MutableSequencedCollection<T>
}
