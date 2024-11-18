package io.github.spacedvoid.connection

interface SortedNavigableMapView<K, V>: SequencedMapView<K, V> {
	val comparator: Comparator<in K>?
		get() = this.kotlin.comparator()

	override fun reversed(): SortedNavigableMapView<K, V>

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		tailMap(than, inclusive).first()

	fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		headMap(than, inclusive).last()

	fun higherKey(than: K, inclusive: Boolean): K? =
		tailMap(than, inclusive).firstKey()

	fun lowerKey(than: K, inclusive: Boolean): K? =
		headMap(than, inclusive).lastKey()

	override val keys: SortedNavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.SortedMap<K, V>
}

interface SortedNavigableMap<K, V>: SequencedMap<K, V>, SortedNavigableMapView<K, V> {
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
