/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator

/**
 * A collection view that only stores one instance per element.
 *
 * This is equivalent to a mathematical set, where no two objects stored in this set are [equal][Any.equals] to each other.
 *
 * The behavior of the set when mutable elements are used, when their mutation affects the result of [Any.equals], is undefined.
 *
 * All operations that check whether an element matches an instance is determined via [Any.equals].
 */
interface SetView<T>: CollectionView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.DISTINCT] are reported by default.
	 * Also, the spliterator must either report
	 * - [Spliterator.IMMUTABLE]
	 * - [Spliterator.CONCURRENT]; or
	 * - be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this set ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>

	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is a [SetView],
	 * this set contains all elements from the given set,
	 * and the given set contains all elements from this set.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this set.
	 *
	 * The hash code is computed based on the contained objects' hash codes.
	 */
	override fun hashCode(): Int
}

/**
 * An immutable set.
 */
interface Set<T>: Collection<T>, SetView<T> {
	/**
	 * Returns a new spliterator for this collection.
	 *
	 * The characteristics [Spliterator.SIZED], [Spliterator.IMMUTABLE] and [Spliterator.DISTINCT] are reported by default.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>
}

/**
 * A mutable set that only supports element removal operations.
 */
interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, SetView<T> {
	/**
	 * Returns a new spliterator for this set.
	 *
	 * The characteristics [Spliterator.SIZED] and [Spliterator.DISTINCT] are reported by default.
	 * Also, the spliterator must either report [Spliterator.CONCURRENT] or be *[late-binding][Spliterator]*.
	 *
	 * The spliterator does not report [Spliterator.CONCURRENT]
	 * unless the implementation of this set ensures such.
	 * When the spliterator does not report such, it may, but is not required to,
	 * throw [ConcurrentModificationException] if this set is modified while it is in use.
	 */
	@StreamSupport
	override fun spliterator(): Spliterator<T>
}

/**
 * A mutable set.
 *
 * When an element which already matches an instance in the set is added,
 * the contained instance is not replaced, and the set remains unchanged.
 */
interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T> {
	/**
	 * Adds the given [element] to this set.
	 * Returns `true` if the addition changed this set, `false` otherwise.
	 *
	 * When an element which already matches an instance in this set is added,
	 * the contained instance is not replaced, and this set remains unchanged.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [set] to this set.
	 * Returns `true` if the addition changed this set, `false` otherwise.
	 *
	 * When an element which already matches an instance in this set is added,
	 * the contained instance is not replaced, and this set remains unchanged.
	 */
	override fun addAll(collection: CollectionView<out T>): Boolean
}
