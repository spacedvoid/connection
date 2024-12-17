package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.MapView
import io.github.spacedvoid.connection.MutableSortedNavigableMap
import io.github.spacedvoid.connection.RemoveOnlySequencedCollection
import io.github.spacedvoid.connection.RemoveOnlySequencedSet
import io.github.spacedvoid.connection.RemoveOnlySortedNavigableSet
import io.github.spacedvoid.connection.SequencedCollection
import io.github.spacedvoid.connection.SequencedSet
import io.github.spacedvoid.connection.SortedNavigableMap
import io.github.spacedvoid.connection.SortedNavigableMapView
import io.github.spacedvoid.connection.SortedNavigableSet
import io.github.spacedvoid.connection.SortedNavigableSetView
import io.github.spacedvoid.connection.utils.naturalOrdering
import java.util.SortedMap
import java.util.SortedSet

// For the implementations of subMap() and its variants, see [SortedNavigableSetImpl].

open class SortedNavigableMapViewImpl<K, V>(private val kotlin: SortedMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), SortedNavigableMapView<K, V> {
	final override val comparator: Comparator<in K> = this.kotlin.comparator() ?: naturalOrdering()

	override fun reversed(): SortedNavigableMapView<K, V> = SortedNavigableMapViewImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMapView<K, V> =
		SortedNavigableMapViewImpl(this.kotlin.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to))

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMapView<K, V> =
		SortedNavigableMapViewImpl(this.kotlin.headMap(higherKey(before, !inclusive) ?: before))

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMapView<K, V> =
		SortedNavigableMapViewImpl(this.kotlin.tailMap(higherKey(after, inclusive) ?: after))

	override val keys: SortedNavigableSetView<out K> = SortedNavigableSetViewImpl(this.kotlin.keySet())

	override val MapView<K, V>.kotlin: SortedMap<K, V>
		get() = this@SortedNavigableMapViewImpl.kotlin
}

open class SortedNavigableMapImpl<K, V>(private val kotlin: SortedMap<K, V>): SortedNavigableMapViewImpl<K, V>(kotlin), SortedNavigableMap<K, V> {
	override fun reversed(): SortedNavigableMap<K, V> = SortedNavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedNavigableMap<K, V> =
		SortedNavigableMapImpl(this.kotlin.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to))

	override fun headMap(before: K, inclusive: Boolean): SortedNavigableMap<K, V> =
		SortedNavigableMapImpl(this.kotlin.headMap(higherKey(before, !inclusive) ?: before))

	override fun tailMap(after: K, inclusive: Boolean): SortedNavigableMap<K, V> =
		SortedNavigableMapImpl(this.kotlin.tailMap(higherKey(after, inclusive) ?: after))

	override val keys: SortedNavigableSet<out K> = SortedNavigableSetImpl(this.kotlin.keySet())

	override val values: SequencedCollection<out V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<out Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableSortedNavigableMapImpl<K, V>(private val kotlin: SortedMap<K, V>): SortedNavigableMapViewImpl<K, V>(kotlin), MutableSortedNavigableMap<K, V> {
	override fun reversed(): MutableSortedNavigableMap<K, V> = MutableSortedNavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableSortedNavigableMap<K, V> =
		MutableSortedNavigableMapImpl(this.kotlin.subMap(higherKey(from, fromInclusive) ?: from, higherKey(to, !toInclusive) ?: to))

	override fun headMap(before: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
		MutableSortedNavigableMapImpl(this.kotlin.headMap(higherKey(before, !inclusive) ?: before))

	override fun tailMap(after: K, inclusive: Boolean): MutableSortedNavigableMap<K, V> =
		MutableSortedNavigableMapImpl(this.kotlin.tailMap(higherKey(after, inclusive) ?: after))

	override val keys: RemoveOnlySortedNavigableSet<K> = RemoveOnlySortedNavigableSetImpl(this.kotlin.keySet())

	override val values: RemoveOnlySequencedCollection<V> = RemoveOnlySequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: RemoveOnlySequencedSet<MutableMap.MutableEntry<K, V>> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedEntrySet())
}

private fun <K> SortedMap<K, *>.keySet(): SortedSet<K> = object: java.util.SequencedSet<K> by this.sequencedKeySet(), SortedSet<K> {
	override fun comparator(): java.util.Comparator<in K>? = this@keySet.comparator()

	override fun reversed(): SortedSet<K> = this@keySet.reversed().keySet()

	override fun first(): K = this@keySet.firstKey()

	override fun last(): K = this@keySet.lastKey()

	override fun subSet(fromElement: K, toElement: K): SortedSet<K> = this@keySet.subMap(fromElement, toElement).keySet()

	override fun headSet(toElement: K): SortedSet<K> = this@keySet.headMap(toElement).keySet()

	override fun tailSet(fromElement: K): SortedSet<K> = this@keySet.tailMap(fromElement).keySet()
}
