package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.MutableSequencedMappable
import io.github.spacedvoid.connection.characteristic.SequencedMappable

interface SequencedMap<K, V>: SequencedMappable<K, V>, Map<K, V> {
	override fun reversed(): SequencedMap<K, V>

	override fun first(): Pair<K, V>?

	override fun last(): Pair<K, V>?

	override val keys: SequencedSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SequencedSet<out kotlin.collections.Map.Entry<K, V>>
}

interface MutatingSequencedMapView<K, V>: SequencedMappable<K, V>, MutatingMapView<K, V> {
	override fun reversed(): MutatingSequencedMapView<K, V>

	override fun first(): Pair<K, V>?

	override fun last(): Pair<K, V>?

	override val keys: MutatingSequencedSetView<out K>

	override val values: MutatingSequencedCollectionView<out V>

	override val entries: MutatingSequencedSetView<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableSequencedMap<K, V>: MutableSequencedMappable<K, V>, MutatingSequencedMapView<K, V>, MutableMap<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	override fun removeFirst(): Pair<K, V>?

	override fun removeLast(): Pair<K, V>?

	override val keys: RemoveOnlySequencedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
