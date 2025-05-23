/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A sequenced map view that defines the iteration order of the entries based on a [Comparator].
 * The entries are sorted with the [comparator] based on their keys.
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this map is not defined.
 */
interface NavigableMapView<K, V>: SequencedMapView<K, V> {
	/**
	 * The comparator used to sort the entries in this map.
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

	override val keys: NavigableSetView<out K>
}

/**
 * An immutable navigable map.
 */
interface NavigableMap<K, V>: SequencedMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V>

	override val keys: NavigableSet<out K>
}

/**
 * A mutable navigable map.
 */
interface MutableNavigableMap<K, V>: MutableSequencedMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>
}
