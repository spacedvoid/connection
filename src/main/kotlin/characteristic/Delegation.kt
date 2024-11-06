package io.github.spacedvoid.connection.characteristic

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

	override fun retainAll(from: io.github.spacedvoid.connection.Collection<T>): Boolean = this.kotlin.retainAll(from.asKotlin().toSet())

	override fun clear() = this.kotlin.clear()
}

class MutableImpl<T>(private val kotlin: MutableCollection<T>): RemoveOnly<T> by RemoveOnlyImpl(kotlin), Mutable<T> {
	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(from: io.github.spacedvoid.connection.Collection<T>): Boolean = this.kotlin.addAll(from.asKotlin())
}

class SequencedImpl<T>(private val kotlin: java.util.SequencedCollection<T>): Sequenced<T> {
	override fun reverse(): Nothing = implementationRequired("reverse")

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
	override fun subList(startInclusive: Int, endExclusive: Int): Nothing = implementationRequired("slice")

	override fun get(index: Int): T = this.kotlin[index]

	override fun indexOf(element: T): Int = this.kotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this.kotlin.lastIndexOf(element)
}

class MutableListedImpl<T>(private val kotlin: MutableList<T>): Listed<T> by ListedImpl(kotlin), MutableListed<T> {
	override fun add(index: Int, element: T) = this.kotlin.add(index, element)

	override fun set(index: Int, element: T): T = this.kotlin.set(index, element)

	override fun removeAt(index: Int): T = this.kotlin.removeAt(index)
}

class SortedNavigableImpl<T>(private val kotlin: SortedSet<T>): Navigable<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): Nothing = implementationRequired("subSet")

	override fun headSet(before: T, inclusive: Boolean): Nothing = implementationRequired("headSet")

	override fun tailSet(after: T, inclusive: Boolean): Nothing = implementationRequired("tailSet")

	override fun lower(than: T, inclusive: Boolean): T? =
		if(this.kotlin.isEmpty()) null
		else if(inclusive) this.kotlin.reversed().tailSet(than).first
		else this.kotlin.headSet(than).last

	override fun higher(than: T, inclusive: Boolean): T? =
		if(this.kotlin.isEmpty()) null
		else if(inclusive) this.kotlin.tailSet(than).first
		else this.kotlin.reversed().headSet(than).last
}

class NavigableImpl<T>(private val kotlin: NavigableSet<T>): Navigable<T> {
	override val comparator: Comparator<in T>? = this.kotlin.comparator()

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): Nothing = implementationRequired("subSet")

	override fun headSet(before: T, inclusive: Boolean): Nothing = implementationRequired("headSet")

	override fun tailSet(after: T, inclusive: Boolean): Nothing = implementationRequired("tailSet")

	override fun higher(than: T, inclusive: Boolean): T? = if(inclusive) this.kotlin.ceiling(than) else this.kotlin.higher(than)

	override fun lower(than: T, inclusive: Boolean): T? = if(inclusive) this.kotlin.floor(than) else this.kotlin.lower(than)
}

class MappableImpl<K, V>(private val kotlin: Map<K, V>): Mappable<K, V> {
	override fun size(): Int = this.kotlin.size

	override fun containsKey(key: K): Boolean = this.kotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this.kotlin.containsValue(value)

	override fun get(key: K): V? = this.kotlin[key]

	override val keys: Nothing = implementationRequired("keys")

	override val values: Nothing = implementationRequired("values")

	override val entries: Nothing = implementationRequired("entries")
}

class MutableMappableImpl<K, V>(private val kotlin: MutableMap<K, V>): Mappable<K, V> by MappableImpl(kotlin), MutableMappable<K, V> {
	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: io.github.spacedvoid.connection.Map<out K, out V>) = this.kotlin.putAll(map.asKotlin())

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()
}

class SequencedMappableImpl<K, V>(private val kotlin: SequencedMap<K, V>): SequencedMappable<K, V> {
	override fun reversed(): Nothing = implementationRequired("reversed")

	override fun first(): Pair<K, V>? = this.kotlin.firstEntry()?.let { it.key to it.value }

	override fun last(): Pair<K, V>? = this.kotlin.lastEntry()?.let { it.key to it.value }

	override fun firstKey(): K = first()?.first ?: throw NoSuchElementException("Map is empty")

	override fun lastKey(): K = last()?.first ?: throw NoSuchElementException("Map is empty")
}

class MutableSequencedMappableImpl<K, V>(private val kotlin: SequencedMap<K, V>): SequencedMappable<K, V> by SequencedMappableImpl(kotlin), MutableSequencedMappable<K, V> {
	override fun removeFirst(): Pair<K, V>? = this.kotlin.pollFirstEntry()?.let { it.key to it.value }

	override fun removeLast(): Pair<K, V>? = this.kotlin.pollLastEntry()?.let { it.key to it.value }
}

class SortedNavigableMappableImpl<K, V>(private val kotlin: SortedMap<K, V>): NavigableMappable<K, V> {
	override val comparator: Comparator<in K>? = this.kotlin.comparator()

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): Nothing = implementationRequired("subMap")

	override fun headMap(before: K, inclusive: Boolean): Nothing = implementationRequired("headMap")

	override fun tailMap(after: K, inclusive: Boolean): Nothing = implementationRequired("tailMap")

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.reversed().tailMap(than).firstEntry()
			else -> this.kotlin.headMap(than).lastEntry()
		}?.let { it.key to it.value }

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.tailMap(than).firstEntry()
			else -> this.kotlin.reversed().headMap(than).lastEntry()
		}?.let { it.key to it.value }

	override fun higherKey(than: K, inclusive: Boolean): K? = higherEntry(than, inclusive)?.first

	override fun lowerKey(than: K, inclusive: Boolean): K? = lowerEntry(than, inclusive)?.first
}

class NavigableMappableImpl<K, V>(private val kotlin: NavigableMap<K, V>): NavigableMappable<K, V> {
	override val comparator: Comparator<in K>? = this.kotlin.comparator()

	override fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): Nothing = implementationRequired("subMap")

	override fun headMap(before: K, inclusive: Boolean): Nothing = implementationRequired("headMap")

	override fun tailMap(after: K, inclusive: Boolean): Nothing = implementationRequired("tailMap")

	override fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.ceilingEntry(than)
			else -> this.kotlin.higherEntry(than)
		}?.let { it.key to it.value }

	override fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>? =
		when {
			inclusive -> this.kotlin.floorEntry(than)
			else -> this.kotlin.lowerEntry(than)
		}?.let { it.key to it.value }

	override fun higherKey(than: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.ceilingKey(than) else this.kotlin.higherKey(than)

	override fun lowerKey(than: K, inclusive: Boolean): K? = if(inclusive) this.kotlin.floorKey(than) else this.kotlin.lowerKey(than)
}

private fun implementationRequired(name: String): Nothing = throw NotImplementedError("This method or property $name requires manual implementation")
