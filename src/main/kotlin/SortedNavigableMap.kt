package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable

interface SortedNavigableMapView<K, V>: SequencedMapView<K, V>, NavigableMappable<K, V> {
	override val comparator: Comparator<in K>?

	override fun reversed(): SortedNavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	override fun higherKey(than: K, inclusive: Boolean): K?

	override fun lowerKey(than: K, inclusive: Boolean): K?

	override val keys: SortedNavigableSetView<out K>
}

interface SortedNavigableMap<K, V>: SequencedMap<K, V>, SortedNavigableMapView<K, V>, NavigableMappable<K, V> {
	override fun reversed(): SortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V>

	override val keys: SortedNavigableSet<out K>
}

interface MutableSortedNavigableMap<K, V>: MutableSequencedMap<K, V>, SortedNavigableMapView<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlySortedNavigableSet<K>
}
