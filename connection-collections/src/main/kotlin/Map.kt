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
 * The number of elements that [keys], [values], and [entries] will return is equal to this map's [size].
 *
 * Entries are unique by their keys; no two entries in this map have equal keys.
 * Whether a key matches another key is determined via [Any.equals].
 *
 * The behavior of this map when mutable keys are used, and their mutation affects the result of [Any.equals], is undefined.
 *
 * The mutability of this map is not defined.
 * This map might be mutable, and might also be self-mutating.
 *
 * The behavior of operations are not defined while another operation modifies this map.
 * Also, unless the implementation ensures thread-safety,
 * the behavior of operations when involved in a data race is not defined.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface MapView<K, out V> {
	/**
	 * Returns the size of this map.
	 * If this map contains more than [Int.MAX_VALUE] entries, returns [Int.MAX_VALUE].
	 */
	fun size(): Int

	/**
	 * Returns `true` if this map is empty, `false` otherwise.
	 *
	 * A map is empty if its [size] is `0`:
	 * therefore, this method is equivalent with `size() == 0`.
	 */
	fun isEmpty(): Boolean

	/**
	 * Returns `true` if an entry has the given [key], `false` otherwise.
	 */
	fun containsKey(key: K): Boolean

	/**
	 * Returns `true` if an entry has the given [value], `false` otherwise.
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
	 */
	val entries: SetView<Map.Entry<K, V>>

	/**
	 * Returns whether the given object is equal to this map.
	 *
	 * The given object is equal to this map if the object is also a [MapView],
	 * the two maps have the same size,
	 * and all entries in this map are equal to the entries in the given map.
	 * This is equivalent with `this.entries.equals(other.entries)`.
	 *
	 * An entry is equal to another entry if and only if they have equal keys and values.
	 */
	override fun equals(other: Any?): Boolean

	/**
	 * Returns the hash code for this map.
	 *
	 * For consistency between implementations, the result must be equal to
	 * ```kotlin
	 * this.entries.sumOf { (k, v) -> k.hashCode() xor v.hashCode() }
	 * ```
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
 *
 * Note that similar to [MutableSet], an entry's key is not replaced when a new entry with the same key is added.
 */
interface MutableMap<K, V>: MapView<K, V> {
	/**
	 * Associates the given [key] with the [value].
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the entry has the given [key].
	 */
	fun put(key: K, value: V): V?

	/**
	 * Copies all entries from the given [map] to this map.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key.
	 */
	fun putAll(map: MapView<out K, V>)

	/**
	 * Removes the entry associated with the given [key] and returns its value, or returns `null` if no entries have the [key].
	 */
	fun remove(key: K): V?

	/**
	 * Removes all entries in this map.
	 */
	fun clear()

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 */
	override val keys: RemoveOnlySet<K>

	/**
	 * Returns a collection that reflects the values that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 */
	override val values: RemoveOnlyCollection<V>

	/**
	 * Returns a collection that reflects the entries that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 */
	override val entries: RemoveOnlySet<MutableMap.MutableEntry<K, V>>
}
