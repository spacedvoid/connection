package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.SortedMappable
import kotlin.collections.Map
import kotlin.collections.MutableMap

interface SortedMap<K, V>: SortedMappable<K, V>, SequencedMap<K, V> {
	override val comparator: Comparator<in K>?

	override fun reversed(): SortedMap<K, V>

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedMap<K, V>

	fun headMap(before: K, inclusive: Boolean): SortedMap<K, V>

	fun tailMap(after: K, inclusive: Boolean): SortedMap<K, V>

	override fun firstKey(): K

	override fun lastKey(): K

	override val keys: SortedSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SortedSet<out Map.Entry<K, V>>
}

interface MutatingSortedMapView<K, V>: SortedMappable<K, V>, MutatingSequencedMapView<K, V> {
	override val comparator: Comparator<in K>?

	override fun reversed(): MutatingSortedMapView<K, V>

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedMapView<K, V>

	fun headMap(before: K, inclusive: Boolean): MutatingSortedMapView<K, V>

	fun tailMap(after: K, inclusive: Boolean): MutatingSortedMapView<K, V>

	override fun firstKey(): K

	override fun lastKey(): K

	override val keys: MutatingSortedSetView<out K>

	override val values: MutatingSequencedCollectionView<out V>

	override val entries: MutatingSortedSetView<out Map.Entry<K, V>>
}

interface MutableSortedMap<K, V>: MutatingSortedMapView<K, V>, MutableSequencedMap<K, V> {
	override fun reversed(): MutableSortedMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedMap<K, V>

	override val keys: RemoveOnlySortedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySortedSet<MutableMap.MutableEntry<K, V>>
}
