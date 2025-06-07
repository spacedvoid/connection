/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A sequenced set view that defines the iteration order of the elements based on a [Comparator].
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this collection is undefined.
 */
interface NavigableSetView<T>: SequencedSetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.DISTINCT], [Spliterator.ORDERED] and [Spliterator.SORTED] are reported by default.
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

	override fun reversed(): NavigableSetView<T>

	/**
	 * The comparator used to sort the elements in this collection.
	 */
	val comparator: Comparator<in T>

	/**
	 * Returns a subset of this collection, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [before] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun headSet(before: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 */
	fun higher(than: T, inclusive: Boolean): T?

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 */
	fun lower(than: T, inclusive: Boolean): T?
}

/**
 * An immutable navigable set.
 */
interface NavigableSet<T>: SequencedSet<T>, NavigableSetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE], [Spliterator.DISTINCT], [Spliterator.ORDERED], and [Spliterator.SORTED] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	override fun reversed(): NavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

/**
 * A mutable navigable set that only supports element removal operations.
 */
interface RemoveOnlyNavigableSet<T>: RemoveOnlySequencedSet<T>, NavigableSetView<T> {
	override fun reversed(): RemoveOnlyNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

/**
 * A mutable navigable set.
 */
interface MutableNavigableSet<T>: MutableSequencedSet<T>, RemoveOnlyNavigableSet<T> {
	override fun reversed(): MutableNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>

	/**
	 * Adds the given [element] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	override fun addAll(collection: CollectionView<out T>): Boolean
}
