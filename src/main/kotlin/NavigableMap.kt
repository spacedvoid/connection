package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable

interface NavigableMapView<K, V>: SortedNavigableMapView<K, V>, NavigableMappable<K, V> {
	override fun reversed(): NavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override val keys: NavigableSetView<K>
	override val keys: NavigableSetView<out K>
}

interface NavigableMap<K, V>: SortedNavigableMap<K, V>, NavigableMapView<K, V>, NavigableMappable<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override val keys: NavigableSet<out K>
}

interface MutableNavigableMap<K, V>: MutableSortedNavigableMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>
}
