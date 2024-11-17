package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.MutableSequencedMappable
import io.github.spacedvoid.connection.characteristic.SequencedMappable

interface SequencedMapView<K, V>: MapView<K, V>, SequencedMappable<K, V> {
	override fun reversed(): SequencedMapView<K, V>

	override fun first(): Pair<K, V>?

	override fun firstKey(): K

	override fun lastKey(): K

	override val keys: SequencedSetView<out K>

	override val values: SequencedCollectionView<out V>

	override val entries: SequencedSetView<out kotlin.collections.Map.Entry<K, V>>
}

interface SequencedMap<K, V>: Map<K, V>, SequencedMapView<K, V>, SequencedMappable<K, V> {
	override fun reversed(): SequencedMap<K, V>

	override val keys: SequencedSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SequencedSet<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableSequencedMap<K, V>: MutableMap<K, V>, SequencedMapView<K, V>, MutableSequencedMappable<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	override fun removeFirst(): Pair<K, V>?

	override fun removeLast(): Pair<K, V>?

	override val keys: RemoveOnlySequencedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
