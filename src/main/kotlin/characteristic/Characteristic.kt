package io.github.spacedvoid.connection.characteristic

import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.Map

interface Wrapper<out T> {
	val origin: T
}

interface Collectable<T> {
	fun size(): Int

	operator fun contains(element: T): Boolean

	fun containsAll(from: Collection<T>): Boolean
}

interface RemoveOnly<T>: Collectable<T> {
	fun remove(element: T): Boolean

	fun removeAll(from: Collection<T>): Boolean

	fun retainAll(from: Collection<T>): Boolean

	fun clear()
}

interface Mutable<T>: RemoveOnly<T> {
	fun add(element: T): Boolean

	fun addAll(from: Collection<T>): Boolean
}

interface Sequenced<T> /* : Collectable<T> */ {
	fun reverse(): Sequenced<T>

	fun first(): T

	fun last(): T
}

interface RemoveOnlySequenced<T>: Sequenced<T> {
	fun removeFirst(): T

	fun removeLast(): T
}

interface MutableSequenced<T>: RemoveOnlySequenced<T> {
	fun addFirst(element: T)

	fun addLast(element: T)
}

interface Listed<T> /* : Sequenced<T> */ {
	fun subList(startInclusive: Int, endExclusive: Int): Listed<T>

	operator fun get(index: Int): T

	fun indexOf(element: T): Int

	fun lastIndexOf(element: T): Int
}

interface MutableListed<T>: Listed<T> {
	fun add(index: Int, element: T)

	operator fun set(index: Int, element: T): T

	fun removeAt(index: Int): T
}

interface Navigable<T> /* : Sequenced<T> */ {
	val comparator: Comparator<in T>?

	fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): Navigable<T>

	fun headSet(before: T, inclusive: Boolean): Navigable<T>

	fun tailSet(after: T, inclusive: Boolean): Navigable<T>

	fun higher(than: T, inclusive: Boolean): T?

	fun lower(than: T, inclusive: Boolean): T?
}

interface Mappable<K, V> {
	fun size(): Int

	fun containsKey(key: K): Boolean

	fun containsValue(value: V): Boolean

	operator fun get(key: K): V?

	val keys: Collectable<out K>

	val values: Collectable<out V>

	val entries: Collectable<out kotlin.collections.Map.Entry<K, V>>
}

interface MutableMappable<K, V>: Mappable<K, V> {
	fun put(key: K, value: V): V?

	fun putAll(map: Map<out K, out V>)

	fun remove(key: K): V?

	fun clear()
}

interface SequencedMappable<K, V> /* : Mappable<K, V> */ {
	fun reversed(): SequencedMappable<K, V>

	fun first(): Pair<K, V>?

	fun last(): Pair<K, V>?

	fun firstKey(): K

	fun lastKey(): K
}

interface MutableSequencedMappable<K, V>: SequencedMappable<K, V> {
	fun removeFirst(): Pair<K, V>?

	fun removeLast(): Pair<K, V>?
}

interface NavigableMappable<K, V> /* : SequencedMappable<K, V> */ {
	val comparator: Comparator<in K>?

	fun subMap(from: K, to: K, fromInclusive: Boolean, toInclusive: Boolean): NavigableMappable<K, V>

	fun headMap(before: K, inclusive: Boolean): NavigableMappable<K, V>

	fun tailMap(after: K, inclusive: Boolean): NavigableMappable<K, V>

	fun higherEntry(than: K, inclusive: Boolean): Pair<K, V>?

	fun lowerEntry(than: K, inclusive: Boolean): Pair<K, V>?

	fun higherKey(than: K, inclusive: Boolean): K?

	fun lowerKey(than: K, inclusive: Boolean): K?
}
