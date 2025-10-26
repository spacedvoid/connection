/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A sequenced map view that defines the iteration order of the entries based on a [Comparator].
 *
 * The iteration order is the ascending order, based on their [keys][java.util.Map.Entry.comparingByKey];
 * any entry is less than the entries that come ofter.
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this map is not defined.
 */
interface NavigableMapView<K, out V>: SequencedMapView<K, V> {
	/**
	 * The comparator used to determine the iteration order.
	 */
	val comparator: Comparator<in K>

	override fun reversed(): NavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range, and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the range before the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [before] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun headMap(before: K, inclusive: Boolean): NavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the range after the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [after] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun tailMap(after: K, inclusive: Boolean): NavigableMapView<K, V>

	/**
	 * Returns the entry higher than the given [key], or `null` if there is no such entry.
	 */
	fun higherEntry(key: K, inclusive: Boolean): Pair<K, V>?

	/**
	 * Returns the entry lower than the given [key], or `null` if there is no such entry.
	 */
	fun lowerEntry(key: K, inclusive: Boolean): Pair<K, V>?

	/**
	 * Returns the key of the entry higher than the given [key], or `null` if there is no such entry.
	 */
	fun higherKey(key: K, inclusive: Boolean): K?

	/**
	 * Returns the key of the entry lower the given [key], or `null` if there is no such entry.
	 */
	fun lowerKey(key: K, inclusive: Boolean): K?

	override val keys: NavigableSetView<K>

	override val values: SequencedCollectionView<V>

	override val entries: SequencedSetView<Map.Entry<K, V>>
}

/**
 * An immutable navigable map.
 */
interface NavigableMap<K, out V>: NavigableMapView<K, V>, SequencedMap<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V>

	override val keys: NavigableSet<K>

	override val values: SequencedCollection<V>

	override val entries: SequencedSet<Map.Entry<K, V>>
}

/**
 * A mutable navigable map.
 */
interface MutableNavigableMap<K, V>: NavigableMapView<K, V>, MutableSequencedMap<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V>

	/**
	 * Associates the given [key] with the [value].
	 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
	 *
	 * The existing entry's key is not replaced when the entry has the given [key].
	 */
	override fun put(key: K, value: V): V?

	/**
	 * Copies all entries from the given [map] to this map.
	 *
	 * The key of an entry in this map is not replaced when a copied entry has the same key.
	 */
	override fun putAll(map: MapView<out K, V>)

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 *
	 * Changes to the collection are reflected in this map, and vice versa.
	 *
	 * The iteration order of the keys in the collection is equal to this map's [entries].
	 */
	override val keys: RemoveOnlyNavigableSet<K>

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
	override val entries: RemoveOnlySequencedSet<MutableMap.MutableEntry<K, V>>
}
