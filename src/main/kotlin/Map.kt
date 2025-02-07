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
 * The mutability of this map is not defined.
 * This map might be mutable, and might also be self-mutating.
 *
 * Whether a key matches another key is determined via [Any.equals].
 *
 * Thread-safety is not defined, unless the underlying map ensures such.
 * As such, the behavior of operations when involved in a data race is not defined.
 *
 * Operations are not optional, and must not throw [UnsupportedOperationException].
 */
interface MapView<K, V> {
	/**
	 * Returns the size of this map.
	 */
	fun size(): Int =
		this.kotlin.size

	/**
	 * Returns `true` if this map is empty, `false` otherwise.
	 */
	fun isEmpty(): Boolean =
		this.kotlin.isEmpty()

	/**
	 * Returns `true` if an entry has the given [key] in this map, `false` otherwise.
	 */
	fun containsKey(key: K): Boolean =
		this.kotlin.containsKey(key)

	/**
	 * Returns `true` if an entry has the given [value] in this map, `false` otherwise.
	 */
	fun containsValue(value: V): Boolean =
		this.kotlin.containsValue(value)

	/**
	 * Returns the value of the entry associated with the given [key], or `null` if no entries have the [key].
	 */
	operator fun get(key: K): V? =
		this.kotlin[key]

	/**
	 * Returns a collection that reflects the keys that this map contains.
	 */
	val keys: SetView<out K>

	/**
	 * Returns a collection that reflects the values that this map contains.
	 */
	val values: CollectionView<out V>

	/**
	 * Returns a collection that reflects the entries that this map contains.
	 *
	 * @apiNote
	 * Although this uses [kotlin.collections.Map.Entry] for compatibility,
	 * it must not be casted to [kotlin.collections.MutableMap.MutableEntry].
	 */
	val entries: SetView<out kotlin.collections.Map.Entry<K, V>>

	/**
	 * Returns a direct Kotlin representation of this map.
	 *
	 * @apiNote
	 * This extension property is not intended for external use; use [asKotlin] instead.
	 * In contexts that inherit or override this property, this extension can be called as
	 *
	 * ```kotlin
	 * this.kotlin
	 * ```
	 *
	 * In other rare cases where the use of this property is required, use
	 *
	 * ```kotlin
	 * with(connection) {
	 *     println(this.kotlin)
	 * }
	 * ```
	 *
	 * @implNote
	 * The implementation ***must not*** use the given receiver.
	 * Even though the receiver will always be `this`,
	 * always return the current class's Kotlin representation instead.
	 */
	val MapView<K, V>.kotlin: kotlin.collections.Map<K, V>
}

/**
 * An immutable [MapView].
 */
interface Map<K, V>: MapView<K, V> {
	override val keys: Set<out K>

	override val values: Collection<out V>

	override val entries: Set<out kotlin.collections.Map.Entry<K, V>>
}

/**
 * A [MapView] that additionally supports entry addition, removal, and mutation operations.
 */
interface MutableMap<K, V>: MapView<K, V> {
	/**
	 * Puts a new entry to this map and returns `null`,
	 * or replaces the value of the entry associated with the given [key] by the [value] and returns the old value.
	 */
	fun put(key: K, value: V): V? =
		this.kotlin.put(key, value)

	/**
	 * For all entries in the given [map],
	 * copies the entry to this map or replaces the value of the entry in this map associated with the key by the entry's value.
	 */
	fun putAll(map: MapView<out K, out V>) =
		this.kotlin.putAll(map.asKotlin())

	/**
	 * Removes the entry associated with the given [key] and returns the value, or returns `null` if no entries have the [key].
	 */
	fun remove(key: K): V? =
		this.kotlin.remove(key)

	/**
	 * Removes all entries in this map.
	 */
	fun clear() =
		this.kotlin.clear()

	override val keys: RemoveOnlySet<K>

	override val values: RemoveOnlyCollection<V>

	override val entries: RemoveOnlySet<kotlin.collections.MutableMap.MutableEntry<K, V>>

	override val MapView<K, V>.kotlin: kotlin.collections.MutableMap<K, V>
}
