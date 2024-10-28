package io.github.spacedvoid.connection.characteristic

import io.github.spacedvoid.connection.asConnection
import io.github.spacedvoid.connection.asKotlin
import java.util.NavigableMap
import java.util.NavigableSet
import java.util.SequencedMap
import java.util.SortedMap
import java.util.SortedSet

class WrapperImpl<out T>(override val origin: T): Wrapper<T>

class CollectableImpl<T>(private val kotlin: Collection<T>): Collectable<T> {
	override fun size(): Int = this.kotlin.size

	override fun contains(element: T): Boolean = this.kotlin.contains(element)

	override fun containsAll(from: io.github.spacedvoid.connection.Collection<T>): Boolean = this.kotlin.containsAll(from.asKotlin())
}

class RemoveOnlyImpl<T>(private val kotlin: MutableCollection<T>): Collectable<T> by CollectableImpl(kotlin), RemoveOnly<T> {
	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeAll(from: io.github.spacedvoid.connection.Collection<T>): Boolean = this.kotlin.removeAll(from.asKotlin().toSet())

	override fun clear() = this.kotlin.clear()
}

class MutableImpl<T>(private val kotlin: MutableCollection<T>): RemoveOnly<T> by RemoveOnlyImpl(kotlin), Mutable<T> {
	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(from: io.github.spacedvoid.connection.Collection<T>): Boolean = this.kotlin.addAll(from.asKotlin())
}

class SequencedImpl<T>(private val kotlin: java.util.SequencedCollection<T>): Sequenced<T> {
	override fun reverse(): Sequenced<T> = this.kotlin.reversed().asConnection()

	override fun first(): T = this.kotlin.first

	override fun last(): T = this.kotlin.last
}

class RemoveOnlySequencedImpl<T>(private val kotlin: java.util.SequencedCollection<T>): Sequenced<T> by SequencedImpl(kotlin), RemoveOnlySequenced<T> {
	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()
}

class MutableSequencedImpl<T>(private val kotlin: java.util.SequencedCollection<T>): RemoveOnlySequenced<T> by RemoveOnlySequencedImpl(kotlin), MutableSequenced<T> {
	override fun addFirst(element: T) = this.kotlin.addFirst(element)

	override fun addLast(element: T) = this.kotlin.addLast(element)
}

class ListedImpl<T>(private val kotlin: List<T>): Listed<T> {
	override fun slice(startInclusive: Int, endExclusive: Int): Listed<T> = this.kotlin.subList(startInclusive, endExclusive).asConnection()

	override fun get(index: Int): T = this.kotlin[index]

	override fun indexOf(element: T): Int = this.kotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this.kotlin.lastIndexOf(element)
}

class MutableListedImpl<T>(private val kotlin: MutableList<T>): Listed<T> by ListedImpl(kotlin), MutableListed<T> {
	override fun add(index: Int, element: T) = this.kotlin.add(index, element)

	override fun set(index: Int, element: T): T = this.kotlin.set(index, element)

	override fun removeAt(index: Int): T = this.kotlin.removeAt(index)
}

class SortedImpl<T>(private val kotlin: SortedSet<T>): Sorted<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): Sorted<T> = this.kotlin.subSet(from, to).asConnection()

	override fun headSet(before: T, inclusive: Boolean): Sorted<T> = this.kotlin.headSet(before).asConnection()

	override fun tailSet(after: T, inclusive: Boolean): Sorted<T> = this.kotlin.tailSet(after).asConnection()
}

class NavigableImpl<T>(private val kotlin: NavigableSet<T>): Navigable<T> {
	override fun higher(than: T, inclusive: Boolean): T? = this.kotlin.higher(than)

	override fun lower(than: T, inclusive: Boolean): T? = this.kotlin.lower(than)
}

class MappableImpl<K, V>(private val kotlin: Map<K, V>): Mappable<K, V> {
	override fun size(): Int = this.kotlin.size

	override fun containsKey(key: K): Boolean = this.kotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this.kotlin.containsValue(value)

	override fun get(key: K): V? = this.kotlin[key]

	override val keys: Collectable<out K> = this.kotlin.keys.asConnection()

	override val values: Collectable<out V> = this.kotlin.values.asConnection()

	override val entries: Collectable<out Map.Entry<K, V>> = this.kotlin.entries.asConnection()
}

class MutableMappableImpl<K, V>(private val kotlin: MutableMap<K, V>): Mappable<K, V> by MappableImpl(kotlin), MutableMappable<K, V> {
	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: io.github.spacedvoid.connection.Map<out K, out V>) = this.kotlin.putAll(map.asKotlin())

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()
}

class SequencedMappableImpl<K, V>(private val kotlin: SequencedMap<K, V>): SequencedMappable<K, V> {
	override fun reversed(): SequencedMappable<K, V> = this.kotlin.reversed().asConnection()

	override fun first(): Pair<K, V>? = this.kotlin.firstEntry()?.let { it.key to it.value }

	override fun last(): Pair<K, V>? = this.kotlin.lastEntry()?.let { it.key to it.value }
}

class MutableSequencedMappableImpl<K, V>(private val kotlin: SequencedMap<K, V>): SequencedMappable<K, V> by SequencedMappableImpl(kotlin), MutableSequencedMappable<K, V> {
	override fun removeFirst(): Pair<K, V>? = this.kotlin.pollFirstEntry()?.let { it.key to it.value }

	override fun removeLast(): Pair<K, V>? = this.kotlin.pollLastEntry()?.let { it.key to it.value }
}

class SortedMappableImpl<K, V>(private val kotlin: SortedMap<K, V>): SortedMappable<K, V> {
	override val comparator: Comparator<in K>? = this.kotlin.comparator()

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): SortedMappable<K, V> = this.kotlin.subMap(from, to).asConnection()

	override fun headMap(before: K, inclusive: Boolean): SortedMappable<K, V> = this.kotlin.headMap(before).asConnection()

	override fun tailMap(after: K, inclusive: Boolean): SortedMappable<K, V> = this.kotlin.tailMap(after).asConnection()

	override fun firstKey(): K = this.kotlin.firstKey()

	override fun lastKey(): K = this.kotlin.lastKey()

	override fun reversed(): SortedMappable<K, V> = this.kotlin.reversed().asConnection()
}

class NavigableMappableImpl<K, V>(private val kotlin: NavigableMap<K, V>): NavigableMappable<K, V> {
	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		(if(inclusive) this.kotlin.floorEntry(than) else this.kotlin.lowerEntry(than))?.let { it.key to it.value }

	override fun lowerKey(than: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.floorKey(than) else this.kotlin.lowerKey(than)

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		(if(inclusive) this.kotlin.ceilingEntry(than) else this.kotlin.higherEntry(than))?.let { it.key to it.value }

	override fun higherKey(than: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.ceilingKey(than) else this.kotlin.higherKey(than)
}
