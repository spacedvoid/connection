/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("DEPRECATION")

package io.github.spacedvoid.connection

/**
 * A [SequencedCollectionView] that supports element retrieval by indexes.
 * The index ranges from `0` to [size]` - 1`, inclusive.
 *
 * Although there are no specification-level restrictions for the maximum size of the list,
 * this API restricts the retrieval of elements outside the non-negative integer index.
 * Element addition, removal, modification, or access operations outside such index may throw any type of exceptions.
 */
interface ListView<T>: SequencedCollectionView<T> {
	/**
	 * Returns a new iterator for this list.
	 *
	 * The iteration order is based on the index.
	 */
	override operator fun iterator(): ListIterator<T> =
		this.kotlin.listIterator()

	override fun reversed(): ListView<T>

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
	 * Returns whether the given object is equal to this list.
	 *
	 * The given object is equal to this list if the object is a [ListView],
	 * and the elements of the given list equals to the elements in this list, regarding the order.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns a hash code for this list.
	 *
	 * The hash is computed based on the contained objects' hash codes, regarding the order.
	 */
	override fun hashCode(): Int

	// On the JVM, kotlin.collections.List = java.util.List, which inherits from java.util.SequencedCollection
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.List<T>
}

/**
 * An immutable [ListView].
 */
interface List<T>: ListView<T>, SequencedCollection<T> {
	override fun reversed(): List<T>

	override fun subList(startInclusive: Int, endExclusive: Int): List<T>

	// Force override, iterator() cannot resolve type as kotlin.collections.List<T>
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
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

	override fun reversed(): MutableList<T>

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T>

	/**
	 * Adds the given [element] to the end of this list, and returns `true`.
	 */
	override fun add(element: T): Boolean =
		super.add(element)

	/**
	 * Inserts the given [element] to the given [index].
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	fun add(index: Int, element: T) =
		this.kotlin.add(index, element)

	/**
	 * Adds all elements from the given [collection] to the end of this list by their encounter order,
	 * and returns `true`.
	 */
	override fun addAll(collection: CollectionView<out T>): Boolean =
		super.addAll(collection)

	/**
	 * Adds the given [element] to the beginning of this list.
	 *
	 * This method is equivalent with `add(0, element)`.
	 */
	fun addFirst(element: T) =
		this.kotlin.addFirst(element)

	/**
	 * Adds the [element] to the end of this list.
	 *
	 * This method is equivalent with [add].
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
	 * Removes the first occurrence of the given [element], which has the lowest index.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean =
		super.remove(element)

	/**
	 * Removes and returns the element at the [index].
	 * Throws [IndexOutOfBoundsException] if the [index] is out of range.
	 */
	fun removeAt(index: Int): T =
		this.kotlin.removeAt(index)

	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: kotlin.collections.MutableList<T>
}
