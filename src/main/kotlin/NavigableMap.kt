package io.github.spacedvoid.connection

interface NavigableMapView<K, V>: SortedNavigableMapView<K, V> {
	override fun reversed(): NavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.ceilingEntry(than)
			else -> this.kotlin.higherEntry(than)
		}.let { it.key to it.value }

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.floorEntry(than)
			else -> this.kotlin.lowerEntry(than)
		}.let { it.key to it.value }

	override fun higherKey(than: K, inclusive: Boolean): K? =
		when {
			inclusive -> this.kotlin.ceilingKey(than)
			else -> this.kotlin.higherKey(than)
		}

	override fun lowerKey(than: K, inclusive: Boolean): K? =
		when {
			inclusive -> this.kotlin.floorKey(than)
			else -> this.kotlin.lowerKey(than)
		}

	override val keys: NavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.NavigableMap<K, V>
}

interface NavigableMap<K, V>: SortedNavigableMap<K, V>, NavigableMapView<K, V> {
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
