package io.github.spacedvoid.connection

import java.util.function.Consumer

/**
 * A collection view.
 * Base interface for the Connection API.
 *
 * The mutability of this collection is not defined.
 * This collection might be mutable, and might also be self-mutating.
 *
 * Thread-safety is not defined, unless the underlying collection ensures such.
 * As such, the behavior of operations when involved in a data race is not defined.
 *
 * The [iterator] may, but is not required to, throw [ConcurrentModificationException]
 * if the collection is modified while it is in use.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface CollectionView<T>: Iterable<T> {
	/**
	 * Returns a new iterator for this collection.
	 *
	 * The iteration order is not defined, and might not be consistent.
	 */
	override operator fun iterator(): Iterator<T> =
		this.kotlin.iterator()

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

	//<editor-fold defaultState="collapsed" desc="// Hidden overrides, deprecated since these become problematic when generating documentation">
	/*
	 * Since kotlin.collections.forEach is annotated with @HidesMembers, this method won't be selected.
	 * Compile with `-Xjvm-default=all-compatibility` in K1, although this will be the default behavior in K2, as this will be fixed at 2.2.0.
	 */
	@Deprecated("This method is problematic when generating documentation via Dokka.", ReplaceWith("forEach { action(it) }", "kotlin.collections.forEach"), level = DeprecationLevel.HIDDEN)
	override fun forEach(action: Consumer<in T>?) = super.forEach(action)
	//</editor-fold>
}

/**
 * An immutable collection.
 */
interface Collection<T>: CollectionView<T>

/**
 * A mutable collection that only supports element removal operations.
 */
interface RemoveOnlyCollection<T>: CollectionView<T>, MutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

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
	 * Removes all occurrences of the elements that match the given [predicate].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * @apiNote
	 * This method should be used in place of [kotlin.collections.removeAll],
	 * since the implementation may use a better strategy.
	 */
	fun removeIf(predicate: (T) -> Boolean): Boolean =
		this.kotlin.removeIf(predicate)

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
