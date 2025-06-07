/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A set view that additionally defines the iteration order.
 */
interface SequencedSetView<T>: SequencedCollectionView<T>, SetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.DISTINCT], and [Spliterator.ORDERED] are reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this collection ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if the collection is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	override fun reversed(): SequencedSetView<T>
}

/**
 * An immutable sequenced set.
 */
interface SequencedSet<T>: SequencedCollection<T>, Set<T>, SequencedSetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE], [Spliterator.DISTINCT], and [Spliterator.ORDERED] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	override fun reversed(): SequencedSet<T>
}

/**
 * A mutable sequenced set that only supports element removal operations.
 */
interface RemoveOnlySequencedSet<T>: RemoveOnlySequencedCollection<T>, RemoveOnlySet<T>, SequencedSetView<T> {
	override fun reversed(): RemoveOnlySequencedSet<T>
}

/**
 * A mutable sequenced set.
 */
interface MutableSequencedSet<T>: MutableSequencedCollection<T>, MutableSet<T>, RemoveOnlySequencedSet<T> {
	override fun reversed(): MutableSequencedSet<T>

	/**
	 * Adds the given [element] to this set.
	 * Returns `true` if the addition changed this set, `false` otherwise.
	 *
	 * When an element which already matches an instance in this set is added,
	 * the contained instance is not replaced, and this set remains unchanged.
	 *
	 * The addition is not strictly determined; it may add to the first, last, or any position.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [set] to this set.
	 * Returns `true` if the addition changed this set, `false` otherwise.
	 *
	 * When an element which already matches an instance in this set is added,
	 * the contained instance is not replaced, and this set remains unchanged.
	 *
	 * The additions are not strictly determined;
	 * it may add to the first, last, or any position, even for consecutive elements in the given [collection].
	 */
	override fun addAll(collection: CollectionView<out T>): Boolean
}
