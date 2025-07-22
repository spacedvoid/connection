/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

/**
 * A map view that additionally defines the iteration order of the entries.
 */
interface SequencedMapView<K, out V>: MapView<K, V> {
	/**
	 * Returns a reverse-ordered map of this map.
	 *
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

	override val keys: SequencedSetView<K>

	override val values: SequencedCollectionView<V>

	override val entries: SequencedSetView<kotlin.collections.Map.Entry<K, V>>
}

/**
 * An immutable sequenced map.
 */
interface SequencedMap<K, out V>: Map<K, V>, SequencedMapView<K, V> {
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
interface MutableSequencedMap<K, V>: MutableMap<K, V>, SequencedMapView<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	/**
	 * Removes and returns the first entry defined by the iteration order, or returns `null` if this map is empty.
	 */
	fun removeFirst(): Pair<K, V>?

	/**
	 * Removes and returns the last entry defined by the iteration order, or returns `null` if this map is empty.
	 */
	fun removeLast(): Pair<K, V>?

	override val keys: RemoveOnlySequencedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
