package io.github.spacedvoid.connection

/**
 * A [SortedNavigableMapView] that optimizes some operations.
 */
interface NavigableMapView<K, V>: SortedNavigableMapView<K, V> {
	override fun reversed(): NavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range, and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Mutating the returned map throws [IllegalArgumentException] if the key is outside the range.
	 *
	 * Since keys in this map are compared by the [comparator],
	 * the returned subset's [firstKey] and [lastKey] might not be equal to the given arguments.
	 * In such cases, the range is defined by the actual keys in this map.
	 *
	 * The behavior of the returned map when this map is *structurally modified*(that is, changing the size of this map)
	 * by operations on this map is not defined.
	 */
	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns a submap of this map, based on the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [before] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Mutating the returned map throws [IllegalArgumentException] if the key is outside the range.
	 *
	 * Since keys in this map are compared by the [comparator],
	 * the returned subset's [lastKey] might not be equal to the given argument.
	 * In such cases, the range is defined by the actual keys in this map.
	 *
	 * The behavior of the returned map when this map is *structurally modified*(that is, changing the size of this map)
	 * by operations on this map is not defined.
	 */
	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns a submap of this map, based on the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [after] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Mutating the returned map throws [IllegalArgumentException] if the key is outside the range.
	 *
	 * Since keys in this map are compared by the [comparator],
	 * the returned subset's [firstKey] might not be equal to the given argument.
	 * In such cases, the range is defined by the actual keys in this map.
	 *
	 * The behavior of the returned map when this map is *structurally modified*(that is, changing the size of this map)
	 * by operations on this map is not defined.
	 */
	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns the entry higher than the given key, or `null` if there is no such entry.
	 */
	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.ceilingEntry(than)
			else -> this.kotlin.higherEntry(than)
		}.let { it.key to it.value }

	/**
	 * Returns the entry lower than the given key, or `null` if there is no such entry.
	 */
	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.floorEntry(than)
			else -> this.kotlin.lowerEntry(than)
		}.let { it.key to it.value }

	/**
	 * Returns the key of the entry higher than the given key, or `null` if there is no such entry.
	 */
	override fun higherKey(than: K, inclusive: Boolean): K? =
		when {
			inclusive -> this.kotlin.ceilingKey(than)
			else -> this.kotlin.higherKey(than)
		}

	/**
	 * Returns the key of the entry lower the given key, or `null` if there is no such entry.
	 */
	override fun lowerKey(than: K, inclusive: Boolean): K? =
		when {
			inclusive -> this.kotlin.floorKey(than)
			else -> this.kotlin.lowerKey(than)
		}

	override val keys: NavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.NavigableMap<K, V>
}

/**
 * An immutable [NavigableMapView].
 */
interface NavigableMap<K, V>: SortedNavigableMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): NavigableMap<K, V>

	override val keys: NavigableSet<out K>
}

/**
 * A [NavigableMapView] that additionally supports entry addition, removal, and mutation operations.
 */
interface MutableNavigableMap<K, V>: MutableSortedNavigableMap<K, V>, NavigableMapView<K, V> {
	override fun reversed(): MutableNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlyNavigableSet<K>
}
