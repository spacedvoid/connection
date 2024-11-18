package io.github.spacedvoid.connection

interface SortedNavigableSetView<T>: SequencedSetView<T> {
	val comparator: Comparator<in T>?
		get() = this.kotlin.comparator()

	override fun reverse(): SortedNavigableSetView<T>

	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableSetView<T>

	fun headSet(before: T, inclusive: Boolean): SortedNavigableSetView<T>

	fun tailSet(after: T, inclusive: Boolean): SortedNavigableSetView<T>

	fun higher(than: T, inclusive: Boolean): T? =
		when {
			isEmpty() -> null
			inclusive -> tailSet(than, true).first()
			else -> reverse().headSet(than, false).last()
		}

	fun lower(than: T, inclusive: Boolean): T? =
		when {
			isEmpty() -> null
			inclusive -> reverse().tailSet(than, true).first()
			else -> headSet(than, false).last()
		}

	override val CollectionView<T>.kotlin: java.util.SortedSet<T>
}

interface SortedNavigableSet<T>: SequencedSet<T>, SortedNavigableSetView<T> {
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
