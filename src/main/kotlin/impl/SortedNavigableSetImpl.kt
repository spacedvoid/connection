package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.CollectionView
import io.github.spacedvoid.connection.MutableSortedNavigableSet
import io.github.spacedvoid.connection.RemoveOnlySortedNavigableSet
import io.github.spacedvoid.connection.SortedNavigableSet
import io.github.spacedvoid.connection.SortedNavigableSetView
import java.util.SortedSet

/*
 * Manual conversion from java.util.SortedSet to java.util.NavigableSet.
 * For all subSet-creation methods, higher() returns `null` if:
 * 1. Set is empty.
 * 2. No entries are higher than the given entry.
 *
 * In both cases, the returned `null` can be safely replaced with the given entry because:
 * 1. -> The subSet will also be empty.
 * 2. -> The entry is higher (or equal) than the highest entry.
 *
 * If the entry was `null`, higher() will throw (and propagate) a NullPointerException if the set does not permit `null` entries.
 * Otherwise, no visual differences are made(`null` replaced by `null`, or non-`null` not being replaced).
 * In other exceptional cases(where the `from` entry is higher than the `to` entry, or the entry is outside the (sub)set's range),
 * the underlying method will propagate the exceptions, mostly being IllegalArgumentException.
 */

open class SortedNavigableSetViewImpl<T>(private val kotlin: SortedSet<T>): SequencedSetViewImpl<T>(kotlin), SortedNavigableSetView<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun reverse(): SortedNavigableSetView<T> = SortedNavigableSetViewImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSetView<T> =
		SortedNavigableSetViewImpl(this.kotlin.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to))

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSetView<T> =
		SortedNavigableSetViewImpl(this.kotlin.headSet(higher(before, !inclusive) ?: before))

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSetView<T> =
		SortedNavigableSetViewImpl(this.kotlin.tailSet(higher(after, inclusive) ?: after))

	override val CollectionView<T>.kotlin: SortedSet<T>
		get() = this@SortedNavigableSetViewImpl.kotlin
}

open class SortedNavigableSetImpl<T>(private val kotlin: SortedSet<T>): SortedNavigableSetViewImpl<T>(kotlin), SortedNavigableSet<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun reverse(): SortedNavigableSet<T> = SortedNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T> =
		SortedNavigableSetImpl(this.kotlin.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to))

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T> =
		SortedNavigableSetImpl(this.kotlin.headSet(higher(before, !inclusive) ?: before))

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T> =
		SortedNavigableSetImpl(this.kotlin.tailSet(higher(after, inclusive) ?: after))
}

open class RemoveOnlySortedNavigableSetImpl<T>(private val kotlin: SortedSet<T>): SortedNavigableSetViewImpl<T>(kotlin), RemoveOnlySortedNavigableSet<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun reverse(): RemoveOnlySortedNavigableSet<T> = RemoveOnlySortedNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
		RemoveOnlySortedNavigableSetImpl(this.kotlin.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to))

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
		RemoveOnlySortedNavigableSetImpl(this.kotlin.headSet(higher(before, !inclusive) ?: before))

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T> =
		RemoveOnlySortedNavigableSetImpl(this.kotlin.tailSet(higher(after, inclusive) ?: after))
}

open class MutableSortedNavigableSetImpl<T>(private val kotlin: SortedSet<T>): RemoveOnlySortedNavigableSetImpl<T>(kotlin), MutableSortedNavigableSet<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun reverse(): MutableSortedNavigableSet<T> = MutableSortedNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T> =
		MutableSortedNavigableSetImpl(this.kotlin.subSet(higher(from, fromInclusive) ?: from, higher(to, !toInclusive) ?: to))

	override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T> =
		MutableSortedNavigableSetImpl(this.kotlin.headSet(higher(before, !inclusive) ?: before))

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T> =
		MutableSortedNavigableSetImpl(this.kotlin.tailSet(higher(after, inclusive) ?: after))
}
