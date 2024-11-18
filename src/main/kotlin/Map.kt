package io.github.spacedvoid.connection

interface MapView<K, V> {
	fun size(): Int =
		this.kotlin.size

	fun isEmpty(): Boolean =
		this.kotlin.isEmpty()

	fun containsKey(key: K): Boolean =
		this.kotlin.containsKey(key)

	fun containsValue(value: V): Boolean =
		this.kotlin.containsValue(value)

	operator fun get(key: K): V? =
		this.kotlin[key]

	val keys: SetView<out K>

	val values: CollectionView<out V>

	val entries: SetView<out kotlin.collections.Map.Entry<K, V>>

	val MapView<K, V>.kotlin: kotlin.collections.Map<K, V>
}

interface Map<K, V>: MapView<K, V> {
	override val keys: Set<out K>

	override val values: Collection<out V>

	override val entries: Set<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableMap<K, V>: MapView<K, V> {
	fun put(key: K, value: V): V? =
		this.kotlin.put(key, value)

	@Suppress("UNCHECKED_CAST")
	fun putAll(map: Map<out K, out V>) =
		this.kotlin.putAll((map as Map<K, V>).kotlin)

	fun remove(key: K): V? =
		this.kotlin.remove(key)

	fun clear() =
		this.kotlin.clear()

	override val keys: RemoveOnlySet<K>

	override val values: RemoveOnlyCollection<V>

	override val entries: RemoveOnlySet<kotlin.collections.MutableMap.MutableEntry<K, V>>

	override val MapView<K, V>.kotlin: kotlin.collections.MutableMap<K, V>
}
