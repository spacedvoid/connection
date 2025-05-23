package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.keySet
import java.util.NavigableMap
import java.util.NavigableSet
import java.util.SortedMap

open class KotlinNavigableMapImpl<K, V>(private val connection: NavigableMapView<K, V>): KotlinSequencedMapImpl<K, V>(connection), NavigableMap<K, V> {
	override fun reversed(): NavigableMap<K, V> = KotlinNavigableMapImpl(this.connection.reversed())

	override fun lowerEntry(key: K): Map.Entry<K, V>? = this.connection.lowerEntry(key, false)?.toEntry()

	override fun lowerKey(key: K): K? = this.connection.lowerKey(key, false)

	override fun floorEntry(key: K): Map.Entry<K, V>? = this.connection.lowerEntry(key, true)?.toEntry()

	override fun floorKey(key: K): K? = this.connection.lowerKey(key, true)

	override fun ceilingEntry(key: K): Map.Entry<K, V>? = this.connection.higherEntry(key, true)?.toEntry()

	override fun ceilingKey(key: K): K? = this.connection.higherKey(key, true)

	override fun higherEntry(key: K): Map.Entry<K, V>? = this.connection.higherEntry(key, false)?.toEntry()

	override fun higherKey(key: K): K? = this.connection.higherKey(key, false)

	override fun descendingMap(): NavigableMap<K, V> = reversed()

	override fun navigableKeySet(): NavigableSet<K> = keySet()

	override fun descendingKeySet(): NavigableSet<K> = descendingMap().keySet()

	override fun subMap(fromKey: K, fromInclusive: Boolean, toKey: K, toInclusive: Boolean): NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.subMap(fromKey, toKey, fromInclusive, toInclusive))

	override fun headMap(toKey: K, inclusive: Boolean): NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.headMap(toKey, inclusive))

	override fun tailMap(fromKey: K, inclusive: Boolean): NavigableMap<K, V> =
		KotlinNavigableMapImpl(this.connection.tailMap(fromKey, inclusive))

	override fun subMap(fromKey: K, toKey: K): SortedMap<K, V> = subMap(fromKey, true, toKey, false)

	override fun headMap(toKey: K): SortedMap<K, V> = headMap(toKey, false)

	override fun tailMap(fromKey: K): SortedMap<K, V> = tailMap(fromKey, true)

	override fun firstEntry(): Map.Entry<K, V>? = this.connection.first()?.toEntry()

	override fun lastEntry(): Map.Entry<K, V>? = this.connection.last()?.toEntry()

	override fun pollFirstEntry(): Map.Entry<K, V>? =
		if(this.connection is MutableNavigableMap<K, V>) this.connection.removeFirst()?.toEntry() else throw UnsupportedOperationException("pollFirstEntry")

	override fun pollLastEntry(): Map.Entry<K, V>? =
		if(this.connection is MutableNavigableMap<K, V>) this.connection.removeLast()?.toEntry() else throw UnsupportedOperationException("pollLastEntry")

	override fun comparator(): Comparator<in K> = this.connection.comparator

	override fun firstKey(): K? = this.connection.firstKey()

	override fun lastKey(): K? = this.connection.lastKey()
}

private data class ImmutableEntry<K, V>(override val key: K, override val value: V): Map.Entry<K, V>

private fun <K, V> Pair<K, V>.toEntry(): Map.Entry<K, V> = ImmutableEntry(this.first, this.second)
