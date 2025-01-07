package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.utils.naturalOrdering
import java.util.SortedSet

open class NavigableMapViewImpl<K, V>(private val kotlin: java.util.NavigableMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), NavigableMapView<K, V> {
	override val comparator: Comparator<in K> = this.kotlin.comparator() ?: naturalOrdering()

	override fun reversed(): NavigableMapView<K, V> = NavigableMapViewImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.tailMap(after, inclusive))

	override val keys: NavigableSetView<out K> = NavigableSetViewImpl(this.kotlin.keySet())

	override val MapView<K, V>.kotlin: java.util.NavigableMap<K, V>
		get() = this@NavigableMapViewImpl.kotlin
}

open class NavigableMapImpl<K, V>(private val kotlin: java.util.NavigableMap<K, V>): NavigableMapViewImpl<K, V>(kotlin), NavigableMap<K, V> {
	override fun reversed(): NavigableMap<K, V> = NavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.tailMap(after, inclusive))

	override val keys: NavigableSet<out K> = NavigableSetImpl(this.kotlin.keySet())

	override val values: SequencedCollection<out V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<out Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableNavigableMapImpl<K, V>(private val kotlin: java.util.NavigableMap<K, V>): NavigableMapViewImpl<K, V>(kotlin), MutableNavigableMap<K, V> {
	override fun reversed(): MutableNavigableMap<K, V> = MutableNavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.tailMap(after, inclusive))

	override val keys: RemoveOnlyNavigableSet<K> = RemoveOnlyNavigableSetImpl(this.kotlin.keySet())

	override val values: RemoveOnlySequencedCollection<V> = RemoveOnlySequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: RemoveOnlySequencedSet<MutableMap.MutableEntry<K, V>> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedEntrySet())
}

private fun <K> java.util.NavigableMap<K, *>.keySet(): java.util.NavigableSet<K> {
	val sequencedKeySet = this.sequencedKeySet()
	return object: java.util.SequencedSet<K> by sequencedKeySet, java.util.NavigableSet<K> {
		override fun comparator(): java.util.Comparator<in K>? = this@keySet.comparator()

		override fun reversed(): java.util.NavigableSet<K> = this@keySet.reversed().keySet()

		override fun first(): K = this@keySet.firstKey()

		override fun last(): K = this@keySet.lastKey()

		override fun pollFirst(): K? = if(isEmpty()) null else removeFirst()

		override fun pollLast(): K? = if(isEmpty()) null else removeLast()

		override fun descendingSet(): java.util.NavigableSet<K> = reversed()

		override fun descendingIterator(): MutableIterator<K> = reversed().iterator()

		override fun higher(e: K): K? = this@keySet.higherKey(e)

		override fun ceiling(e: K): K? = this@keySet.ceilingKey(e)

		override fun floor(e: K): K? = this@keySet.floorKey(e)

		override fun lower(e: K): K? = this@keySet.lowerKey(e)

		override fun subSet(fromElement: K, toElement: K): SortedSet<K> = subSet(fromElement, true, toElement, false)

		override fun subSet(fromElement: K, fromInclusive: Boolean, toElement: K, toInclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.subMap(fromElement, fromInclusive, toElement, toInclusive).keySet()

		override fun headSet(toElement: K): SortedSet<K> = headSet(toElement, false)

		override fun headSet(toElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.headMap(toElement, inclusive).keySet()

		override fun tailSet(fromElement: K): SortedSet<K> = tailSet(fromElement, true)

		override fun tailSet(fromElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.tailMap(fromElement, inclusive).keySet()
	}
}
