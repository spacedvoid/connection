package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable
import kotlin.collections.Map
import kotlin.collections.MutableMap

interface SortedNavigableMap<K, V>: NavigableMappable<K, V>, SequencedMap<K, V> {
	override val comparator: Comparator<in K>?

	override fun reversed(): SortedNavigableMap<K, V>

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V>

	fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V>

	fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun higherKey(than: K, inclusive: Boolean): K?

	override fun lowerKey(than: K, inclusive: Boolean): K?

	override val keys: SortedNavigableSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SortedNavigableSet<out Map.Entry<K, V>>
}

interface MutatingSortedNavigableMapView<K, V>: NavigableMappable<K, V>, MutatingSequencedMapView<K, V> {
	override val comparator: Comparator<in K>?

	override fun reversed(): MutatingSortedNavigableMapView<K, V>

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	fun headMap(before: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	fun tailMap(after: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun higherKey(than: K, inclusive: Boolean): K?

	override fun lowerKey(than: K, inclusive: Boolean): K?

	override val keys: MutatingSortedNavigableSetView<out K>

	override val values: MutatingSequencedCollectionView<out V>

	override val entries: MutatingSortedNavigableSetView<out Map.Entry<K, V>>
}

interface MutableSortedNavigableMap<K, V>: MutatingSortedNavigableMapView<K, V>, MutableSequencedMap<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlySortedNavigableSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySortedNavigableSet<MutableMap.MutableEntry<K, V>>
}
