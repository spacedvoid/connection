/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A map view.
 * Base interface for the Connection API.
 *
 * Elements in this collection are called *entries*, which hold a key-value pair.
 * These 'elements' can be obtained by [entries].
 * Entries are unique by their keys; two entries in a map cannot have the same key.
 *
 * Whether a key matches another key is determined via [Any.equals].
 *
 * The mutability of this map is not defined.
 * This map might be mutable, and might also be self-mutating.
 *
 * Thread-safety is not defined, unless the implementation ensures such.
 * As such, the behavior of operations when involved in a data race is not defined.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface MapView<K, out V> {
	/**
	 * Returns the size of this map.
	 */
	fun size(): Int

	/**
	 * Returns `true` if this map is empty, `false` otherwise.
	 */
	fun isEmpty(): Boolean

	/**
	 * Returns `true` if an entry has the given [key] in this map, `false` otherwise.
	 */
	fun containsKey(key: K): Boolean

	/**
	 * Returns `true` if an entry has the given [value] in this map, `false` otherwise.
	 */
	fun containsValue(value: @UnsafeVariance V): Boolean

	/**
	 * Returns the value of the entry associated with the given [key], or `null` if no entries have the [key].
	 */
	operator fun get(key: K): V?

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 */
	val keys: SetView<K>

	/**
	 * Returns a collection that reflects the values that this map contains.
	 */
	val values: CollectionView<V>

	/**
	 * Returns a collection that reflects the entries that this map contains.
	 *
	 * Although this uses [kotlin.collections.Map.Entry] for compatibility,
	 * it must not be casted to [kotlin.collections.MutableMap.MutableEntry].
	 */
	val entries: SetView<Map.Entry<K, V>>

	/**
	 * Returns whether the given object is equal to this map.
	 *
	 * The given object is equal to this map if the object is a [MapView],
	 * and all entries in this map are equal to the entries in the given map, in terms of both keys and values.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this map.
	 *
	 * The hash code is computed based on the hash codes from the entries in this map,
	 * which are then based on the hash codes of the key and value.
	 */
	override fun hashCode(): Int
}

/**
 * An immutable map.
 */
interface Map<K, out V>: MapView<K, V> {
	override val keys: Set<K>

	override val values: Collection<V>

	override val entries: Set<Map.Entry<K, V>>
}

/**
 * A mutable map.
 */
interface MutableMap<K, V>: MapView<K, V> {
	/**
	 * Puts a new entry to this map and returns `null`,
	 * or replaces the value of the entry associated with the given [key] by the [value] and returns the old value.
	 */
	fun put(key: K, value: V): V?

	/**
	 * For all entries in the given [map],
	 * copies the entry to this map or replaces the value of the entry in this map associated with the key by the entry's value.
	 */
	fun putAll(map: MapView<out K, V>)

	/**
	 * Removes the entry associated with the given [key] and returns the value, or returns `null` if no entries have the [key].
	 */
	fun remove(key: K): V?

	/**
	 * Removes all entries in this map.
	 */
	fun clear()

	override val keys: RemoveOnlySet<K>

	override val values: RemoveOnlyCollection<V>

	override val entries: RemoveOnlySet<MutableMap.MutableEntry<K, V>>
}
