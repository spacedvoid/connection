package io.github.spacedvoid.connection

/**
 * A [SequencedMapView] that defines the iteration order based on a [Comparator].
 * Elements inserted in this map must be comparable by the [comparator].
 * A [TypeCastException] is thrown if the element is not comparable.
 *
 * The [comparator] must be *consistent with equals*;
 * that is, `comparator.compare(a, b) == 0` if and only if `a.equals(b)`.
 * Otherwise, the behavior of this map is not defined.
 *
 * @apiNote
 * This type is not different with [NavigableMapView];
 * however, because this type was intended to migrate a [java.util.SortedMap] to a [java.util.NavigableMap],
 * operations on this map makes no guarantees about performance and thread-safety.
 * If you can select the type of the map, use [NavigableMapView] instead.
 */
interface SortedNavigableMapView<K, V>: SequencedMapView<K, V> {
	/**
	 * The comparator used to sort the entries in this map.
	 */
	val comparator: Comparator<in K>

	override fun reversed(): SortedNavigableMapView<K, V>

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
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [fromInclusive] is `true` and [toInclusive] is `false`;
	 * in such cases, this directly invokes [java.util.SortedMap.subMap].
	 */
	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V>

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
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [inclusive] is `false`;
	 * in such cases, this directly invokes [java.util.SortedMap.headMap].
	 */
	fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V>

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
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 * The only exception is when [inclusive] is `true`;
	 * in such cases, this directly invokes [java.util.SortedMap.tailMap].
	 */
	fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V>

	/**
	 * Returns the entry higher than the given key, or `null` if there is no such entry.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		tailMap(than, inclusive).first()

	/**
	 * Returns the entry lower than the given key, or `null` if there is no such entry.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		headMap(than, inclusive).last()

	/**
	 * Returns the key of the entry higher than the given key, or `null` if there is no such entry.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun higherKey(than: K, inclusive: Boolean): K? =
		tailMap(than, inclusive).firstKey()

	/**
	 * Returns the key of the entry lower the given key, or `null` if there is no such entry.
	 *
	 * This method makes no guarantees about its performance and thread-safety.
	 * This operation might not be atomic, which may return undefined results.
	 */
	fun lowerKey(than: K, inclusive: Boolean): K? =
		headMap(than, inclusive).lastKey()

	override val keys: SortedNavigableSetView<out K>

	override val MapView<K, V>.kotlin: java.util.SortedMap<K, V>
}

/**
 * An immutable [SortedNavigableMapView].
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
 */
interface MutableSortedNavigableMap<K, V>: MutableSequencedMap<K, V>, SortedNavigableMapView<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V>

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V>

	override val keys: RemoveOnlySortedNavigableSet<K>
}
