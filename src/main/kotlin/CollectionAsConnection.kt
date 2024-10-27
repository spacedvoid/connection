package io.github.spacedvoid.connection

import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// kotlin.collections.Collection -> Collection

fun <T> KotlinCollection<T>.asConnection(): Collection<T> = object: Collection<T> {
	override fun iterator(): Iterator<T> = this@asConnection.iterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())
}

fun <T> KotlinCollection<T>.asViewConnection(): MutatingCollectionView<T> = object: MutatingCollectionView<T>, UnsafeIterable<T> {
	override fun iterator(): Iterator<T> = this@asViewConnection.iterator()

	override fun size(): Int = this@asViewConnection.size

	override fun contains(element: T): Boolean = this@asViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asViewConnection.containsAll(from.asKotlin())
}

fun <T> KotlinMutableCollection<T>.asRemoveOnlyConnection(): RemoveOnlyCollection<T> = object: RemoveOnlyCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asRemoveOnlyConnection.iterator()

	override fun size(): Int = this@asRemoveOnlyConnection.size

	override fun contains(element: T): Boolean = this@asRemoveOnlyConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.containsAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asRemoveOnlyConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.removeAll(from)

	override fun clear() = this@asRemoveOnlyConnection.clear()
}

fun <T> KotlinMutableCollection<T>.asConnection(): MutableCollection<T> = object: MutableCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asConnection.iterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())

	override fun add(element: T): Boolean = this@asConnection.add(element)

	override fun addAll(from: Collection<T>): Boolean = this@asConnection.addAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asConnection.removeAll(from.asKotlin().toSet())

	override fun clear() = this@asConnection.clear()
}

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.asConnection(): SequencedCollection<T> = object: SequencedCollection<T> {
	override fun iterator(): Iterator<T> = this@asConnection.iterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())

	override fun reverse(): SequencedCollection<T> = this@asConnection.reversed().asConnection()

	override fun first(): T = this@asConnection.first

	override fun last(): T = this@asConnection.last
}

fun <T> java.util.SequencedCollection<T>.asViewConnection(): MutatingSequencedCollectionView<T> = object: MutatingSequencedCollectionView<T>, UnsafeIterable<T> {
	override fun iterator(): Iterator<T> = this@asViewConnection.iterator()

	override fun size(): Int = this@asViewConnection.size

	override fun contains(element: T): Boolean = this@asViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asViewConnection.containsAll(from.asKotlin())

	override fun reverse(): MutatingSequencedCollectionView<T> = this@asViewConnection.reversed().asViewConnection()

	override fun first(): T = this@asViewConnection.first

	override fun last(): T = this@asViewConnection.last
}

fun <T> java.util.SequencedCollection<T>.asRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = object: RemoveOnlySequencedCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asRemoveOnlyConnection.iterator()

	override fun size(): Int = this@asRemoveOnlyConnection.size

	override fun contains(element: T): Boolean = this@asRemoveOnlyConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.containsAll(from.asKotlin())

	override fun reverse(): RemoveOnlySequencedCollection<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

	override fun first(): T = this@asRemoveOnlyConnection.first

	override fun last(): T = this@asRemoveOnlyConnection.last

	override fun remove(element: T): Boolean = this@asRemoveOnlyConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.removeAll(from)

	override fun removeFirst(): T = this@asRemoveOnlyConnection.removeFirst()

	override fun removeLast(): T = this@asRemoveOnlyConnection.removeLast()

	override fun clear() = this@asRemoveOnlyConnection.clear()
}

fun <T> java.util.SequencedCollection<T>.asMutableConnection(): MutableSequencedCollection<T> = object: MutableSequencedCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asMutableConnection.iterator()

	override fun size(): Int = this@asMutableConnection.size

	override fun contains(element: T): Boolean = this@asMutableConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asMutableConnection.containsAll(from.asKotlin())

	override fun reverse(): MutableSequencedCollection<T> = this@asMutableConnection.reversed().asMutableConnection()

	override fun first(): T = this@asMutableConnection.first

	override fun last(): T = this@asMutableConnection.last

	override fun removeFirst(): T = this@asMutableConnection.removeFirst()

	override fun removeLast(): T = this@asMutableConnection.removeLast()

	override fun add(element: T): Boolean = this@asMutableConnection.add(element)

	override fun addAll(from: Collection<T>): Boolean = this@asMutableConnection.addAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asMutableConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asMutableConnection.removeAll(from.asKotlin().toSet())

	override fun clear() = this@asMutableConnection.clear()
}

// kotlin.collections.List -> SequencedCollection

fun <T> KotlinList<T>.asSequencedConnection(): SequencedCollection<T> = object: SequencedCollection<T> {
	override fun iterator(): Iterator<T> = this@asSequencedConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asSequencedConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): SequencedCollection<T> = this@asSequencedConnection.asReversed().asSequencedConnection()

	override fun first(): T = this@asSequencedConnection.first()

	override fun last(): T = this@asSequencedConnection.last()
}

fun <T> KotlinList<T>.asSequencedViewConnection(): MutatingSequencedCollectionView<T> = object: MutatingSequencedCollectionView<T>, UnsafeIterable<T> {
	override fun iterator(): Iterator<T> = this@asSequencedViewConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asSequencedViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): MutatingSequencedCollectionView<T> = this@asSequencedViewConnection.asReversed().asSequencedViewConnection()

	override fun first(): T = this@asSequencedViewConnection.first()

	override fun last(): T = this@asSequencedViewConnection.last()
}

fun <T> KotlinMutableList<T>.asSequencedRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = object: RemoveOnlySequencedCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asSequencedRemoveOnlyConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asSequencedRemoveOnlyConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): MutableSequencedCollection<T> = asReversed().asConnection()

	override fun first(): T = this@asSequencedRemoveOnlyConnection.first()

	override fun last(): T = this@asSequencedRemoveOnlyConnection.last()

	override fun remove(element: T): Boolean = this@asSequencedRemoveOnlyConnection.remove(element)

	override fun removeFirst(): T = this@asSequencedRemoveOnlyConnection.removeFirst()

	override fun removeLast(): T = this@asSequencedRemoveOnlyConnection.removeLast()

	override fun removeAll(from: Collection<T>): Boolean = removeAll(from.asKotlin())

	override fun clear() = this@asSequencedRemoveOnlyConnection.clear()
}

fun <T> KotlinMutableList<T>.asSequencedConnection(): MutableSequencedCollection<T> = object: MutableSequencedCollection<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asSequencedConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asSequencedConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): MutableSequencedCollection<T> = asReversed().asConnection()

	override fun first(): T = this@asSequencedConnection.first()

	override fun last(): T = this@asSequencedConnection.last()

	override fun add(element: T): Boolean = this@asSequencedConnection.add(element)

	override fun addAll(from: Collection<T>): Boolean = this@asSequencedConnection.addAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asSequencedConnection.remove(element)

	override fun removeFirst(): T = this@asSequencedConnection.removeFirst()

	override fun removeLast(): T = this@asSequencedConnection.removeLast()

	override fun removeAll(from: Collection<T>): Boolean = removeAll(from.asKotlin())

	override fun clear() = this@asSequencedConnection.clear()
}

// kotlin.collections.List -> List

fun <T> KotlinList<T>.asConnection(): List<T> = object: List<T> {
	override fun iterator(): ListIterator<T> = this@asConnection.listIterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())

	override fun reverse(): List<T> = this@asConnection.asReversed().asConnection()

	override fun slice(startInclusive: Int, endExclusive: Int): List<T> = this@asConnection.subList(startInclusive, endExclusive).asConnection()

	override fun get(index: Int): T = this@asConnection[index]

	override fun first(): T = if(size() != 0) this@asConnection[0] else throw IllegalStateException("Empty list")

	override fun last(): T = if(size() != 0) this@asConnection[size() - 1] else throw IllegalStateException("Empty list")

	override fun indexOf(element: T): Int  = this@asConnection.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asConnection.lastIndexOf(element)
}

fun <T> KotlinList<T>.asViewConnection(): MutatingListView<T> = object: MutatingListView<T>, UnsafeListIterable<T> {
	override fun iterator(): ListIterator<T> = this@asViewConnection.listIterator()

	override fun size(): Int = this@asViewConnection.size

	override fun contains(element: T): Boolean = this@asViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asViewConnection.containsAll(from.asKotlin())

	override fun reverse(): MutatingListView<T> = this@asViewConnection.asReversed().asViewConnection()

	override fun slice(startInclusive: Int, endExclusive: Int): MutatingListView<T> = this@asViewConnection.subList(startInclusive, endExclusive).asViewConnection()

	override fun get(index: Int): T = this@asViewConnection[index]

	override fun first(): T = if(size() != 0) this@asViewConnection[0] else throw IllegalStateException("Empty list")

	override fun last(): T = if(size() != 0) this@asViewConnection[size() - 1] else throw IllegalStateException("Empty list")

	override fun indexOf(element: T): Int  = this@asViewConnection.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asViewConnection.lastIndexOf(element)
}

fun <T> KotlinMutableList<T>.asConnection(): MutableList<T> = object: MutableList<T>, UnsafeMutableListIterable<T> {
	override fun iterator(): MutableListIterator<T> = this@asConnection.listIterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())

	override fun reverse(): MutableList<T> = this@asConnection.asReversed().asConnection()

	override fun slice(startInclusive: Int, endExclusive: Int): MutableList<T> = this@asConnection.subList(startInclusive, endExclusive).asConnection()

	override fun get(index: Int): T = this@asConnection[index]

	override fun first(): T = this@asConnection.first()

	override fun last(): T = this@asConnection.last()

	override fun indexOf(element: T): Int = this@asConnection.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asConnection.lastIndexOf(element)

	override fun add(element: T): Boolean = this@asConnection.add(element)

	override fun add(index: Int, element: T) = this@asConnection.add(index, element)

	override fun addAll(from: Collection<T>): Boolean = this@asConnection.addAll(from.asKotlin())

	override fun set(index: Int, element: T): T = this@asConnection.set(index, element)

	override fun remove(element: T): Boolean = this@asConnection.remove(element)

	override fun removeAt(index: Int): T = this@asConnection.removeAt(index)

	override fun removeAll(from: Collection<T>) = this@asConnection.removeAll(from.asKotlin())

	override fun removeFirst(): T = this@asConnection.removeFirst()

	override fun removeLast(): T = this@asConnection.removeLast()

	override fun clear() = this@asConnection.clear()
}

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.asConnection(): SequencedSet<T> = object: SequencedSet<T> {
	override fun iterator(): Iterator<T> = this@asConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): SequencedSet<T> = this@asConnection.reversed().asConnection()

	override fun first(): T = this@asConnection.first()

	override fun last(): T = this@asConnection.last()
}

fun <T> java.util.SequencedSet<T>.asViewConnection(): MutatingSequencedSetView<T> = object: MutatingSequencedSetView<T>, UnsafeIterable<T> {
	override fun iterator(): Iterator<T> = this@asViewConnection.iterator()

	override fun size(): Int = size

	override fun contains(element: T): Boolean = this@asViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = containsAll(from.asKotlin())

	override fun reverse(): MutatingSequencedSetView<T> = this@asViewConnection.reversed().asViewConnection()

	override fun first(): T = this@asViewConnection.first()

	override fun last(): T = this@asViewConnection.last()
}

fun <T> java.util.SequencedSet<T>.asRemoveOnlyConnection(): RemoveOnlySequencedSet<T> = object: RemoveOnlySequencedSet<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asRemoveOnlyConnection.iterator()

	override fun size(): Int = this@asRemoveOnlyConnection.size

	override fun contains(element: T): Boolean = this@asRemoveOnlyConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.containsAll(from.asKotlin())

	override fun reverse(): RemoveOnlySequencedSet<T> = this@asRemoveOnlyConnection.reversed().asRemoveOnlyConnection()

	override fun first(): T = this@asRemoveOnlyConnection.first

	override fun last(): T = this@asRemoveOnlyConnection.last

	override fun remove(element: T): Boolean = this@asRemoveOnlyConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.removeAll(from.asKotlin().toSet())

	override fun removeFirst(): T = this@asRemoveOnlyConnection.removeFirst()

	override fun removeLast(): T = this@asRemoveOnlyConnection.removeLast()

	override fun clear() = this@asRemoveOnlyConnection.clear()
}

fun <T> java.util.SequencedSet<T>.asMutableConnection(): MutableSequencedSet<T> = object: MutableSequencedSet<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asMutableConnection.iterator()

	override fun size(): Int = this@asMutableConnection.size

	override fun contains(element: T): Boolean = this@asMutableConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asMutableConnection.containsAll(from.asKotlin())

	override fun reverse(): MutableSequencedSet<T> = this@asMutableConnection.reversed().asMutableConnection()

	override fun first(): T = this@asMutableConnection.first

	override fun last(): T = this@asMutableConnection.last

	override fun add(element: T): Boolean = this@asMutableConnection.add(element)

	override fun addAll(from: Collection<T>): Boolean = this@asMutableConnection.addAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asMutableConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asMutableConnection.removeAll(from.asKotlin().toSet())

	override fun removeFirst(): T = this@asMutableConnection.removeFirst()

	override fun removeLast(): T = this@asMutableConnection.removeLast()

	override fun clear() = this@asMutableConnection.clear()
}

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.asConnection(): Set<T> = object: Set<T> {
	override fun iterator(): Iterator<T> = this@asConnection.iterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())
}

fun <T> KotlinSet<T>.asViewConnection(): MutatingSetView<T> = object: MutatingSetView<T>, UnsafeIterable<T> {
	override fun iterator(): Iterator<T> = this@asViewConnection.iterator()

	override fun size(): Int = this@asViewConnection.size

	override fun contains(element: T): Boolean = this@asViewConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asViewConnection.containsAll(from.asKotlin())
}

fun <T> KotlinMutableSet<T>.asRemoveOnlyConnection(): RemoveOnlySet<T> = object: RemoveOnlySet<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asRemoveOnlyConnection.iterator()

	override fun size(): Int = this@asRemoveOnlyConnection.size

	override fun contains(element: T): Boolean = this@asRemoveOnlyConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.containsAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asRemoveOnlyConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asRemoveOnlyConnection.removeAll(from.asKotlin().toSet())

	override fun clear() = this@asRemoveOnlyConnection.clear()
}

fun <T> KotlinMutableSet<T>.asConnection(): MutableSet<T> = object: MutableSet<T>, UnsafeMutableIterable<T> {
	override fun iterator(): MutableIterator<T> = this@asConnection.iterator()

	override fun size(): Int = this@asConnection.size

	override fun contains(element: T): Boolean = this@asConnection.contains(element)

	override fun containsAll(from: Collection<T>): Boolean = this@asConnection.containsAll(from.asKotlin())

	override fun add(element: T): Boolean = this@asConnection.add(element)

	override fun addAll(from: Collection<T>): Boolean = this@asConnection.addAll(from.asKotlin())

	override fun remove(element: T): Boolean = this@asConnection.remove(element)

	override fun removeAll(from: Collection<T>): Boolean = this@asConnection.removeAll(from.asKotlin().toSet())

	override fun clear() = this@asConnection.clear()
}

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.asConnection(): Map<K, V> = object: Map<K, V> {
	override fun size(): Int = this@asConnection.size

	override fun containsKey(key: K): Boolean = this@asConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asConnection.containsValue(value)

	override fun get(key: K): V? = this@asConnection[key]

	override val keys: Set<K> = this@asConnection.keys.asConnection()

	override val values: Collection<V> = this@asConnection.values.asConnection()

	override val entries: Set<KotlinMap.Entry<K, V>> = this@asConnection.entries.asConnection()
}

fun <K, V> KotlinMap<K, V>.asViewConnection(): MutatingMapView<K, V> = object: MutatingMapView<K, V> {
	override fun size(): Int = this@asViewConnection.size

	override fun containsKey(key: K): Boolean = this@asViewConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asViewConnection.containsValue(value)

	override fun get(key: K): V? = this@asViewConnection[key]

	override val keys: MutatingSetView<K> = this@asViewConnection.keys.asViewConnection()

	override val values: MutatingCollectionView<V> = this@asViewConnection.values.asViewConnection()

	override val entries: MutatingSetView<KotlinMap.Entry<K, V>> = this@asViewConnection.entries.asViewConnection()
}

fun <K, V> KotlinMutableMap<K, V>.asConnection(): MutableMap<K, V> = object: MutableMap<K, V> {
	override fun size(): Int = this@asConnection.size

	override fun get(key: K): V? = this@asConnection[key]

	override fun containsKey(key: K): Boolean = this@asConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asConnection.containsValue(value)

	override fun put(key: K, value: V): V? = this@asConnection.put(key, value)

	override fun putAll(map: Map<out K, V>) = this@asConnection.putAll(map.asKotlin())

	override fun remove(key: K): V? = this@asConnection.remove(key)

	override fun clear() = this@asConnection.clear()

	override val keys: RemoveOnlySet<K> = this@asConnection.keys.asConnection()

	override val values: RemoveOnlyCollection<V> = this@asConnection.values.asConnection()

	override val entries: MutableSet<out KotlinMutableMap.MutableEntry<K, V>> = this@asConnection.entries.asConnection()
}

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.asConnection(): SequencedMap<K, V> = object: SequencedMap<K, V> {
	override fun size(): Int = this@asConnection.size

	override fun containsKey(key: K): Boolean = this@asConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asConnection.containsValue(value)

	override fun get(key: K): V? = this@asConnection[key]

	override val keys: SequencedSet<K> = this@asConnection.sequencedKeySet().asConnection()

	override val values: SequencedCollection<V> = this@asConnection.sequencedValues().asConnection()

	override val entries: SequencedSet<out KotlinMap.Entry<K, V>> = this@asConnection.sequencedEntrySet().asConnection()
}

fun <K, V> java.util.SequencedMap<K, V>.asViewConnection(): MutatingSequencedMapView<K, V> = object: MutatingSequencedMapView<K, V> {
	override fun size(): Int = this@asViewConnection.size

	override fun containsKey(key: K): Boolean = this@asViewConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asViewConnection.containsValue(value)

	override fun get(key: K): V? = this@asViewConnection[key]

	override val keys: MutatingSequencedSetView<K> = this@asViewConnection.sequencedKeySet().asViewConnection()

	override val values: MutatingSequencedCollectionView<V> = this@asViewConnection.sequencedValues().asViewConnection()

	override val entries: MutatingSequencedSetView<out KotlinMap.Entry<K, V>> = this@asViewConnection.sequencedEntrySet().asViewConnection()
}

fun <K, V> java.util.SequencedMap<K, V>.asMutableConnection(): MutableSequencedMap<K, V> = object: MutableSequencedMap<K, V> {
	override fun size(): Int = this@asMutableConnection.size

	override fun containsKey(key: K): Boolean = this@asMutableConnection.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asMutableConnection.containsValue(value)

	override fun get(key: K): V? = this@asMutableConnection[key]

	override fun put(key: K, value: V): V? = this@asMutableConnection.put(key, value)

	override fun putAll(map: Map<out K, V>) = this@asMutableConnection.putAll(map.asKotlin())

	override fun remove(key: K): V? = this@asMutableConnection.remove(key)

	override fun clear() = this@asMutableConnection.clear()

	override val keys: MutableSequencedSet<K> = this@asMutableConnection.sequencedKeySet().asMutableConnection()

	override val values: MutableSequencedCollection<V> = this@asMutableConnection.sequencedValues().asMutableConnection()

	override val entries: MutableSequencedSet<out KotlinMutableMap.MutableEntry<K, V>> = this@asMutableConnection.sequencedEntrySet().asMutableConnection()
}
