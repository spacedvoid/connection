package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Mappable
import io.github.spacedvoid.connection.characteristic.MutableMappable
import kotlin.collections.MutableMap

interface MapView<K, V>: Mappable<K, V> {
	override fun size(): Int

	override fun containsKey(key: K): Boolean

	override fun containsValue(value: V): Boolean

	override operator fun get(key: K): V?

	override val keys: SetView<out K>

	override val values: CollectionView<out V>

	override val entries: SetView<out kotlin.collections.Map.Entry<K, V>>
}

interface Map<K, V>: MapView<K, V> {
	override val keys: Set<out K>

	override val values: Collection<out V>

	override val entries: Set<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableMap<K, V>: MapView<K, V>, MutableMappable<K, V> {
	override fun put(key: K, value: V): V?

	override fun putAll(map: Map<out K, out V>)

	override fun remove(key: K): V?

	override fun clear()

	override val keys: RemoveOnlySet<K>

	override val values: RemoveOnlyCollection<V>

	override val entries: RemoveOnlySet<MutableMap.MutableEntry<K, V>>
}
