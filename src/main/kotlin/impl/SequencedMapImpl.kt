package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.MapView
import io.github.spacedvoid.connection.MutableSequencedMap
import io.github.spacedvoid.connection.RemoveOnlySequencedCollection
import io.github.spacedvoid.connection.RemoveOnlySequencedSet
import io.github.spacedvoid.connection.SequencedCollection
import io.github.spacedvoid.connection.SequencedCollectionView
import io.github.spacedvoid.connection.SequencedMap
import io.github.spacedvoid.connection.SequencedMapView
import io.github.spacedvoid.connection.SequencedSet
import io.github.spacedvoid.connection.SequencedSetView

open class SequencedMapViewImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): MapViewImpl<K, V>(kotlin), SequencedMapView<K, V> {
	override fun reversed(): SequencedMapView<K, V> = SequencedMapViewImpl(this.kotlin.reversed())

	override val keys: SequencedSetView<out K> = SequencedSetViewImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollectionView<out V> = SequencedCollectionViewImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSetView<out Map.Entry<K, V>> = SequencedSetViewImpl(this.kotlin.sequencedEntrySet())

	override val MapView<K, V>.kotlin: java.util.SequencedMap<K, V>
		get() = this@SequencedMapViewImpl.kotlin
}

open class SequencedMapImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), SequencedMap<K, V> {
	override fun reversed(): SequencedMap<K, V> = SequencedMapImpl(this.kotlin.reversed())

	override val keys: SequencedSet<out K> = SequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollection<out V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<out Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableSequencedMapImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), MutableSequencedMap<K, V> {
	override fun reversed(): MutableSequencedMap<K, V> = MutableSequencedMapImpl(this.kotlin.reversed())

	override val keys: RemoveOnlySequencedSet<K> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: RemoveOnlySequencedCollection<V> = RemoveOnlySequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: RemoveOnlySequencedSet<MutableMap.MutableEntry<K, V>> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedEntrySet())
}
