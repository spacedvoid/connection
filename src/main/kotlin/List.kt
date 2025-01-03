package io.github.spacedvoid.connection

/**
 * A [SequencedCollectionView] that supports element retrieval by indexes.
 * The index ranges from `0` to `size - 1`, inclusive.
 */
interface ListView<T>: SequencedCollectionView<T> {
	/**
	 * Returns a new iterator for this list.
	 *
	 * The iteration order is based on the index.
	 */
	override operator fun iterator(): ListIterator<T> =
		this.kotlin.listIterator()

	override fun reverse(): ListView<T>

	/**
	 * Returns a sublist of this list, in the given range.
	 * Throws [IndexOutOfBoundsException] if [startInclusive] is less than `0`,
	 * [endExclusive] is greater than this list's size,
	 * or [startInclusive] is greater than [endExclusive].
	 *
	 * Operations on the returned list is delegated to this list.
	 *
	 * The behavior of the returned list when this list is *structurally modified*(that is, changing the size of this list)
	 * by operations on this list is not defined.
	 */
	fun subList(startInclusive: Int, endExclusive: Int): ListView<T>

	/**
	 * Returns the element at the given [index].
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	operator fun get(index: Int): T =
		this.kotlin[index]

	/**
	 * Returns the index of the first occurrence of the given [element], or `-1` if the [element] is not in this list.
	 */
	fun indexOf(element: T): Int =
		this.kotlin.indexOf(element)

	/**
	 * Returns the index of the last occurrence of the given [element], or `-1` if the [element] is not in this list.
	 */
	fun lastIndexOf(element: T): Int =
		this.kotlin.lastIndexOf(element)

	/**
	 * @implNote
	 * @inheritDoc
	 *
	 * Implementations might require `@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")`.
	 */
	// On the JVM, kotlin.collections.List = java.util.List, which inherits from java.util.SequencedCollection.
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.List<T>
}

/**
 * An immutable [ListView].
 */
interface List<T>: ListView<T>, SequencedCollection<T> {
	override fun reverse(): List<T>

	override fun subList(startInclusive: Int, endExclusive: Int): List<T>

	// Force override, iterator() cannot resolve type as kotlin.collections.List<T>
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.List<T>
}

/**
 * A [ListView] that additionally supports element addition and removal operations.
 */
interface MutableList<T>: ListView<T>, MutableSequencedCollection<T> {
	/**
	 * Returns a new iterator for this list.
	 *
	 * The iteration order is based on the index.
	 */
	override fun iterator(): MutableListIterator<T> =
		this.kotlin.listIterator()

	override fun reverse(): MutableList<T>

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T>

	/**
	 * Inserts the [element] to the given [index].
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	fun add(index: Int, element: T) =
		this.kotlin.add(index, element)

	/**
	 * Adds the [element] to the beginning of this list.
	 *
	 * @apiNote
	 * This method is equivalent with `add(0, element)`.
	 */
	fun addFirst(element: T) =
		this.kotlin.addFirst(element)

	/**
	 * Adds the [element] to the end of this list.
	 *
	 * @apiNote
	 * This method is equivalent with `add(size - 1, element)`.
	 */
	fun addLast(element: T) =
		this.kotlin.addLast(element)

	/**
	 * Replaces the element at the [index] with the given [element], and returns the old element.
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	operator fun set(index: Int, element: T): T =
		this.kotlin.set(index, element)

	/**
	 * Removes and returns the element at the [index].
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	fun removeAt(index: Int): T =
		this.kotlin.removeAt(index)

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.MutableList<T>
}
