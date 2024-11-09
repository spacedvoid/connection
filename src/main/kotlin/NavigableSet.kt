package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Navigable

interface NavigableSetView<T>: SortedNavigableSetView<T>, Navigable<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T>
}

interface NavigableSet<T>: SortedNavigableSet<T>, NavigableSetView<T>, Navigable<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

interface RemoveOnlyNavigableSet<T>: RemoveOnlySortedNavigableSet<T>, NavigableSetView<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

interface MutableNavigableSet<T>: MutableSortedNavigableSet<T>, RemoveOnlyNavigableSet<T> {
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
