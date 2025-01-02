package io.github.spacedvoid.connection

/**
 * A [SortedNavigableSetView] that optimizes some operations.
 */
interface NavigableSetView<T>: SortedNavigableSetView<T> {
	override fun reversed(): NavigableSetView<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T>

	override fun higher(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.ceiling(than) else this.kotlin.higher(than)

	override fun lower(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.floor(than) else this.kotlin.lower(than)

	override val CollectionView<T>.kotlin: java.util.NavigableSet<T>
}

/**
 * An immutable [NavigableSetView].
 */
interface NavigableSet<T>: SortedNavigableSet<T>, NavigableSetView<T> {
	override fun reversed(): NavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

/**
 * A [NavigableSetView] that additionally supports element removal operations.
 */
interface RemoveOnlyNavigableSet<T>: RemoveOnlySortedNavigableSet<T>, NavigableSetView<T> {
	override fun reversed(): RemoveOnlyNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

/**
 * A [RemoveOnlyNavigableSet] that additionally supports element addition operations.
 */
interface MutableNavigableSet<T>: MutableSortedNavigableSet<T>, RemoveOnlyNavigableSet<T> {
	override fun reversed(): MutableNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
