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
	@Suppress("UNCHECKED_CAST")
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is SetView<*>) return false
		return size() == other.size() && containsAll(other as SetView<T>)
	}

	override fun hashCode(): Int = sumOf { it.hashCode() }
}

open class SetImpl<out T>(override val kotlin: kotlin.collections.Set<T>): Set<T>, SetViewImpl<T>(kotlin)

open class RemoveOnlySetImpl<T>(override val kotlin: kotlin.collections.MutableSet<T>): RemoveOnlySet<T>, RemoveOnlyCollectionImpl<T>(kotlin)

open class MutableSetImpl<T>(override val kotlin: kotlin.collections.MutableSet<T>): MutableSet<T>, MutableCollectionImpl<T>(kotlin)
