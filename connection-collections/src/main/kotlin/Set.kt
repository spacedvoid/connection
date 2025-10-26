/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A collection view that only stores one element among equal objects.
 *
 * This is equivalent to a mathematical set, where no two elements in this set are equal to each other.
 *
 * The behavior of this set when mutable elements are used, and their mutation affects the result of [Any.equals], is undefined.
 *
 * Whether an element matches another object is determined via [Any.equals].
 */
interface SetView<out T>: CollectionView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.DISTINCT] are reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the object is a [SetView],
	 * the two sets have the same size,
	 * and this set contains all elements from the given set.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this set.
	 *
	 * For consistency between implementations, the result must be equal to
	 * ```kotlin
	 * sumOf { it.hashCode() }
	 * ```
	 */
	override fun hashCode(): Int
}

/**
 * An immutable set.
 */
interface Set<out T>: SetView<T>, Collection<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE] and [Spliterator.DISTINCT] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>
}

/**
 * A mutable set that only supports element removal operations.
 */
interface RemoveOnlySet<T>: SetView<T>, RemoveOnlyCollection<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.DISTINCT] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * If the spliterator does not report [Spliterator.CONCURRENT], it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>
}

/**
 * A mutable set.
 *
 * When an object which already matches an element in the set is added,
 * the contained element is not replaced, and the set remains unchanged.
 */
interface MutableSet<T>: RemoveOnlySet<T>, MutableCollection<T> {
	/**
	 * Adds the given [element] to this set and returns `true` if it was not in this set,
	 * returns `false` otherwise.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced, and this set remains unchanged.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this set by their encounter order
	 * if they were not in this set.
	 * Returns `true` if any elements were added, `false` otherwise.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced, and this set remains unchanged.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean
}
