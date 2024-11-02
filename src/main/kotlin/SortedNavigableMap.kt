package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.NavigableMappable

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
}

interface MutableSortedNavigableMap<K, V>: MutatingSortedNavigableMapView<K, V>, MutableSequencedMap<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlySortedNavigableSet<K>
}
