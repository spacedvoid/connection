package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable

interface NavigableMap<K, V>: NavigableMappable<K, V>, SortedNavigableMap<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override val keys: NavigableSet<K>
}

interface MutatingNavigableMapView<K, V>: NavigableMappable<K, V>, MutatingSortedNavigableMapView<K, V> {
	override fun reversed(): MutatingNavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutatingSortedNavigableMapView<K, V>

	override val keys: MutatingNavigableSetView<K>
}

interface MutableNavigableMap<K, V>: MutatingNavigableMapView<K, V>, MutableSortedNavigableMap<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>
}
