/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableSet
import io.github.spacedvoid.connection.Set

open class SetViewImpl<out T>(override val kotlin: kotlin.collections.Set<T>): SetView<T>, CollectionViewImpl<T>(kotlin) {
	/**
	 * Returns whether the given object is equal to this set.
	 *
	 * The given object is equal to this set if the given object is a [SetView],
	 * this set contains all elements from the given set,
	 * and the given set contains all elements from this set.
	 *
	 * This implementation uses the Java way, which is documented and implemented in [java.util.AbstractSet.equals].
	 */
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is SetView<*>) return false
		@Suppress("UNCHECKED_CAST")
		return size() == other.size() && containsAll(other as SetView<T>)
	}

	/**
	 * Returns the hash code for this set.
	 *
	 * The hash code is computed based on the contained objects' hash codes.
	 *
	 * This implementation uses the Java way, which is documented and implemented in [java.util.AbstractSet.hashCode].
	 */
	override fun hashCode(): Int = sumOf { it.hashCode() }
}

open class SetImpl<out T>(override val kotlin: kotlin.collections.Set<T>): Set<T>, SetViewImpl<T>(kotlin)

open class RemoveOnlySetImpl<T>(override val kotlin: kotlin.collections.MutableSet<T>): RemoveOnlySet<T>, RemoveOnlyCollectionImpl<T>(kotlin)

open class MutableSetImpl<T>(override val kotlin: kotlin.collections.MutableSet<T>): MutableSet<T>, MutableCollectionImpl<T>(kotlin)
