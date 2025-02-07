/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A [SequencedSetView] that defines the iteration order of the elements based on a [Comparator].
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this collection is undefined.
 */
interface NavigableSetView<T>: SequencedSetView<T> {
	/**
	 * The comparator used to sort the elements in this collection.
	 */
	val comparator: Comparator<in T>

	override fun reversed(): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * @implNote
	 * Overrides should return the same type with the declaring class.
	 */
	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [before] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * @implNote
	 * Overrides should return the same type with the declaring class.
	 */
	fun headSet(before: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * @implNote
	 * Overrides should return the same type with the declaring class.
	 */
	fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 */
	fun higher(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.ceiling(than) else this.kotlin.higher(than)

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 */
	fun lower(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.floor(than) else this.kotlin.lower(than)

	override val CollectionView<T>.kotlin: java.util.NavigableSet<T>
}

/**
 * An immutable [NavigableSetView].
 */
interface NavigableSet<T>: SequencedSet<T>, NavigableSetView<T> {
	override fun reversed(): NavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

/**
 * A [NavigableSetView] that additionally supports element removal operations.
 */
interface RemoveOnlyNavigableSet<T>: RemoveOnlySequencedSet<T>, NavigableSetView<T> {
	override fun reversed(): RemoveOnlyNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

/**
 * A [RemoveOnlyNavigableSet] that additionally supports element addition operations.
 */
interface MutableNavigableSet<T>: MutableSequencedSet<T>, RemoveOnlyNavigableSet<T> {
	override fun reversed(): MutableNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
