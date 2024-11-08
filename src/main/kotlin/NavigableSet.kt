package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Navigable

interface NavigableSet<T>: Navigable<T>, SortedNavigableSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

interface MutatingNavigableSetView<T>: Navigable<T>, MutatingSortedNavigableSetView<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableSetView<T>

	override fun headSet(before: T, inclusive: Boolean): MutatingNavigableSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): MutatingNavigableSetView<T>
}

interface RemoveOnlyNavigableSet<T>: MutatingNavigableSetView<T>, RemoveOnlySortedNavigableSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

interface MutableNavigableSet<T>: RemoveOnlyNavigableSet<T>, MutableSortedNavigableSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
