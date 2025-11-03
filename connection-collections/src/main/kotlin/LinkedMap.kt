/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A mutable sequenced map that defines the entry iteration order as the addition order.
 *
 * Note that this type does not define immutable or view collection kinds,
 * simply because they would not be different with sequenced maps.
 */
interface LinkedMap<K, V>: MutableSequencedMap<K, V> {
	override fun reversed(): LinkedMap<K, V>

	/**
	 * Associates the given [key] with the [value] at the start of this map.
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the entry has the given [key],
	 * but the entry will be relocated to the start of this map.
	 */
	fun putFirst(key: K, value: V): V?

	/**
	 * Associates the given [key] with the [value] at the end of this map.
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the entry has the given [key],
	 * but the entry will be relocated to the end of this map.
	 *
	 * This method is equivalent with [put].
	 */
	fun putLast(key: K, value: V): V? = put(key, value)

	/**
	 * Copies all entries from the given [map] to the start of this map by their encounter order.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key,
	 * but the entry will be relocated to the start of this map.
	 */
	fun putAllFirst(map: MapView<out K, V>)

	/**
	 * Copies all entries from the given [map] to the end of this map by their encounter order.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key,
	 * but the entry will be relocated to the end of this map.
	 *
	 * This method is equivalent with [putAll].
	 */
	fun putAllLast(map: MapView<out K, V>) {
		putAll(map)
	}

	/**
	 * Associates the given [key] with the [value] at the end of this map.
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the given entry has the given [key],
	 * but the entry will be relocated to the end of this map.
	 */
	override fun put(key: K, value: V): V?

	/**
	 * Copies all entries from the given [map] to the end of this map by their encounter order.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key,
	 * but the entry will be relocated to the end of this map.
	 */
	override fun putAll(map: MapView<out K, V>)
}
