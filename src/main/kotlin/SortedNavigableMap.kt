package io.github.spacedvoid.connection

/**
 * A [SequencedMapView] that defines the iteration order of the entries based on a [Comparator].
 * The entries are sorted with the [comparator] based on their keys.
 *
 * The [comparator] must satisfy `comparator.compare(a, b) == 0` if and only if `a.equals(b)`,
 * which is also called as *consistent with equals*.
 * Otherwise, the behavior of this map is not defined.
 *
 * @apiNote
 * This type is not different with [NavigableMapView].
 * However, because this type was intended to migrate a [java.util.SortedMap] to a [java.util.NavigableMap],
 * operations on this map makes no guarantees about performance and thread-safety.
 * If you can select the type of the map, use [NavigableMapView] instead.
 */
interface SortedNavigableMapView<K, V>: SequencedMapView<K, V> {
	/**
	 * The comparator used to sort the keys in this map.
	 */
	val comparator: Comparator<in K>

	override fun reversed(): SortedNavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the given range.
	 * Throws [IllegalArgumentException] if [from] is higher than [to],
	 * or if this set has a restricted range, and [from] or [to] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the range before the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [before] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns a submap of this map, in the range after the given key.
	 * Throws [IllegalArgumentException] if this set has a restricted range, and [after] lies outside the range.
	 *
	 * Operations on the returned map is delegated to this map.
	 * Adding entries to the returned map throws [IllegalArgumentException] if the key is outside the range.
	 */
	fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns the entry higher than the given key, or `null` if there is no such entry.
	 */
	fun higherEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		tailMap(key, inclusive).first()

	/**
	 * Returns the entry lower than the given key, or `null` if there is no such entry.
	 */
	fun lowerEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		headMap(key, inclusive).last()

	/**
	 * Returns the key of the entry higher than the given key, or `null` if there is no such entry.
	 */
	fun higherKey(key: K, inclusive: Boolean): K? =
		tailMap(key, inclusive).first()?.first

	/**
	 * Returns the key of the entry lower the given key, or `null` if there is no such entry.
	 */
	fun lowerKey(key: K, inclusive: Boolean): K? =
		headMap(key, inclusive).last()?.first

	override val keys: SortedNavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.SortedMap<K, V>
}

/**
 * An immutable [SortedNavigableMapView].
 *
 * @apiNote
 * This type is not different with [NavigableMap].
 * However, because this type was intended to migrate a [java.util.SortedMap] to a [java.util.NavigableMap],
 * operations on this map makes no guarantees about performance and thread-safety.
 * If you can select the type of the map, use [NavigableMap] instead.
 */
interface SortedNavigableMap<K, V>: SequencedMap<K, V>, SortedNavigableMapView<K, V> {
	override fun reversed(): SortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V>

	override val keys: SortedNavigableSet<out K>
}

/**
 * A [SortedNavigableMapView] that additionally supports entry addition, removal, and mutation operations.
 *
 * @apiNote
 * This type is not different with [MutableNavigableMap].
 * However, because this type was intended to migrate a [java.util.SortedMap] to a [java.util.NavigableMap],
 * operations on this map makes no guarantees about performance and thread-safety.
 * If you can select the type of the map, use [MutableNavigableMap] instead.
 */
interface MutableSortedNavigableMap<K, V>: MutableSequencedMap<K, V>, SortedNavigableMapView<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlySortedNavigableSet<K>
}
