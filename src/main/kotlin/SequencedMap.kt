package io.github.spacedvoid.connection

interface SequencedMapView<K, V>: MapView<K, V> {
	fun reversed(): SequencedMapView<K, V>

	fun first(): Pair<K, V>? =
		this.kotlin.firstEntry()?.let { it.key to it.value }

	fun last(): Pair<K, V>? =
		this.kotlin.firstEntry()?.let { it.key to it.value }

	fun firstKey(): K =
		this.kotlin.firstEntry().key

	fun lastKey(): K =
		this.kotlin.lastEntry().key

	override val keys: SequencedSetView<out K>

	override val values: SequencedCollectionView<out V>

	override val entries: SequencedSetView<out kotlin.collections.Map.Entry<K, V>>

	override val MapView<K, V>.kotlin: java.util.SequencedMap<K, V>
}

interface SequencedMap<K, V>: Map<K, V>, SequencedMapView<K, V> {
	override fun reversed(): SequencedMap<K, V>

	override val keys: SequencedSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SequencedSet<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableSequencedMap<K, V>: MutableMap<K, V>, SequencedMapView<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	fun removeFirst(): Pair<K, V>? =
		this.kotlin.pollFirstEntry()?.let { it.key to it.value }

	fun removeLast(): Pair<K, V>? =
		this.kotlin.pollLastEntry()?.let { it.key to it.value }

	override val keys: RemoveOnlySequencedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
