/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A mutable sequenced set that defines the iteration order as the addition order.
 *
 * Note that this type does not define immutable or view collection kinds,
 * simply because they would not be different with sequenced sets.
 */
interface LinkedSet<T>: MutableSequencedSet<T> {
	override fun reversed(): LinkedSet<T>

	/**
	 * Adds the given [element] to the start of this set.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the start of this set.
	 */
	fun addFirst(element: T)

	/**
	 * Adds the given [element] to the end of this set.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the end of this set.
	 *
	 * This method is equivalent with [add].
	 */
	fun addLast(element: T) {
		add(element)
	}

	/**
	 * Adds all elements from the given [collection] to the start of this set by their encounter order.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the start of this set.
	 */
	fun addAllFirst(collection: CollectionView<T>)

	/**
	 * Adds all elements from the given [collection] to the end of this set by their encounter order.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the end of this set.
	 *
	 * This method is equivalent with [addAll].
	 */
	fun addAllLast(collection: CollectionView<T>) {
		addAll(collection)
	}

	/**
	 * Adds the given [element] to the end of this set.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When the [element] already matches an element in this set,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the end of this set.
	 */
	override fun add(element: T): Boolean

	/**
	 * Adds all elements from the given [collection] to the end of this set by their encounter order.
	 * Returns `true` if this addition changed this set, `false` otherwise.
	 *
	 * When an object which already matches an element in this set is added,
	 * the contained element is not replaced and this set remains unchanged,
	 * but it will be relocated to the end of this set.
	 */
	override fun addAll(collection: CollectionView<T>): Boolean
}
