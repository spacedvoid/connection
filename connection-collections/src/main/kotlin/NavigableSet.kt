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
 * The iteration order is the ascending order; any element is less than the elements that come ofter.
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this collection is undefined.
 */
interface NavigableSetView<out T>: SequencedSetView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.DISTINCT], [Spliterator.ORDERED] and [Spliterator.SORTED] are reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	override fun reversed(): NavigableSetView<T>

	/**
	 * The comparator used to determine the iteration order.
	 */
	val comparator: Comparator<in @UnsafeVariance T>

	/**
	 * Returns a subset of this set, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned set is delegated to this set.
	 * Adding elements to the returned set throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun subSet(from: @UnsafeVariance T, to: @UnsafeVariance T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this set, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [before] lies outside the range.
	 *
	 * Operations on the returned set is delegated to this set.
	 * Adding elements to the returned set throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun headSet(before: @UnsafeVariance T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this set, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned set is delegated to this set.
	 * Adding elements to the returned set throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun tailSet(after: @UnsafeVariance T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 */
	fun higher(than: @UnsafeVariance T, inclusive: Boolean): T?

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 */
	fun lower(than: @UnsafeVariance T, inclusive: Boolean): T?
}

/**
 * An immutable navigable set.
 */
interface NavigableSet<out T>: SequencedSet<T>, NavigableSetView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE], [Spliterator.DISTINCT],
	 * [Spliterator.ORDERED], and [Spliterator.SORTED] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	override fun reversed(): NavigableSet<T>

	override fun subSet(from: @UnsafeVariance T, to: @UnsafeVariance T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: @UnsafeVariance T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: @UnsafeVariance T, inclusive: Boolean): NavigableSet<T>
}

/**
 * A mutable navigable set that only supports element removal operations.
 */
interface RemoveOnlyNavigableSet<T>: RemoveOnlySequencedSet<T>, NavigableSetView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.DISTINCT], [Spliterator.ORDERED] and [Spliterator.SORTED] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

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
	 * Adds the given [element] to the end of this set.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced, and this set remains unchanged.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to the end of this set by their encounter order.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced, and this set remains unchanged.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean
}
