package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Navigable

interface SortedNavigableSet<T>: Navigable<T>, SequencedSet<T> {
	override val comparator: Comparator<in T>?

	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T>

	fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T>

	fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T>

	override fun higher(than: T, inclusive: Boolean): T?

	override fun lower(than: T, inclusive: Boolean): T?
}

interface MutatingSortedNavigableSetView<T>: Navigable<T>, MutatingSequencedSetView<T> {
	override val comparator: Comparator<in T>?

	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableSetView<T>

	fun headSet(before: T, inclusive: Boolean): MutatingSortedNavigableSetView<T>

	fun tailSet(after: T, inclusive: Boolean): MutatingSortedNavigableSetView<T>

	override fun higher(than: T, inclusive: Boolean): T?

	override fun lower(than: T, inclusive: Boolean): T?
}

interface RemoveOnlySortedNavigableSet<T>: MutatingSortedNavigableSetView<T>, RemoveOnlySequencedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>
}

interface MutableSortedNavigableSet<T>: RemoveOnlySortedNavigableSet<T>, MutableSequencedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T>
}
