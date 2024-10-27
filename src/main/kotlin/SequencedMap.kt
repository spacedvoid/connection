@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

@Deprecated("This type is hidden for public use.", ReplaceWith("SequencedMap", "io.github.spacedvoid.connection.SequencedMap"), DeprecationLevel.HIDDEN)
interface SequencedMapBase<K, V>: MapBase<K, V> {
	override val keys: SequencedSetBase<K>

	override val values: SequencedCollectionBase<V>

	override val entries: SequencedSetBase<out kotlin.collections.Map.Entry<K, V>>
}

interface SequencedMap<K, V>: Map<K, V>, SequencedMapBase<K, V> {
	override val keys: SequencedSet<K>

	override val values: SequencedCollection<V>

	override val entries: SequencedSet<out kotlin.collections.Map.Entry<K, V>>
}

interface MutatingSequencedMapView<K, V>: MutatingMapView<K, V>, SequencedMapBase<K, V> {
	override val keys: MutatingSequencedSetView<K>

	override val values: MutatingSequencedCollectionView<V>

	override val entries: MutatingSequencedSetView<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableSequencedMap<K, V>: MutatingSequencedMapView<K, V>, MutableMap<K, V> {
	override val keys: MutableSequencedSet<K>

	override val values: MutableSequencedCollection<V>

	override val entries: MutableSequencedSet<out kotlin.collections.MutableMap.MutableEntry<K, V>>
}
