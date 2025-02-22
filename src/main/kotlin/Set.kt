/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A [CollectionView] that only stores one instance per element.
 *
 * The behavior of the set when mutable elements are used, when their mutation affects the result of [Any.equals], is undefined.
 *
 * All operations that check whether an element matches an instance is determined via [Any.equals].
 */
interface SetView<T>: CollectionView<T> {
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

	override val CollectionView<T>.kotlin: kotlin.collections.Set<T>
}

/**
 * An immutable [SetView].
 */
interface Set<T>: Collection<T>, SetView<T>

/**
 * A [SetView] that additionally supports element removal operations.
 */
interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, SetView<T> {
	override val CollectionView<T>.kotlin: kotlin.collections.MutableSet<T>
}

/**
 * A [RemoveOnlySet] that additionally supports element addition operations.
 *
 * When an element which already matches an instance in the set is added,
 * the contained instance is not replaced, and the set remains unchanged.
 */
interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
