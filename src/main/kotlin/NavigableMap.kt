package io.github.spacedvoid.connection

/**
 * A [SortedNavigableMapView] that optimizes some operations.
 */
interface NavigableMapView<K, V>: SortedNavigableMapView<K, V> {
	override fun reversed(): NavigableMapView<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMapView<K, V>

	override fun headMap(before: K, inclusive: Boolean): NavigableMapView<K, V>

	override fun tailMap(after: K, inclusive: Boolean): NavigableMapView<K, V>

	override fun higherEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.ceilingEntry(key)
			else -> this.kotlin.higherEntry(key)
		}.let { it.key to it.value }

	override fun lowerEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.floorEntry(key)
			else -> this.kotlin.lowerEntry(key)
		}.let { it.key to it.value }

	override fun higherKey(key: K, inclusive: Boolean): K? =
		if(inclusive) this.kotlin.ceilingKey(key) else this.kotlin.higherKey(key)

	override fun lowerKey(key: K, inclusive: Boolean): K? =
		if(inclusive) this.kotlin.floorKey(key) else this.kotlin.lowerKey(key)

	override val keys: NavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.NavigableMap<K, V>
}

/**
 * An immutable [NavigableMapView].
 */
interface NavigableMap<K, V>: SortedNavigableMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V>

	override val keys: NavigableSet<out K>
}

/**
 * A [NavigableMapView] that additionally supports entry addition, removal, and mutation operations.
 */
interface MutableNavigableMap<K, V>: MutableSortedNavigableMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>
}
