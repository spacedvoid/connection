package io.github.spacedvoid.connection

/**
 * A [MapView] that additionally defines the iteration order.
 */
interface SequencedMapView<K, V>: MapView<K, V> {
	/**
	 * Returns a reverse-ordered map of this map.
	 *
	 * Operations on the returned map delegates to this map.
	 */
	fun reversed(): SequencedMapView<K, V>

	/**
	 * Returns the first entry of this map, defined by the iteration order.
	 */
	fun first(): Pair<K, V>? =
		this.kotlin.firstEntry()?.let { it.key to it.value }

	/**
	 * Returns the last entry of this map, defined by the iteration order.
	 */
	fun last(): Pair<K, V>? =
		this.kotlin.firstEntry()?.let { it.key to it.value }

	/**
	 * Returns the key of the first entry of this map, defined by the iteration order.
	 */
	fun firstKey(): K =
		this.kotlin.firstEntry().key

	/**
	 * Returns the key of the last entry of this map, defined by the iteration order.
	 */
	fun lastKey(): K =
		this.kotlin.lastEntry().key

	override val keys: SequencedSetView<out K>

	override val values: SequencedCollectionView<out V>

	override val entries: SequencedSetView<out kotlin.collections.Map.Entry<K, V>>

	override val MapView<K, V>.kotlin: java.util.SequencedMap<K, V>
}

/**
 * An immutable [SequencedMapView].
 */
interface SequencedMap<K, V>: Map<K, V>, SequencedMapView<K, V> {
	override fun reversed(): SequencedMap<K, V>

	override val keys: SequencedSet<out K>

	override val values: SequencedCollection<out V>

	override val entries: SequencedSet<out kotlin.collections.Map.Entry<K, V>>
}

/**
 * A [SequencedMapView] that additionally supports entry addition, removal, and mutation operations.
 */
interface MutableSequencedMap<K, V>: MutableMap<K, V>, SequencedMapView<K, V> {
	override fun reversed(): MutableSequencedMap<K, V>

	fun removeFirst(): Pair<K, V>? =
		this.kotlin.pollFirstEntry()?.let { it.key to it.value }

	fun removeLast(): Pair<K, V>? =
		this.kotlin.pollLastEntry()?.let { it.key to it.value }

	override val keys: RemoveOnlySequencedSet<K>

	override val values: RemoveOnlySequencedCollection<V>

	override val entries: RemoveOnlySequencedSet<kotlin.collections.MutableMap.MutableEntry<K, V>>
}
