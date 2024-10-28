package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Sorted

interface SortedSet<T>: Sorted<T>, SequencedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedSet<T>

	override fun headSet(before: T, inclusive: Boolean): SortedSet<T>

	override fun tailSet(after: T, inclusive: Boolean): SortedSet<T>
}

interface MutatingSortedSetView<T>: Sorted<T>, MutatingSequencedSetView<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedSetView<T>

	override fun headSet(before: T, inclusive: Boolean): MutatingSortedSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): MutatingSortedSetView<T>
}

interface RemoveOnlySortedSet<T>: MutatingSortedSetView<T>, RemoveOnlySequencedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedSet<T>
}

interface MutableSortedSet<T>: RemoveOnlySortedSet<T>, MutableSequencedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableSortedSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedSet<T>
}
