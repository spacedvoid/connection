@file:Suppress("UNCHECKED_CAST")

package io.github.spacedvoid.connection

import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// Collection -> kotlin.collections.Collection

fun <T> Collection<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> {
	override fun iterator(): Iterator<T> = this@asKotlin.iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> MutatingCollectionView<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> {
	override fun iterator(): Iterator<T> = (this@asKotlin as UnsafeIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> RemoveOnlyCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = throw UnsupportedOperationException("add")

	override fun addAll(elements: KotlinCollection<T>): Boolean = throw UnsupportedOperationException("addAll")

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

fun <T> MutableCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = this@asKotlin.add(element)

	override fun addAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.addAll(elements.asConnection())

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

// SequencedCollection -> kotlin.collections.Collection

fun <T> SequencedCollection<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> {
	override fun iterator(): Iterator<T> = this@asKotlin.iterator()

	override val size: Int
		get() = size()

	override fun isEmpty(): Boolean = size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> MutatingSequencedCollectionView<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> {
	override fun iterator(): Iterator<T> = (this@asKotlin as UnsafeIterable<T>).iterator()

	override val size: Int
		get() = size()

	override fun isEmpty(): Boolean = size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> RemoveOnlySequencedCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = size()

	override fun isEmpty(): Boolean = size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = throw UnsupportedOperationException("add")

	override fun addAll(elements: KotlinCollection<T>): Boolean = throw UnsupportedOperationException("addAll")

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

fun <T> MutableSequencedCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = size()

	override fun isEmpty(): Boolean = size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = this@asKotlin.add(element)

	override fun addAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.addAll(elements.asConnection())

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

// List -> kotlin.collections.List

fun <T> List<T>.asKotlin(): KotlinList<T> = object: KotlinList<T> {
	override fun iterator(): Iterator<T> = this@asKotlin.iterator()

	override fun listIterator(): ListIterator<T> = this@asKotlin.iterator()

	override fun listIterator(index: Int): ListIterator<T> {
		val iterator = this@asKotlin.iterator()
		for(unused in 0..index) iterator.next()
		return iterator
	}

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun reversed(): KotlinList<T> = this@asKotlin.reverse().asKotlin()

	override fun subList(fromIndex: Int, toIndex: Int): KotlinList<T> = this@asKotlin.slice(fromIndex, toIndex).asKotlin()

	override fun get(index: Int): T = this@asKotlin[index]

	override fun indexOf(element: T): Int = this@asKotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asKotlin.lastIndexOf(element)
}

fun <T> MutatingListView<T>.asKotlin(): KotlinList<T> = object: KotlinList<T> {
	override fun iterator(): Iterator<T> = (this@asKotlin as UnsafeListIterable<T>).iterator()

	override fun listIterator(): ListIterator<T> = (this@asKotlin as UnsafeListIterable<T>).iterator()

	override fun listIterator(index: Int): ListIterator<T> {
		val iterator = (this@asKotlin as UnsafeListIterable<T>).iterator()
		for(unused in 0..index) iterator.next()
		return iterator
	}

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun reversed(): KotlinList<T> = this@asKotlin.reverse().asKotlin()

	override fun subList(fromIndex: Int, toIndex: Int): KotlinList<T> = this@asKotlin.slice(fromIndex, toIndex).asKotlin()

	override fun get(index: Int): T = this@asKotlin[index]

	override fun indexOf(element: T): Int = this@asKotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asKotlin.lastIndexOf(element)
}

fun <T> MutableList<T>.asKotlin(): KotlinMutableList<T> = object: KotlinMutableList<T> {
	override fun iterator(): MutableListIterator<T> = (this@asKotlin as UnsafeMutableListIterable<T>).iterator()

	override fun listIterator(): MutableListIterator<T> = (this@asKotlin as UnsafeMutableListIterable<T>).iterator()

	override fun listIterator(index: Int): MutableListIterator<T> {
		val iterator = (this@asKotlin as UnsafeMutableListIterable<T>).iterator()
		for(unused in 0..index) iterator.next()
		return iterator
	}

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun reversed(): KotlinMutableList<T> = this@asKotlin.reverse().asKotlin()

	override fun subList(fromIndex: Int, toIndex: Int): KotlinMutableList<T> = this@asKotlin.slice(fromIndex, toIndex).asKotlin()

	override fun get(index: Int): T = this@asKotlin[index]

	override fun indexOf(element: T): Int = this@asKotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this@asKotlin.lastIndexOf(element)

	override fun add(element: T): Boolean = this@asKotlin.add(element)

	override fun add(index: Int, element: T) = this@asKotlin.add(index, element)

	override fun addAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.addAll(elements.asConnection())

	override fun addAll(index: Int, elements: KotlinCollection<T>): Boolean = this@asKotlin.slice(index, 0).addAll(elements.asConnection())

	override fun set(index: Int, element: T): T = this@asKotlin.set(index, element)

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAt(index: Int): T = this@asKotlin.removeAt(index)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun removeFirst(): T = this@asKotlin.removeFirst()

	override fun removeLast(): T = this@asKotlin.removeLast()

	override fun clear() = this@asKotlin.clear()
}

// Set -> kotlin.collections.Set

fun <T> Set<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> {
	override fun iterator(): Iterator<T> = this@asKotlin.iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> MutatingSetView<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> {
	override fun iterator(): Iterator<T> = (this@asKotlin as UnsafeIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> RemoveOnlySet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = throw UnsupportedOperationException("add")

	override fun addAll(elements: KotlinCollection<T>): Boolean = throw UnsupportedOperationException("addAll")

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

fun <T> MutableSet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = this@asKotlin.add(element)

	override fun addAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.addAll(elements.asConnection())

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

// SequencedSet -> kotlin.collections.Set

fun <T> SequencedSet<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> {
	override fun iterator(): Iterator<T> = this@asKotlin.iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> MutatingSequencedSetView<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> {
	override fun iterator(): Iterator<T> = (this@asKotlin as UnsafeIterable<T>).iterator()
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())
}

fun <T> RemoveOnlySequencedSet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = throw UnsupportedOperationException("add")

	override fun addAll(elements: KotlinCollection<T>): Boolean = throw UnsupportedOperationException("addAll")

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

fun <T> MutableSequencedSet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> {
	override fun iterator(): MutableIterator<T> = (this@asKotlin as UnsafeMutableIterable<T>).iterator()

	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun contains(element: T): Boolean = this@asKotlin.contains(element)

	override fun containsAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.containsAll(elements.asConnection())

	override fun add(element: T): Boolean = this@asKotlin.add(element)

	override fun addAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.addAll(elements.asConnection())

	override fun remove(element: T): Boolean = this@asKotlin.remove(element)

	override fun removeAll(elements: KotlinCollection<T>): Boolean = this@asKotlin.removeAll(elements.asConnection())

	override fun retainAll(elements: KotlinCollection<T>): Boolean {
		var removed = false
		for(e in elements) if(e !in this@asKotlin) removed = removed or this@asKotlin.remove(e)
		return removed
	}

	override fun clear() = this@asKotlin.clear()
}

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun get(key: K): V? = this@asKotlin[key]

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override val keys: KotlinSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinCollection<V> = this@asKotlin.values.asKotlin()

	override val entries: KotlinSet<KotlinMap.Entry<K, V>> = this@asKotlin.entries.asKotlin()
}

fun <K, V> MutatingMapView<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun get(key: K): V? = this@asKotlin[key]

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override val keys: KotlinSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinCollection<V> = this@asKotlin.values.asKotlin()

	override val entries: KotlinSet<KotlinMap.Entry<K, V>> = this@asKotlin.entries.asKotlin()
}

fun <K, V> MutableMap<K, V>.asKotlin(): KotlinMutableMap<K, V> = object: KotlinMutableMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun get(key: K): V? = this@asKotlin[key]

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override fun put(key: K, value: V): V? = this@asKotlin.put(key, value)

	override fun putAll(from: KotlinMap<out K, V>) = this@asKotlin.putAll(from.asConnection())

	override fun remove(key: K): V? = this@asKotlin.remove(key)

	override fun clear() = this@asKotlin.clear()

	override val keys: KotlinMutableSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinMutableCollection<V> = this@asKotlin.values.asKotlin()

	@Suppress("UNCHECKED_CAST")
	override val entries: KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>> = this@asKotlin.entries.asKotlin() as KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>>
}

// SequencedMap -> kotlin.collections.Map

fun <K, V> SequencedMap<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override fun get(key: K): V? = this@asKotlin[key]

	override val keys: KotlinSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinCollection<V> = this@asKotlin.values.asKotlin()

	@Suppress("UNCHECKED_CAST")
	override val entries: KotlinSet<KotlinMap.Entry<K, V>> = this@asKotlin.entries.asKotlin() as KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>>
}

fun <K, V> MutatingSequencedMapView<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override fun get(key: K): V? = this@asKotlin[key]

	override val keys: KotlinSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinCollection<V> = this@asKotlin.values.asKotlin()

	@Suppress("UNCHECKED_CAST")
	override val entries: KotlinSet<KotlinMap.Entry<K, V>> = this@asKotlin.entries.asKotlin() as KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>>
}

fun <K, V> MutableSequencedMap<K, V>.asKotlin(): KotlinMutableMap<K, V> = object: KotlinMutableMap<K, V> {
	override val size: Int
		get() = this@asKotlin.size()

	override fun isEmpty(): Boolean = this@asKotlin.size() == 0

	override fun containsKey(key: K): Boolean = this@asKotlin.containsKey(key)

	override fun containsValue(value: V): Boolean = this@asKotlin.containsValue(value)

	override fun get(key: K): V? = this@asKotlin[key]

	override fun put(key: K, value: V): V? = this@asKotlin.put(key, value)

	override fun putAll(from: KotlinMap<out K, V>) = this@asKotlin.putAll(from.asConnection())

	override fun remove(key: K): V? = this@asKotlin.remove(key)

	override fun clear() = this@asKotlin.clear()

	override val keys: KotlinMutableSet<K> = this@asKotlin.keys.asKotlin()

	override val values: KotlinMutableCollection<V> = this@asKotlin.values.asKotlin()

	@Suppress("UNCHECKED_CAST")
	override val entries: KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>> = this@asKotlin.entries.asKotlin() as KotlinMutableSet<KotlinMutableMap.MutableEntry<K, V>>
}
