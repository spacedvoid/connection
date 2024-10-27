@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

import kotlin.collections.MutableMap

@Deprecated("This type is hidden for public use.", ReplaceWith("Map", "io.github.spacedvoid.connection.Map"), DeprecationLevel.HIDDEN)
interface MapBase<K, V> {
	fun size(): Int

	fun containsKey(key: K): Boolean

	fun containsValue(value: V): Boolean

	operator fun get(key: K): V?

	val keys: SetBase<K>

	val values: CollectionBase<V>

	val entries: SetBase<out kotlin.collections.Map.Entry<K, V>>
}

interface Map<K, V>: MapBase<K, V> {
	override val keys: Set<K>

	override val values: Collection<V>

	override val entries: Set<out kotlin.collections.Map.Entry<K, V>>
}

interface MutatingMapView<K, V>: MapBase<K, V> {
	override val keys: MutatingSetView<K>

	override val values: MutatingCollectionView<V>

	override val entries: MutatingSetView<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableMap<K, V>: MutatingMapView<K, V> {
	fun put(key: K, value: V): V?

	fun putAll(map: Map<out K, V>)

	fun remove(key: K): V?

	fun clear()

	override val keys: RemoveOnlySet<K>

	override val values: RemoveOnlyCollection<V>

	override val entries: MutableSet<out MutableMap.MutableEntry<K, V>>
}
