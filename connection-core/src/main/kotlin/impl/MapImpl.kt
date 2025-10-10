/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.Map
import io.github.spacedvoid.connection.MutableMap
import io.github.spacedvoid.connection.Set
import io.github.spacedvoid.connection.impl.kotlin.KotlinMapImpl

open class MapViewImpl<K, out V>(open val kotlin: kotlin.collections.Map<K, V>): MapView<K, V> {
	override fun size(): Int = this.kotlin.size

	override fun isEmpty(): Boolean = this.kotlin.isEmpty()

	override fun containsKey(key: K): Boolean = this.kotlin.containsKey(key)

	override fun containsValue(value: @UnsafeVariance V): Boolean = this.kotlin.containsValue(value)

	override fun get(key: K): V? = this.kotlin[key]

	override val keys: SetView<K> = SetViewImpl(this.kotlin.keys)

	override val values: CollectionView<V> = CollectionViewImpl(this.kotlin.values)

	override val entries: SetView<kotlin.collections.Map.Entry<K, V>> = SetViewImpl(this.kotlin.entries)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is MapView<*, *>) return false
		val thisIterator = this.entries.iterator()
		val otherIterator = this.entries.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() || otherIterator.hasNext())
	}

	override fun hashCode(): Int = this.entries.sumOf { (k, v) -> k.hashCode() xor v.hashCode() }

	override fun toString(): String = "${this::class.qualifiedName}{entries=[${this.entries.joinToString { "{${it.key}=${it.value}}" }}]}"
}

open class MapImpl<K, out V>(override val kotlin: kotlin.collections.Map<K, V>): Map<K, V>, MapViewImpl<K, V>(kotlin) {
	override val keys: Set<K> = SetImpl(this.kotlin.keys)

	override val values: Collection<V> = CollectionImpl(this.kotlin.values)

	override val entries: Set<kotlin.collections.Map.Entry<K, V>> = SetImpl(this.kotlin.entries)
}

open class MutableMapImpl<K, V>(override val kotlin: kotlin.collections.MutableMap<K, V>): MutableMap<K, V>, MapViewImpl<K, V>(kotlin) {
	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: MapView<out K, V>) = this.kotlin.putAll(KotlinMapImpl(map))

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()

	override val keys: RemoveOnlySet<K> = RemoveOnlySetImpl(this.kotlin.keys)

	override val values: RemoveOnlyCollection<V> = RemoveOnlyCollectionImpl(this.kotlin.values)

	override val entries: RemoveOnlySet<kotlin.collections.MutableMap.MutableEntry<K, V>> = RemoveOnlySetImpl(this.kotlin.entries)
}
