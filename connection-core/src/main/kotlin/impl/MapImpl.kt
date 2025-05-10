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

open class MapViewImpl<K, V>(open val kotlin: kotlin.collections.Map<K, V>): MapView<K, V> {
	override fun size(): Int = this.kotlin.size

	override fun isEmpty(): Boolean = this.kotlin.isEmpty()

	override fun containsKey(key: K): Boolean = this.kotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this.kotlin.containsValue(value)

	override fun get(key: K): V? = this.kotlin[key]

	override val keys: SetView<out K> = SetViewImpl(this.kotlin.keys)

	override val values: CollectionView<out V> = CollectionViewImpl(this.kotlin.values)

	override val entries: SetView<out kotlin.collections.Map.Entry<K, V>> = SetViewImpl(this.kotlin.entries)

	override fun equals(other: Any?): Boolean = super.equals(other)

	override fun hashCode(): Int = super.hashCode()

}

open class MapImpl<K, V>(override val kotlin: kotlin.collections.Map<K, V>): MapViewImpl<K, V>(kotlin), Map<K, V> {
	override val keys: Set<out K> = SetImpl(this.kotlin.keys)

	override val values: Collection<out V> = CollectionImpl(this.kotlin.values)

	override val entries: Set<out kotlin.collections.Map.Entry<K, V>> = SetImpl(this.kotlin.entries)
}

open class MutableMapImpl<K, V>(override val kotlin: kotlin.collections.MutableMap<K, V>): MapViewImpl<K, V>(kotlin), MutableMap<K, V> {
	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: MapView<out K, out V>) = this.kotlin.putAll(KotlinMapImpl(map))

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()

	override val keys: RemoveOnlySet<K> = RemoveOnlySetImpl(this.kotlin.keys)

	override val values: RemoveOnlyCollection<V> = RemoveOnlyCollectionImpl(this.kotlin.values)

	override val entries: RemoveOnlySet<kotlin.collections.MutableMap.MutableEntry<K, V>> = RemoveOnlySetImpl(this.kotlin.entries)

}
