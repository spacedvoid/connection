/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinMapImpl
import io.github.spacedvoid.connection.utils.naturalOrdering
import java.util.SortedSet

open class NavigableMapViewImpl<K, out V>(override val kotlin: java.util.NavigableMap<K, @UnsafeVariance V>): NavigableMapView<K, V>, SequencedMapViewImpl<K, V>(kotlin) {
	override fun reversed(): NavigableMapView<K, V> = NavigableMapViewImpl(this.kotlin.reversed())

	override val comparator: Comparator<in K> = this.kotlin.comparator() ?: naturalOrdering()

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): NavigableMapView<K, V> =
		NavigableMapViewImpl(this.kotlin.tailMap(after, inclusive))

	override fun higherEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		when(inclusive) {
			true -> this.kotlin.ceilingEntry(key)
			false -> this.kotlin.higherEntry(key)
		}?.let { it.key to it.value }

	override fun lowerEntry(key: K, inclusive: Boolean): Pair<K, V>? =
		when(inclusive) {
			true -> this.kotlin.floorEntry(key)
			false -> this.kotlin.lowerEntry(key)
		}?.let { it.key to it.value }

	override fun higherKey(key: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.ceilingKey(key) else this.kotlin.higherKey(key)

	override fun lowerKey(key: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.floorKey(key) else this.kotlin.lowerKey(key)

	override val keys: NavigableSetView<K> = NavigableSetViewImpl(this.kotlin.keySet())

	override val values: SequencedCollectionView<V> = SequencedCollectionViewImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSetView<Map.Entry<K, V>> = SequencedSetViewImpl(this.kotlin.sequencedEntrySet())
}

open class NavigableMapImpl<K, out V>(override val kotlin: java.util.NavigableMap<K, @UnsafeVariance V>): NavigableMap<K, V>, NavigableMapViewImpl<K, V>(kotlin) {
	override fun reversed(): NavigableMap<K, V> = NavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): NavigableMap<K, V> =
		NavigableMapImpl(this.kotlin.tailMap(after, inclusive))

	override val keys: NavigableSet<K> = NavigableSetImpl(this.kotlin.keySet())

	override val values: SequencedCollection<V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableNavigableMapImpl<K, V>(override val kotlin: java.util.NavigableMap<K, V>): MutableNavigableMap<K, V>, NavigableMapViewImpl<K, V>(kotlin) {
	override fun reversed(): MutableNavigableMap<K, V> = MutableNavigableMapImpl(this.kotlin.reversed())

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.subMap(from, fromInclusive, to, toInclusive))

	override fun headMap(before: K, inclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.headMap(before, inclusive))

	override fun tailMap(after: K, inclusive: Boolean): MutableNavigableMap<K, V> =
		MutableNavigableMapImpl(this.kotlin.tailMap(after, inclusive))

	override fun removeFirst(): Pair<K, V>? = this.kotlin.pollFirstEntry()?.toPair()

	override fun removeLast(): Pair<K, V>? = this.kotlin.pollLastEntry()?.toPair()

	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: MapView<out K, V>) = this.kotlin.putAll(KotlinMapImpl(map))

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()

	override val keys: MutableNavigableSet<K> = MutableNavigableSetImpl(this.kotlin.keySet())

	override val values: MutableSequencedCollection<V> = MutableSequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: MutableSequencedSet<MutableMap.MutableEntry<K, V>> = MutableSequencedSetImpl(this.kotlin.sequencedEntrySet())
}

internal fun <K> java.util.NavigableMap<K, *>.keySet(): java.util.NavigableSet<K> {
	val sequencedKeys = sequencedKeySet()
	return object: java.util.NavigableSet<K>, java.util.SequencedSet<K> by sequencedKeys {
		override fun reversed(): java.util.NavigableSet<K> = this@keySet.reversed().keySet()

		override fun lower(e: K): K? = this@keySet.lowerKey(e)

		override fun floor(e: K): K? = this@keySet.floorKey(e)

		override fun ceiling(e: K): K? = this@keySet.ceilingKey(e)

		override fun higher(e: K): K? = this@keySet.higherKey(e)

		override fun pollFirst(): K? = this@keySet.pollFirstEntry()?.key

		override fun pollLast(): K? = this@keySet.pollLastEntry()?.key

		override fun descendingSet(): java.util.NavigableSet<K> = reversed()

		override fun descendingIterator(): Iterator<K> = descendingSet().iterator()

		override fun subSet(fromElement: K, fromInclusive: Boolean, toElement: K, toInclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.subMap(fromElement, fromInclusive, toElement, toInclusive).keySet()

		override fun headSet(toElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.headMap(toElement, inclusive).keySet()

		override fun tailSet(fromElement: K, inclusive: Boolean): java.util.NavigableSet<K> =
			this@keySet.tailMap(fromElement, inclusive).keySet()

		override fun subSet(fromElement: K, toElement: K): SortedSet<K> = subSet(fromElement, true, toElement, false)

		override fun headSet(toElement: K): SortedSet<K> = headSet(toElement, false)

		override fun tailSet(fromElement: K): SortedSet<K> = tailSet(fromElement, true)

		override fun comparator(): java.util.Comparator<in K> = this@keySet.comparator()

		override fun first(): K? = this@keySet.firstKey()

		override fun last(): K? = this@keySet.lastKey()
	}
}
