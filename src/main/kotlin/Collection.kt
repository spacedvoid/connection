package io.github.spacedvoid.connection

/**
 * A collection view.
 * Base interface for the Connection API.
 *
 * The mutability of this collection is not defined.
 * This collection might be mutable, and might also be self-mutating.
 *
 * Thread-safety is not defined, unless the underlying collection ensures such.
 * As such, the behavior of operations when involved in a data race is not defined.
 * Additionally, this class (and specific subclasses) do not declare an [Iterator],
 * since they cannot consistently detect concurrent modifications.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface CollectionView<T> {
	/**
	 * Returns the size of this collection.
	 */
	fun size(): Int =
		this.kotlin.size

	/**
	 * Returns `true` if this collection is empty, `false` otherwise.
	 */
	fun isEmpty(): Boolean =
		this.kotlin.isEmpty()

	/**
	 * Returns `true` if this collection contains the given [element], `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	operator fun contains(element: T): Boolean =
		this.kotlin.contains(element)

	/**
	 * Returns `true` if this collection contains all elements in the given collection, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun containsAll(from: Collection<T>): Boolean =
		this.kotlin.containsAll(from.kotlin)

	/**
	 * Returns a direct Kotlin representation of this collection.
	 *
	 * @apiNote
	 * This extension property is not intended for external use; use [asKotlin] instead.
	 * In contexts that inherit or override this property, this extension can be called as
	 *
	 * ```kotlin
	 * this.kotlin
	 * ```
	 *
	 * In other rare cases where the use of this property is required, use
	 *
	 * ```kotlin
	 * with(connection) {
	 *     println(this.kotlin)
	 * }
	 * ```
	 *
	 * @implNote
	 * The implementation ***must not*** use the given receiver.
	 * Even though the receiver will always be `this`,
	 * always return the current class's Kotlin representation instead.
	 */
	val CollectionView<T>.kotlin: kotlin.collections.Collection<T>
}

/**
 * An immutable collection.
 * This class additionally provides an [Iterator].
 */
interface Collection<T>: CollectionView<T>, Iterable<T> {
	/**
	 * Returns an [Iterator] for this collection.
	 *
	 * The iteration order is not defined, and may not be consistent.
	 */
	override operator fun iterator(): Iterator<T> =
		this.kotlin.iterator()
}

/**
 * A mutable collection that only supports element removal operations.
 */
interface RemoveOnlyCollection<T>: CollectionView<T> {
	/**
	 * Removes a single occurrence of the given [element] from this collection.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 *
	 * The removal is not strictly determined; it may remove the first, last, or any occurrence.
	 */
	fun remove(element: T): Boolean =
		this.kotlin.remove(element)

	/**
	 * Removes all occurrences of the elements in this collection that are also contained in the given collection.
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun removeAll(from: Collection<T>): Boolean =
		this.kotlin.removeAll(from.kotlin.toSet())

	/**
	 * Removes all occurrences of the elements in this collection that are not contained in the given collection.
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun retainAll(from: Collection<T>): Boolean =
		this.kotlin.retainAll(from.kotlin.toSet())

	/**
	 * Removes all elements in this collection.
	 */
	fun clear() =
		this.kotlin.clear()

	override val CollectionView<T>.kotlin: kotlin.collections.MutableCollection<T>
}

/**
 * A mutable collection that additionally supports element addition operations.
 */
interface MutableCollection<T>: RemoveOnlyCollection<T> {
	/**
	 * Adds the given [element] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	fun add(element: T): Boolean =
		this.kotlin.add(element)

	/**
	 * Adds all elements in the given collection to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	fun addAll(from: Collection<T>): Boolean =
		this.kotlin.addAll(from.kotlin)
}
