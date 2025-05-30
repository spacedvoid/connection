package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinMapImpl

open class SequencedMapViewImpl<K, V>(override val kotlin: java.util.SequencedMap<K, V>): SequencedMapView<K, V>, MapViewImpl<K, V>(kotlin) {
	override fun reversed(): SequencedMapView<K, V> = SequencedMapViewImpl(this.kotlin.reversed())

	override fun first(): Pair<K, V>? = this.kotlin.firstEntry()?.let { it.key to it.value }

	override fun last(): Pair<K, V>? = this.kotlin.lastEntry()?.let { it.key to it.value }

	override fun firstKey(): K = this.kotlin.firstEntry()?.key ?: throw NoSuchElementException("Map is empty")

	override fun lastKey(): K = this.kotlin.lastEntry()?.key ?: throw NoSuchElementException("Map is empty")

	override val keys: SequencedSetView<out K> = SequencedSetViewImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollectionView<out V> = SequencedCollectionViewImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSetView<out Map.Entry<K, V>> = SequencedSetViewImpl(this.kotlin.sequencedEntrySet())
}

open class SequencedMapImpl<K, V>(override val kotlin: java.util.SequencedMap<K, V>): SequencedMap<K, V>, SequencedMapViewImpl<K, V>(kotlin) {
	override fun reversed(): SequencedMap<K, V> = SequencedMapImpl(this.kotlin.reversed())

	override val keys: SequencedSet<out K> = SequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollection<out V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<out Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableSequencedMapImpl<K, V>(override val kotlin: java.util.SequencedMap<K, V>): MutableSequencedMap<K, V>, SequencedMapViewImpl<K, V>(kotlin) {
	override fun reversed(): MutableSequencedMap<K, V> = MutableSequencedMapImpl(this.kotlin.reversed())

	override fun removeFirst(): Pair<K, V>? = this.kotlin.pollFirstEntry()?.let { it.key to it.value }

	override fun removeLast(): Pair<K, V>? = this.kotlin.pollLastEntry()?.let { it.key to it.value }

	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: MapView<out K, out V>) = this.kotlin.putAll(KotlinMapImpl(map))

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()

	override val keys: MutableSequencedSet<K> = MutableSequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: MutableSequencedCollection<V> = MutableSequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: MutableSequencedSet<MutableMap.MutableEntry<K, V>> = MutableSequencedSetImpl(this.kotlin.sequencedEntrySet())
}
