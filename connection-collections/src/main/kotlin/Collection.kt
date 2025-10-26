/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator
import java.util.function.Consumer

/**
 * A collection view.
 * Base interface for the Connection API.
 *
 * **Note:** This type should not be directly implemented or used, as operations might fail to satisfy any intentions.
 * The specifications are very vague so this type can be used for all collection-based operations.
 *
 * A collection contains *elements*, which are obtainable by using the [iterator].
 * The number of elements that the iterator will return is equal to this collection's [size].
 *
 * The mutability of this collection is not defined.
 * This collection might be mutable, and might also be self-mutating.
 *
 * The behavior of operations are not defined while another operation modifies this collection.
 * Also, unless the implementation ensures thread-safety,
 * the behavior of operations when involved in a data race is not defined.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface CollectionView<out T>: Iterable<T> {
	/**
	 * Returns a new iterator for this collection.
	 *
	 * The iteration order is not defined, and might not be consistent.
	 *
	 * The iterator may, but is not required to, throw [ConcurrentModificationException]
	 * if this collection is modified while it is in use.
	 */
	override operator fun iterator(): Iterator<T>

	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristic [Spliterator.SIZED] is reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this collection is modified while it is in use.
	 *
	 * Any other characteristics are implementation-defined,
	 * such as [Spliterator.DISTINCT] for [SetView] and [Spliterator.IMMUTABLE] for [Collection].
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	/**
	 * Returns the size of this collection.
	 * If this collection contains more than [Int.MAX_VALUE] elements, returns [Int.MAX_VALUE].
	 */
	fun size(): Int

	/**
	 * Returns `true` if this collection is empty, `false` otherwise.
	 *
	 * A collection is empty if its [size] is `0`:
	 * therefore, this method is equivalent with `size() == 0`.
	 */
	fun isEmpty(): Boolean

	/**
	 * Returns `true` if this collection contains the given [element], `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	operator fun contains(element: @UnsafeVariance T): Boolean

	/**
	 * Returns `true` if this collection contains all elements from the given [collection], `false` otherwise.
	 *
	 * Whether an element in this collection matches another element in the given [collection] is determined via [Any.equals].
	 */
	fun containsAll(collection: CollectionView<@UnsafeVariance T>): Boolean

	/**
	 * Returns whether the given object is equal to this collection.
	 *
	 * This specification is very vague, simply because [CollectionView] is the base type for all kinds of collections.
	 * Although implementations will perform element-wise comparisons(possibly including their order) on their best efforts basis,
	 * the result might not satisfy obvious cases.
	 * For exact element-wise comparisons including their order, use [List.equals].
	 *
	 * Still, the [basic contracts of `equals`][Any.equals] remains the same.
	 *
	 * @see ListView.equals
	 * @see SetView.equals
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this collection.
	 *
	 * This specification is very vague, simply because [CollectionView] is the base type for all kinds of collections.
	 *
	 * Still, the [basic contracts of `hashCode`][Any.hashCode] remains the same.
	 */
	override fun hashCode(): Int

	//<editor-fold defaultState="collapsed" desc="// Hidden overrides, suppressed since these become problematic when generating documentation">
	/*
	 * Since kotlin.collections.forEach is annotated with @HidesMembers, this method won't be selected.
	 * Compile with `-Xjvm-default=all-compatibility` in K1 or K2 before 2.2.0.
	 */
	/**
	 * @suppress
	 */
	@Suppress("RedundantOverride")
	override fun forEach(action: Consumer<in T>?) = super.forEach(action)
	//</editor-fold>
}

/**
 * An immutable collection.
 */
interface Collection<out T>: CollectionView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.IMMUTABLE] are reported by default.
	 *
	 * Any other characteristics are implementation-defined,
	 * such as [Spliterator.DISTINCT] for [SetView] and [Spliterator.IMMUTABLE] for [Collection].
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>
}

/**
 * A mutable collection that only supports element removal operations.
 */
interface RemoveOnlyCollection<T>: CollectionView<T>, MutableIterable<T> {
	override fun iterator(): MutableIterator<T>

	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristic [Spliterator.SIZED] is reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this collection is modified while it is in use.
	 *
	 * Any other characteristics are implementation-defined,
	 * such as [Spliterator.DISTINCT] for [SetView] and [Spliterator.IMMUTABLE] for [Collection].
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Removes a single occurrence of the given [element] from this collection.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	fun remove(element: T): Boolean

	/**
	 * Removes all elements from this collection that are also contained in the given [collection].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element in the given [collection] is determined via [Any.equals].
	 */
	fun removeAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes all elements from this collection that are not contained in the given [collection].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element in the given [collection] is determined via [Any.equals].
	 */
	fun retainAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes all elements in this collection.
	 */
	fun clear()
}

/**
 * A mutable collection.
 */
interface MutableCollection<T>: RemoveOnlyCollection<T> {
	/**
	 * Adds the given [element] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this collection by their encounter order.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	fun addAll(collection: CollectionView<T>): Boolean
}
