package io.github.spacedvoid.connection

/**
 * A [SequencedSetView] that defines the iteration order based on a [Comparator].
 * Elements inserted in this map must be comparable by the [comparator].
 * A [TypeCastException] is thrown if the element is not comparable.
 *
 * The [comparator] must be *consistent with equals*;
 * that is, `comparator.compare(a, b) == 0` if and only if `a.equals(b)`.
 * Otherwise, the behavior of this collection is not defined.
 *
 * @apiNote
 * This type is not different with [NavigableSetView];
 * however, because this type was intended to migrate a [java.util.SortedSet] to a [java.util.NavigableSet],
 * operations on this collection makes no guarantees about performance and thread-safety.
 * If you can select the type of the collection, use [NavigableSetView] instead.
 */
interface SortedNavigableSetView<T>: SequencedSetView<T> {
	/**
	 * The comparator used to sort the elements in this collection.
	 */
	val comparator: Comparator<in T>

	override fun reverse(): SortedNavigableSetView<T>

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
	 * The behavior of the returned collection when this collection is *structurally modified*(that is, changing the size of this collection)
	 * by operations on this collection is not defined.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [fromInclusive] is `true` and [toInclusive] is `false`;
	 * in such cases, this directly invokes [java.util.SortedSet.subSet].
	 */
	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSetView<T>

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
	 * The behavior of the returned collection when this collection is *structurally modified*(that is, changing the size of this collection)
	 * by operations on this collection is not defined.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [inclusive] is `false`;
	 * in such cases, this directly invokes [java.util.SortedSet.headSet].
	 */
	fun headSet(before: T, inclusive: Boolean): SortedNavigableSetView<T>

	/**
	 * Returns a subset of this collection, based on the given element.
	 * Throws [IllegalArgumentException] if this set has a restricted range and [after] lies outside the range.
	 *
	 * Operations on the returned collection is delegated to this collection.
	 * Mutating the returned collection throws [IllegalArgumentException] if the element is outside the range.
	 *
	 * Since elements in this collection are compared by the [comparator] (or their natural ordering),
	 * the returned subset's [first] element might not be equal to the given argument.
	 * In such cases, the range is defined by the actual elements in this collection.
	 *
	 * The behavior of the returned collection when this collection is *structurally modified*(that is, changing the size of this collection)
	 * by operations on this collection is not defined.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [inclusive] is `true`;
	 * in such cases, this directly invokes [java.util.SortedSet.tailSet].
	 */
	fun tailSet(after: T, inclusive: Boolean): SortedNavigableSetView<T>

	/**
	 * Returns the element higher than the given element, or `null` if there is no such element.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun higher(than: T, inclusive: Boolean): T? =
		when {
			isEmpty() -> null
			inclusive -> tailSet(than, true).first()
			else -> reverse().headSet(than, false).last()
		}

	/**
	 * Returns the element lower than the given element, or `null` if there is no such element.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun lower(than: T, inclusive: Boolean): T? =
		when {
			isEmpty() -> null
			inclusive -> reverse().tailSet(than, true).first()
			else -> headSet(than, false).last()
		}

	override val CollectionView<T>.kotlin: java.util.SortedSet<T>
}

/**
 * An immutable [SortedNavigableSetView].
 */
interface SortedNavigableSet<T>: SequencedSet<T>, SortedNavigableSetView<T> {
	override fun reverse(): SortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): SortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): SortedNavigableSet<T>
}

/**
 * A [SortedNavigableSetView] that additionally supports element removal operations.
 */
interface RemoveOnlySortedNavigableSet<T>: RemoveOnlySequencedSet<T>, SortedNavigableSetView<T> {
	override fun reverse(): RemoveOnlySortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlySortedNavigableSet<T>
}

/**
 * A [RemoveOnlySortedNavigableSet] that additionally supports element addition operations.
 */
interface MutableSortedNavigableSet<T>: MutableSequencedSet<T>, RemoveOnlySortedNavigableSet<T> {
	override fun reverse(): MutableSortedNavigableSet<T>

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableSet<T>

	override fun headSet(before: T, inclusive: Boolean): MutableSortedNavigableSet<T>

	override fun tailSet(after: T, inclusive: Boolean): MutableSortedNavigableSet<T>
}
