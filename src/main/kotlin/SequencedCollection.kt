package io.github.spacedvoid.connection

/**
 * A [CollectionView] that additionally defines the iteration order.
 */
interface SequencedCollectionView<T>: CollectionView<T> {
	/**
	 * Returns a reverse-ordered collection of this collection.
	 *
	 * Operations on the returned collection delegates to this collection.
	 */
	fun reverse(): SequencedCollectionView<T>

	/**
	 * Returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun first(): T =
		this.kotlin.first

	/**
	 * Returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun last(): T =
		this.kotlin.last

	override val CollectionView<T>.kotlin: java.util.SequencedCollection<T>
}

/**
 * An immutable [SequencedCollectionView].
 */
interface SequencedCollection<T>: Collection<T>, SequencedCollectionView<T>{
	/**
	 * Returns an [Iterator] for this collection.
	 *
	 * The iteration order is defined by the specification.
	 */
	override fun iterator(): Iterator<T> =
		this.kotlin.iterator()

	override fun reverse(): SequencedCollection<T>
}

/**
 * A [SequencedCollectionView] that additionally supports element removal operations.
 */
interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, SequencedCollectionView<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	/**
	 * Removes and returns the first element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeFirst(): T =
		this.kotlin.removeFirst()

	/**
	 * Removes and returns the last element of this collection, defined by the iteration order.
	 * Throws [NoSuchElementException] if this collection is empty.
	 */
	fun removeLast(): T =
		this.kotlin.removeLast()
}

/**
 * A [RemoveOnlySequencedCollection] that additionally supports element addition operations.
 *
 * @apiNote
 * This class does not define `addFirst` and `addLast` operations;
 * most subtypes of this collection(namely [MutableNavigableSet]) manages the iteration order by other criteria.
 */
interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
