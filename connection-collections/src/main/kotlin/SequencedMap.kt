/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A map view that additionally defines the iteration order of the [entries].
 */
interface SequencedMapView<K, out V>: MapView<K, V> {
	/**
	 * Returns a reverse-ordered map of this map.
	 *
	 * The iteration order of the map is the reverse of this map.
	 * Operations on the returned map delegates to this map.
	 */
	fun reversed(): SequencedMapView<K, V>

	/**
	 * Returns the first entry of this map defined by the iteration order, or `null` if this map is empty.
	 */
	fun first(): Pair<K, V>?

	/**
	 * Returns the last entry of this map defined by the iteration order, or `null` if this map is empty.
	 */
	fun last(): Pair<K, V>?

	/**
	 * Returns the key of the first entry of this map, defined by the iteration order.
	 * Throws [NoSuchElementException] if this map is empty.
	 */
	fun firstKey(): K

	/**
	 * Returns the key of the last entry of this map, defined by the iteration order.
	 * Throws [NoSuchElementException] if this map is empty.
	 */
	fun lastKey(): K

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 *
	 * The iteration order of the keys in the collection is equal to this map's [entries].
	 */
	override val keys: SequencedSetView<K>

	/**
	 * Returns a collection that reflects the values that this map contains.
	 *
	 * The iteration order of the values in the collection is equal to this map's [entries].
	 */
	override val values: SequencedCollectionView<V>

	override val entries: SequencedSetView<kotlin.collections.Map.Entry<K, V>>
}

/**
 * An immutable sequenced map.
 */
interface SequencedMap<K, out V>: SequencedMapView<K, V>, Map<K, V> {
	override fun reversed(): SequencedMap<K, V>

	override val keys: SequencedSet<K>

	override val values: SequencedCollection<V>

	override val entries: SequencedSet<kotlin.collections.Map.Entry<K, V>>
}

/**
 * A mutable sequenced map.
 *
 * Note that entry additions, mutations, or deletions might not depend on or affect the iteration order.
 */
interface MutableSequencedMap<K, V>: SequencedMapView<K, V>, MutableMap<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	/**
	 * Associates the given [key] with the [value].
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the entry has the given [key].
	 *
	 * The addition is not strictly determined; it may add to the first, last, or any position.
	 */
	override fun put(key: K, value: V): V?

	/**
	 * Copies all entries from the given [map] to this map.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key.
	 *
	 * The additions are not strictly determined;
	 * it may add to the first, last, or any position, even for consecutive entries in the given [map].
	 */
	override fun putAll(map: MapView<out K, V>)

	/**
	 * Removes and returns the first entry defined by the iteration order, or returns `null` if this map is empty.
	 */
	fun removeFirst(): Pair<K, V>?

	/**
	 * Removes and returns the last entry defined by the iteration order, or returns `null` if this map is empty.
	 */
	fun removeLast(): Pair<K, V>?

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 *
	 * The iteration order of the keys in the collection is equal to this map's [entries].
	 */
	override val keys: RemoveOnlySequencedSet<K>

	/**
	 * Returns a collection that reflects the values that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 *
	 * The iteration order of the values in the collection is equal to this map's [entries].
	 */
	override val values: RemoveOnlySequencedCollection<V>

	/**
	 * Returns a collection that reflects the entries that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 */
	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
