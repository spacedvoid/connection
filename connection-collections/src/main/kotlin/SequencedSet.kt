/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A set view that additionally defines the iteration order.
 */
interface SequencedSetView<out T>: SequencedCollectionView<T>, SetView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.DISTINCT], and [Spliterator.ORDERED] are reported by default.
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

	override fun reversed(): SequencedSetView<T>

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is also a [SetView],
	 * this set contains all elements in the given set,
	 * and the given set also contains all elements in this set.
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
 * An immutable sequenced set.
 */
interface SequencedSet<out T>: SequencedCollection<T>, Set<T>, SequencedSetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE], [Spliterator.DISTINCT], and [Spliterator.ORDERED] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T>

	override fun reversed(): SequencedSet<T>

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is also a [SetView],
	 * this set contains all elements in the given set,
	 * and the given set also contains all elements in this set.
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
 * A mutable sequenced set that only supports element removal operations.
 */
interface RemoveOnlySequencedSet<T>: RemoveOnlySequencedCollection<T>, RemoveOnlySet<T>, SequencedSetView<T> {
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

	override fun reversed(): RemoveOnlySequencedSet<T>

	/**
	 * Removes a single occurrence of the given [element] from this collection.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is also a [SetView],
	 * this set contains all elements in the given set,
	 * and the given set also contains all elements in this set.
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
 * A mutable sequenced set.
 */
interface MutableSequencedSet<T>: MutableSequencedCollection<T>, MutableSet<T>, RemoveOnlySequencedSet<T> {
	override fun reversed(): MutableSequencedSet<T>

	/**
	 * Adds the given [element] to this set.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced, and this set remains unchanged.
	 *
	 * The addition is not strictly determined; it may add to the first, last, or any position.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to this set by their encounter order.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced, and this set remains unchanged.
	 *
	 * The additions are not strictly determined;
	 * it may add to the first, last, or any position, even for consecutive elements in the given [collection].
	 */
	override fun addAll(collection: CollectionView<T>): Boolean

	/**
	 * Removes a single occurrence of the given [element] from this collection.
	 * Returns `true` if an element was removed, `false` otherwise.
	 *
	 * Whether an element in this collection matches the given [element] is determined via [Any.equals].
	 */
	override fun remove(element: T): Boolean

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is also a [SetView],
	 * this set contains all elements in the given set,
	 * and the given set also contains all elements in this set.
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
