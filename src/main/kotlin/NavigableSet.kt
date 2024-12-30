package io.github.spacedvoid.connection

/**
 * A [SortedNavigableSetView] that optimizes some operations.
 */
interface NavigableSetView<T>: SortedNavigableSetView<T> {
	override fun reverse(): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to]
	 * or if this set has a restricted range and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Mutating the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * Since elements in this collection are compared by the [comparator],
	 * the returned subset's [first] and [last] element might not be equal to the given arguments.
	 * In such cases, the range is defined by the actual elements in this collection.
	 *
	 * The behavior of the returned collection when this collection is modified by operations on this collection in any way is not defined.
	 */
	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [before] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Mutating the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * Since elements in this collection are compared by the [comparator],
	 * the returned subset's [last] element might not be equal to the given argument.
	 * In such cases, the range is defined by the actual elements in this collection.
	 *
	 * The behavior of the returned collection when this collection is modified by operations on this collection in any way is not defined.
	 */
	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Mutating the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * Since elements in this collection are compared by the [comparator],
	 * the returned subset's [first] element might not be equal to the given argument.
	 * In such cases, the range is defined by the actual elements in this collection.
	 *
	 * The behavior of the returned collection when this collection is modified by operations on this collection in any way is not defined.
	 */
	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 */
	override fun higher(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.ceiling(than) else this.kotlin.higher(than)

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 */
	override fun lower(than: T, inclusive: Boolean): T? =
		if(inclusive) this.kotlin.floor(than) else this.kotlin.lower(than)

	override val CollectionView<T>.kotlin: java.util.NavigableSet<T>
}

/**
 * An immutable [NavigableSetView].
 */
interface NavigableSet<T>: SortedNavigableSet<T>, NavigableSetView<T> {
	override fun reverse(): NavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T>
}

/**
 * A [NavigableSetView] that additionally supports element removal operations.
 */
interface RemoveOnlyNavigableSet<T>: RemoveOnlySortedNavigableSet<T>, NavigableSetView<T> {
	override fun reverse(): RemoveOnlyNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T>
}

/**
 * A [RemoveOnlyNavigableSet] that additionally supports element addition operations.
 */
interface MutableNavigableSet<T>: MutableSortedNavigableSet<T>, RemoveOnlyNavigableSet<T> {
	override fun reverse(): MutableNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T>
}
