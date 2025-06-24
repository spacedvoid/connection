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
 * The mutability of this collection is not defined.
 * This collection might be mutable, and might also be self-mutating.
 *
 * Thread-safety is not defined, unless the implementation ensures such.
 * As such, the behavior of operations when involved in a data race is not defined.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface CollectionView<T>: Iterable<T> {
	/**
	 * Returns a new iterator for this collection.
	 *
	 * The iteration order is not defined, and might not be consistent.
	 *
	 * The iterator may, but is not required to, throw [ConcurrentModificationException]
	 * if the collection is modified while it is in use.
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
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this collection ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this collection is modified while it is in use.
	 *
	 * Any other characteristics are implementation-defined,
	 * such as [Spliterator.DISTINCT] for [SetView] and [Spliterator.IMMUTABLE] for [Collection].
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Returns the size of this collection.
	 */
	fun size(): Int

	/**
	 * Returns `true` if this collection is empty, `false` otherwise.
	 */
	fun isEmpty(): Boolean

	/**
	 * Returns `true` if this collection contains the given [element], `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	operator fun contains(element: T): Boolean

	/**
	 * Returns `true` if this collection contains all elements from the given [collection], `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun containsAll(collection: CollectionView<out T>): Boolean

	/**
	 * Returns whether the given object is equal to this collection.
	 *
	 * This specification is very vague, simply because [ListView] and [SetView] have conflicting overrides.
	 * While there should really be no direct implementations of [CollectionView] or its subkinds,
	 * such cases should have careful implementations to obey the [general contracts][Any.equals] of this method.
	 * For element-wise equality, use [containsAll].
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this collection.
	 *
	 * This specification is very vague, simply because [ListView] and [SetView] have conflicting overrides.
	 * While there should really be no direct implementations of [CollectionView] or its subkinds,
	 * such cases should have careful implementations to obey the [general contracts][Any.hashCode] of this method.
	 */
	override fun hashCode(): Int

	//<editor-fold defaultState="collapsed" desc="// Hidden overrides, deprecated since these become problematic when generating documentation">
	/*
	 * Since kotlin.collections.forEach is annotated with @HidesMembers, this method won't be selected.
	 * Compile with `-Xjvm-default=all-compatibility` in K1, although this will be the default behavior in K2, as this will be fixed at 2.2.0.
	 */
	@Deprecated("This method should not be used. Use Kotlin's forEach instead.", ReplaceWith("forEach { action(it) }"), DeprecationLevel.HIDDEN)
	override fun forEach(action: Consumer<in T>?) = super.forEach(action)
	//</editor-fold>
}

/**
 * An immutable collection.
 */
interface Collection<T>: CollectionView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.IMMUTABLE] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>
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
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this collection ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
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
	 * Removes all elements from this collection which matches the given [predicate].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * This method should be used in place of [MutableIterable.removeAll],
	 * since the implementation may use a better strategy.
	 */
	fun removeIf(predicate: (T) -> Boolean): Boolean

	/**
	 * Removes all elements from this collection which are also contained in the given [collection].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun removeAll(collection: CollectionView<out T>): Boolean

	/**
	 * Removes all elements from this collection which are not contained in the given [collection].
	 * Returns `true` if any elements were removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches an element is determined via [Any.equals].
	 */
	fun retainAll(collection: CollectionView<out T>): Boolean

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
	 * Adds all elements from the given [collection] to this collection.
	 * Returns `true` if the addition changed this collection, `false` otherwise.
	 */
	fun addAll(collection: CollectionView<out T>): Boolean
}
