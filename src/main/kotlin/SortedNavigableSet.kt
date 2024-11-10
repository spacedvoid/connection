package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Navigable

interface SortedNavigableSetView<T>: SequencedSetView<T>, Navigable<T> {
	override val comparator: Comparator<in T>?

	override fun reverse(): SortedNavigableSetView<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSetView<T>

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSetView<T>

	override fun higher(than: T, inclusive: Boolean): T?

	override fun lower(than: T, inclusive: Boolean): T?
}

interface SortedNavigableSet<T>: SequencedSet<T>, SortedNavigableSetView<T>, Navigable<T> {
	override fun reverse(): SortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T>
}

interface RemoveOnlySortedNavigableSet<T>: RemoveOnlySequencedSet<T>, SortedNavigableSetView<T> {
	override fun reverse(): RemoveOnlySortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>
}

interface MutableSortedNavigableSet<T>: MutableSequencedSet<T>, RemoveOnlySortedNavigableSet<T> {
	override fun reverse(): MutableSortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T>
}
