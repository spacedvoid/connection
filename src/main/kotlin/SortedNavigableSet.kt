package io.github.spacedvoid.connection

/**
 * A [SequencedSetView] that defines the iteration order of the elements based on a [Comparator].
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this collection is undefined.
 *
 * @apiNote
 * This type is not different with [NavigableSetView].
 * However, because this type was intended to migrate a [java.util.SortedSet] to a [java.util.NavigableSet],
 * operations on this collection makes no guarantees about performance and thread-safety.
 * If you can select the type of the collection, use [NavigableSetView] instead.
 */
interface SortedNavigableSetView<T>: SequencedSetView<T> {
	/**
	 * The comparator used to sort the elements in this collection.
	 */
	val comparator: Comparator<in T>

	override fun reversed(): SortedNavigableSetView<T>

	/**
	 * Returns a subset of this collection, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [before] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun headSet(before: T, inclusive: Boolean): SortedNavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Adding elements to the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 */
	fun tailSet(after: T, inclusive: Boolean): SortedNavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 */
	fun higher(than: T, inclusive: Boolean): T? =
		tailSet(than, inclusive).takeIf { !it.isEmpty() }?.first()

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 */
	fun lower(than: T, inclusive: Boolean): T? =
		headSet(than, inclusive).takeIf { !it.isEmpty() }?.first()

	override val CollectionView<T>.kotlin: java.util.SortedSet<T>
}

/**
 * An immutable [SortedNavigableSetView].
 *
 * @apiNote
 * This type is not different with [NavigableSet].
 * However, because this type was intended to migrate a [java.util.SortedSet] to a [java.util.NavigableSet],
 * operations on this collection makes no guarantees about performance and thread-safety.
 * If you can select the type of the collection, use [NavigableSet] instead.
 */
interface SortedNavigableSet<T>: SortedNavigableSetView<T>, SequencedSet<T> {
	override fun reversed(): SortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T>
}

/**
 * A [SortedNavigableSetView] that additionally supports element removal operations.
 *
 * @apiNote
 * This type is not different with [RemoveOnlyNavigableSet].
 * However, because this type was intended to migrate a [java.util.SortedSet] to a [java.util.NavigableSet],
 * operations on this collection makes no guarantees about performance and thread-safety.
 * If you can select the type of the collection, use [RemoveOnlyNavigableSet] instead.
 */
interface RemoveOnlySortedNavigableSet<T>: RemoveOnlySequencedSet<T>, SortedNavigableSetView<T> {
	override fun reversed(): RemoveOnlySortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>
}

/**
 * A [RemoveOnlySortedNavigableSet] that additionally supports element addition operations.
 *
 * @apiNote
 * This type is not different with [MutableNavigableSet].
 * However, because this type was intended to migrate a [java.util.SortedSet] to a [java.util.NavigableSet],
 * operations on this collection makes no guarantees about performance and thread-safety.
 * If you can select the type of the collection, use [MutableNavigableSet] instead.
 */
interface MutableSortedNavigableSet<T>: RemoveOnlySortedNavigableSet<T>, MutableSequencedSet<T> {
	override fun reversed(): MutableSortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T>
}
