package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Navigable

interface NavigableSet<T>: Navigable<T>, SortedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>

	override fun higher(than: T, inclusive: Boolean): T?

	override fun lower(than: T, inclusive: Boolean): T?
}

interface MutatingNavigableSetView<T>: Navigable<T>, MutatingSortedSetView<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableSetView<T>

	override fun headSet(before: T, inclusive: Boolean): MutatingNavigableSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): MutatingNavigableSetView<T>

	override fun higher(than: T, inclusive: Boolean): T?

	override fun lower(than: T, inclusive: Boolean): T?
}

interface RemoveOnlyNavigableSet<T>: MutatingNavigableSetView<T>, RemoveOnlySortedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

interface MutableNavigableSet<T>: RemoveOnlyNavigableSet<T>, MutableSortedSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
