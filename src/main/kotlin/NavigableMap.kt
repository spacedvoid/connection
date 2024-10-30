package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable
import kotlin.collections.Map
import kotlin.collections.MutableMap

interface NavigableMap<K, V>: NavigableMappable<K, V>, SortedMap<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun higherKey(than: K, inclusive: Boolean): K?

	override fun lowerKey(than: K, inclusive: Boolean): K?

	override val keys: NavigableSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: NavigableSet<out Map.Entry<K, V>>
}

interface MutatingNavigableMapView<K, V>: NavigableMappable<K, V>, MutatingSortedMapView<K, V> {
	override fun reversed(): MutatingNavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingNavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutatingNavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutatingNavigableMapView<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun higherKey(than: K, inclusive: Boolean): K?

	override fun lowerKey(than: K, inclusive: Boolean): K?

	override val keys: MutatingNavigableSetView<out K>

	override val values: MutatingSequencedCollectionView<out V>

	override val entries: MutatingNavigableSetView<out Map.Entry<K, V>>
}

interface MutableNavigableMap<K, V>: MutatingNavigableMapView<K, V>, MutableSortedMap<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlyNavigableSet<MutableMap.MutableEntry<K, V>>
}
