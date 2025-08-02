/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.keySet

open class KotlinNavigableMapImpl<K, V>(private val connection: NavigableMapView<K, V>): KotlinSequencedMapImpl<K, V>(connection), java.util.NavigableMap<K, V> {
	override fun reversed(): java.util.NavigableMap<K, V> = KotlinNavigableMapImpl(this.connection.reversed())

	override fun lowerEntry(key: K): Map.Entry<K, V>? = this.connection.lowerEntry(key, false)?.toEntry()

	override fun lowerKey(key: K): K? = this.connection.lowerKey(key, false)

	override fun floorEntry(key: K): Map.Entry<K, V>? = this.connection.lowerEntry(key, true)?.toEntry()

	override fun floorKey(key: K): K? = this.connection.lowerKey(key, true)

	override fun ceilingEntry(key: K): Map.Entry<K, V>? = this.connection.higherEntry(key, true)?.toEntry()

	override fun ceilingKey(key: K): K? = this.connection.higherKey(key, true)

	override fun higherEntry(key: K): Map.Entry<K, V>? = this.connection.higherEntry(key, false)?.toEntry()

	override fun higherKey(key: K): K? = this.connection.higherKey(key, false)

	override fun descendingMap(): java.util.NavigableMap<K, V> = reversed()

	override fun navigableKeySet(): java.util.NavigableSet<K> = keySet()

	override fun descendingKeySet(): java.util.NavigableSet<K> = descendingMap().keySet()

	override fun subMap(fromKey: K, fromInclusive: Boolean, toKey: K, toInclusive: Boolean): java.util.NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.subMap(fromKey, toKey, fromInclusive, toInclusive))

	override fun headMap(toKey: K, inclusive: Boolean): java.util.NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.headMap(toKey, inclusive))

	override fun tailMap(fromKey: K, inclusive: Boolean): java.util.NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.tailMap(fromKey, inclusive))

	override fun subMap(fromKey: K, toKey: K): java.util.NavigableMap<K, V> = subMap(fromKey, true, toKey, false)

	override fun headMap(toKey: K): java.util.NavigableMap<K, V> = headMap(toKey, false)

	override fun tailMap(fromKey: K): java.util.NavigableMap<K, V> = tailMap(fromKey, true)

	override fun firstEntry(): Map.Entry<K, V>? = this.connection.first()?.toEntry()

	override fun lastEntry(): Map.Entry<K, V>? = this.connection.last()?.toEntry()

	override fun pollFirstEntry(): Map.Entry<K, V>? =
		if(this.connection is MutableNavigableMap<K, V>) this.connection.removeFirst()?.toEntry() else throw UnsupportedOperationException("pollFirstEntry")

	override fun pollLastEntry(): Map.Entry<K, V>? =
		if(this.connection is MutableNavigableMap<K, V>) this.connection.removeLast()?.toEntry() else throw UnsupportedOperationException("pollLastEntry")

	override fun comparator(): Comparator<in K> = this.connection.comparator

	override fun firstKey(): K? = this.connection.firstKey()

	override fun lastKey(): K? = this.connection.lastKey()
}

private data class ImmutableEntry<K, V>(override val key: K, override val value: V): Map.Entry<K, V> {
	override fun equals(other: Any?): Boolean {
		if(this == other) return true
		if(other !is Map.Entry<*, *>) return false
		return this.key == other.key && this.value == other.value
	}

	/**
	 * Returns a hash code value for the object.
	 *
	 * This implementation uses the same way from [java.util.AbstractMap.SimpleEntry.hashCode].
	 */
	override fun hashCode(): Int = this.key.hashCode() xor this.value.hashCode()
}

private fun <K, V> Pair<K, V>.toEntry(): Map.Entry<K, V> = ImmutableEntry(this.first, this.second)
