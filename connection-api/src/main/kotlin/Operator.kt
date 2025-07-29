/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.random.Random
import kotlin.collections.first as kotlinFirst
import kotlin.collections.firstOrNull as kotlinFirstOrNull
import kotlin.collections.last as kotlinLast
import kotlin.collections.lastOrNull as kotlinLastOrNull
import kotlin.collections.shuffle as kotlinShuffle
import kotlin.collections.shuffled as kotlinShuffled
import kotlin.collections.single as kotlinSingle
import kotlin.collections.singleOrNull as kotlinSingleOrNull

/**
 * Shortcut for `!isEmpty()`.
 */
fun CollectionView<*>.isNotEmpty() = !isEmpty()

/**
 * Returns a range for `0` to [size][CollectionView.size]` - 1`.
 */
val CollectionView<*>.indices: IntRange
	get() = 0..<size()

/**
 * Returns the index of the last element, or `-1` if this list is empty.
 */
val ListView<*>.lastIndex: Int
	get() = size() - 1

/**
 * Returns the first element of this list.
 *
 * Throws [IndexOutOfBoundsException] if this list has no elements.
 */
operator fun <T> ListView<T>.component1(): T = get(0)

/**
 * Returns the second element of this list.
 * Throws [IndexOutOfBoundsException] if this list has less than two elements.
 */
operator fun <T> ListView<T>.component2(): T = get(1)

/**
 * Returns the third element of this list.
 * Throws [IndexOutOfBoundsException] if this list has less than three elements.
 */
operator fun <T> ListView<T>.component3(): T = get(2)

/**
 * Returns the fourth element of this list.
 * Throws [IndexOutOfBoundsException] if this list has less than four elements.
 */
operator fun <T> ListView<T>.component4(): T = get(3)

/**
 * Returns the fifth element of this list.
 * Throws [IndexOutOfBoundsException] if this list has less than five elements.
 */
operator fun <T> ListView<T>.component5(): T = get(4)

/**
 * Shortcut for [MutableCollection.addAll].
 */
fun <T> MutableCollection<T>.addAll(elements: Iterable<T>): Boolean = when(elements) {
	is CollectionView<T> -> addAll(elements)
	is kotlin.collections.Collection<T> -> addAll(elements.asViewConnection())
	else -> {
		var result = false
		for(e in elements) if(add(e)) result = true
		result // ^when
	}
}

/**
 * Shortcut for [MutableCollection.addAll].
 */
fun <T> MutableCollection<T>.addAll(elements: Array<out T>): Boolean = addAll(elements.asList())

/**
 * Shortcut for [MutableCollection.addAll].
 */
fun <T> MutableCollection<T>.addAll(elements: Sequence<T>): Boolean {
	var result = false
	for(e in elements) if(add(e)) result = true
	return result
}

/**
 * Shortcut for [RemoveOnlyCollection.removeAll].
 */
fun <T> RemoveOnlyCollection<T>.removeAll(elements: Iterable<T>): Boolean = when(elements) {
	is CollectionView<T> -> removeAll(elements)
	is kotlin.collections.Collection<T> -> removeAll(elements.asViewConnection())
	else -> removeAll(elements.toSet())
}

/**
 * Shortcut for [RemoveOnlyCollection.removeAll].
 */
fun <T> RemoveOnlyCollection<T>.removeAll(elements: Array<out T>) {
	removeAll(elements.toSet())
}

/**
 * Shortcut for [RemoveOnlyCollection.removeAll].
 */
fun <T> RemoveOnlyCollection<T>.removeAll(elements: Sequence<T>) {
	removeAll(elements.toSet())
}

/**
 * Shortcut for [RemoveOnlyCollection.retainAll].
 */
fun <T> RemoveOnlyCollection<T>.retainAll(elements: Iterable<T>) {
	retainAll(elements.toSet())
}

/**
 * Shortcut for [RemoveOnlyCollection.retainAll].
 */
fun <T> RemoveOnlyCollection<T>.retainAll(elements: Array<out T>) {
	retainAll(elements.toSet())
}

/**
 * Shortcut for [RemoveOnlyCollection.retainAll].
 */
fun <T> RemoveOnlyCollection<T>.retainAll(elements: Sequence<T>) {
	retainAll(elements.toSet())
}

/**
 * Shortcut for [MutableCollection.add].
 */
operator fun <T> MutableCollection<T>.plusAssign(element: T) {
	add(element)
}

/**
 * Shortcut for [addAll].
 */
operator fun <T> MutableCollection<T>.plusAssign(elements: Iterable<T>) {
	addAll(elements)
}

/**
 * Shortcut for [addAll].
 */
operator fun <T> MutableCollection<T>.plusAssign(elements: Array<out T>) {
	addAll(elements)
}

/**
 * Shortcut for [addAll].
 */
operator fun <T> MutableCollection<T>.plusAssign(elements: Sequence<T>) {
	addAll(elements)
}

/**
 * Shortcut for [RemoveOnlyCollection.remove].
 */
operator fun <T> RemoveOnlyCollection<T>.minusAssign(element: T) {
	remove(element)
}

/**
 * Shortcut for [removeAll].
 */
operator fun <T> RemoveOnlyCollection<T>.minusAssign(elements: Iterable<T>) {
	removeAll(elements)
}

/**
 * Shortcut for [removeAll].
 */
operator fun <T> RemoveOnlyCollection<T>.minusAssign(elements: Array<out T>) = removeAll(elements)

/**
 * Shortcut for [removeAll].
 */
operator fun <T> RemoveOnlyCollection<T>.minusAssign(elements: Sequence<T>) = removeAll(elements)

/**
 * Returns a list that contains the elements by their encounter order and then the given [element].
 */
operator fun <T> Iterable<T>.plus(element: T): List<T> = buildList {
	addAll(this@plus)
	add(element)
}

/**
 * Returns a list that contains the elements by their encounter order and then the given [elements] by their encounter order.
 */
operator fun <T> Iterable<T>.plus(elements: Iterable<T>): List<T> = buildList {
	addAll(this@plus)
	addAll(elements)
}

/**
 * Returns a list that contains the elements by their encounter order and then the given [elements] by their encounter order.
 */
operator fun <T> Iterable<T>.plus(elements: Array<out T>): List<T> = buildList {
	addAll(this@plus)
	addAll(elements)
}

/**
 * Returns a list that contains the elements by their encounter order and then the given [elements] by their encounter order.
 */
operator fun <T> Iterable<T>.plus(elements: Sequence<T>): List<T> = buildList {
	addAll(this@plus)
	addAll(elements)
}

/**
 * Returns a list that contains the elements by their encounter order, but without the first occurrence of the given [element].
 *
 * Whether an element matches the given [element] is determined via [Any.equals].
 */
operator fun <T> Iterable<T>.minus(element: T): List<T> = buildList {
	addAll(this@minus)
	remove(element)
}

/**
 * Returns a list that contains the elements by their encounter order, but without the elements that are also contained in the given [elements].
 *
 * Whether an element matches another element is determined via [Any.equals].
 */
operator fun <T> Iterable<T>.minus(elements: Iterable<T>): List<T> = buildList {
	addAll(this@minus)
	removeAll(elements)
}

/**
 * Returns a list that contains the elements by their encounter order, but without the elements that are also contained in the given [elements].
 *
 * Whether an element matches another element is determined via [Any.equals].
 */
operator fun <T> Iterable<T>.minus(elements: Array<out T>): List<T> = buildList {
	addAll(this@minus)
	removeAll(elements)
}

/**
 * Returns a list that contains the elements by their encounter order, but without the elements that are also contained in the given [elements].
 *
 * Whether an element matches another element is determined via [Any.equals].
 */
operator fun <T> Iterable<T>.minus(elements: Sequence<T>): List<T> = buildList {
	addAll(this@minus)
	removeAll(elements.toSet())
}

/**
 * Returns a new list that repeats the contents of this list [n] times in their order.
 */
operator fun <T> ListView<T>.times(n: Int): List<T> = buildList(size() * n) {
	repeat(n) { addAll(this@times) }
}

/**
 * Returns the first element by their encounter order.
 * Throws [NoSuchElementException] if this collection is empty.
 */
fun <T> Iterable<T>.first(): T = when(this) {
	is SequencedCollectionView<T> -> first()
	is kotlin.collections.List<T> -> kotlinFirst()
	else -> run {
		val iterator = iterator()
		return@run if(iterator.hasNext()) iterator.next() else throw NoSuchElementException("Collection is empty")
	}
}

/**
 * Returns the last element by their encounter order.
 * Throws [NoSuchElementException] if this collection is empty.
 */
fun <T> Iterable<T>.last(): T = when(this) {
	is SequencedCollectionView<T> -> last()
	is kotlin.collections.List<T> -> kotlinLast()
	else -> run {
		val iterator = iterator()
		if(!iterator.hasNext()) throw NoSuchElementException("Collection is empty")
		var last = iterator.next()
		while(iterator.hasNext()) last = iterator.next()
		return@run last
	}
}

/**
 * Returns the first element of this collection, or `null` if this collection is empty.
 */
fun <T> Iterable<T>.firstOrNull(): T? = when(this) {
	is SequencedCollectionView<T> -> firstOrNull()
	is kotlin.collections.List<T> -> kotlinFirstOrNull()
	else -> run {
		val iterator = iterator()
		return@run if(iterator.hasNext()) iterator.next() else null
	}
}

/**
 * Returns the last element of this collection, or `null` if this collection is empty.
 */
fun <T> Iterable<T>.lastOrNull(): T? = when(this) {
	is SequencedCollectionView<T> -> lastOrNull()
	is kotlin.collections.List<T> -> kotlinLastOrNull()
	else -> run {
		var last: T? = null
		for(e in this) last = e
		return last
	}
}

/**
 * Returns the first element of this collection, or `null` if this collection is empty.
 */
fun <T> SequencedCollectionView<T>.firstOrNull(): T? = if(isEmpty()) null else first()

/**
 * Returns the last element of this collection, or `null` if this collection is empty.
 */
fun <T> SequencedCollectionView<T>.lastOrNull(): T? = if(isEmpty()) null else last()

/**
 * Returns the last element that matches the [predicate], or `null` if no matching elements were found.
 */
inline fun <T> SequencedCollectionView<T>.lastOrNull(predicate: (T) -> Boolean): T? = reversed().kotlinFirstOrNull(predicate)

/**
 * Returns the last element that matches the [predicate], or `null` if no matching elements were found.
 *
 * This method is equivalent with [lastOrNull].
 */
inline fun <T> SequencedCollectionView<T>.findLast(predicate: (T) -> Boolean): T? = lastOrNull(predicate)

/**
 * Returns the only element of this collection.
 * Throws [NoSuchElementException] if this collection is empty,
 * or [IllegalArgumentException] if this collection has more than one element.
 */
fun <T> Iterable<T>.single(): T = when(this) {
	is SequencedCollectionView<T> -> single()
	is kotlin.collections.List<T> -> kotlinSingle()
	else -> run {
		val iterator = iterator()
		if(!iterator.hasNext()) throw NoSuchElementException("Collection is empty")
		val single = iterator.next()
		if(iterator.hasNext()) throw IllegalArgumentException("Collection has more than one element")
		return@run single
	}
}

/**
 * Returns the only element of this collection.
 * Throws [NoSuchElementException] if this collection is empty,
 * or [IllegalArgumentException] if this collection has more than one element.
 */
fun <T> SequencedCollectionView<T>.single(): T = when(size()) {
	0 -> throw NoSuchElementException("Collection is empty")
	1 -> first()
	else -> throw IllegalArgumentException("Collection has more than one element")
}

/**
 * Returns the only element of this collection, or `null` if this collection is empty or has more than one element.
 */
fun <T> Iterable<T>.singleOrNull(): T? = when(this) {
	is SequencedCollectionView<T> -> singleOrNull()
	is kotlin.collections.List<T> -> kotlinSingleOrNull()
	else -> run {
		val iterator = iterator()
		if(!iterator.hasNext()) return@run null
		val single = iterator.next()
		if(iterator.hasNext()) return@run null
		return@run single
	}
}

/**
 * Returns the only element of this collection, or `null` if this collection is empty or has more than one element.
 */
fun <T> SequencedCollectionView<T>.singleOrNull(): T? = if(size() == 1) first() else null

/**
 * Returns a random element from the collection, or throws [NoSuchElementException] if the collection is empty.
 */
fun <T> CollectionView<T>.random(random: Random = Random.Default): T =
	if(isEmpty()) throw NoSuchElementException("Collection is empty") else elementAt(random.nextInt(size()))

/**
 * Returns a random element from the collection, or `null` if the collection is empty.
 */
fun <T> CollectionView<T>.randomOrNull(random: Random = Random.Default): T? =
	if(isEmpty()) null else elementAt(random.nextInt(size()))

/**
 * Performs a stable sort on the elements in this list in-place.
 *
 * The [natural ordering][naturalOrder] is used.
 */
@Suppress("UNCHECKED_CAST")
fun <T: Comparable<T>> MutableList<T>.sort() {
	val array = toTypedArray<Comparable<T>>()
	array.sort()
	for(i in array.indices) set(i, array[i] as T)
}

/**
 * Performs a stable reversed sort on the elements in this list in-place.
 *
 * The [reverse ordering][reverseOrder] is used.
 */
fun <T: Comparable<T>> MutableList<T>.sortDescending() = sort(reverseOrder())

/**
 * Performs a stable sort on the elements in this list in-place.
 *
 * The given [comparator] is used.
 */
@Suppress("UNCHECKED_CAST")
fun <T> MutableList<T>.sort(comparator: Comparator<in T>) {
	val array = toTypedArray<Any?>() as Array<T>
	array.sortWith(comparator)
	for(i in array.indices) set(i, array[i])
}

/**
 * Returns a stable sorted list with the elements in this list.
 *
 * The [natural ordering][naturalOrder] is used.
 */
fun <T: Comparable<T>> Iterable<T>.sorted(): List<T> = buildList {
	addAll(this@sorted)
	sort()
}

/**
 * Returns a stable reverse sorted list from the elements in this list.
 *
 * The [reverse ordering][reverseOrder] is used.
 */
fun <T: Comparable<T>> Iterable<T>.sortedDescending(): List<T> = buildList {
	addAll(this@sortedDescending)
	sortDescending()
}

/**
 * Returns a stable sorted list from the elements in this list.
 *
 * The given [comparator] is used.
 */
fun <T> Iterable<T>.sorted(comparator: Comparator<in T>): List<T> = buildList {
	addAll(this@sorted)
	sort(comparator)
}

/**
 * Shuffles the list in-place, using the given [random] generator.
 *
 * This implementation uses the [Fisher-Yates shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm).
 */
fun <T> MutableList<T>.shuffle(random: Random = Random.Default) {
	for(i in this.lastIndex downTo 1) {
		val j = random.nextInt(i + 1)
		this[i] = set(j, this[i])
	}
}

/**
 * Returns a list that shuffles the elements from their encounter order, using the given [random] generator.
 *
 * This implementation uses the [Fisher-Yates shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm).
 */
fun <T> Iterable<T>.shuffled(random: Random = Random.Default): List<T> = buildList {
	addAll(this@shuffled)
	shuffle(random)
}

/**
 * Replaces all elements in this list with the [transform] of each element.
 */
inline fun <T> MutableList<T>.replaceAll(transform: (T) -> T) {
	val iterator = iterator()
	for(e in iterator) iterator.set(transform(e))
}

/**
 * Shortcut for [MutableMap.putAll].
 */
fun <K, V> MutableMap<K, V>.putAll(entries: Iterable<Pair<K, V>>) {
	for((key, value) in entries) put(key, value)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
fun <K, V> MutableMap<K, V>.putAll(entries: Array<Pair<K, V>>) = putAll(entries.asList())

/**
 * Shortcut for [MutableMap.putAll].
 */
fun <K, V> MutableMap<K, V>.putAll(entries: Sequence<Pair<K, V>>) {
	for((key, value) in entries) put(key, value)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MutableMap<K, V>.plusAssign(map: MapView<out K, V>) {
	putAll(map)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MutableMap<K, V>.plusAssign(entries: Iterable<Pair<K, V>>) {
	putAll(entries)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MutableMap<K, V>.plusAssign(entries: Array<Pair<K, V>>) {
	putAll(entries)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MutableMap<K, V>.plusAssign(entries: Sequence<Pair<K, V>>) {
	putAll(entries)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MapView<out K, V>.plus(map: MapView<out K, V>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(map)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MapView<out K, V>.plus(entries: Iterable<Pair<K, V>>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(entries)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MapView<out K, V>.plus(entries: Array<Pair<K, V>>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(entries)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MapView<out K, V>.plus(entries: Sequence<Pair<K, V>>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(entries)
}

/**
 * Returns `true` if this map is not empty, `false` otherwise.
 */
fun MapView<*, *>.isNotEmpty(): Boolean = !isEmpty()

/**
 * Shortcut for [MapView.containsKey].
 */
operator fun <K> MapView<K, *>.contains(key: K): Boolean = containsKey(key)

/**
 * Shortcut for `this.entries.iterator`.
 */
operator fun <K, V> MapView<out K, V>.iterator(): Iterator<kotlin.collections.Map.Entry<K, V>> = this.entries.iterator()

/**
 * Returns the iterator for this map's [entries][MutableMap.entries].
 */
operator fun <K, V> MutableMap<K, V>.iterator(): MutableIterator<kotlin.collections.MutableMap.MutableEntry<K, V>> = this.entries.iterator()

/**
 * Performs the given [action] on this map's [entries][MapView.entries].
 */
inline fun <K, V> MapView<out K, V>.forEach(action: (kotlin.collections.Map.Entry<K, V>) -> Unit) {
	for(e in this.entries) action(e)
}

/**
 * Returns the value of the entry associated with the given [key].
 * Throws [NoSuchElementException] if no entries have the [key].
 */
@Suppress("UNCHECKED_CAST")
@JvmName("getNullableValue")
fun <K, V> MapView<K, V>.getValue(key: K): V =
	get(key) ?: if(key in this) null as V else throw NoSuchElementException("Value for key $key is not present")

/**
 * Returns the value of the entry associated with the given [key].
 * Throws [NoSuchElementException] if no entries have the [key].
 */
fun <K, V: Any> MapView<K, V>.getValue(key: K): V = get(key) ?: throw NoSuchElementException("Value for key $key is not present")

/**
 * Returns the value of the entry associated with the given [key],
 * or returns and puts a new entry with the [defaultValue].
 */
@Suppress("UNCHECKED_CAST")
@JvmName("getOrPutNullable")
inline fun <K, V> MutableMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V =
	get(key) ?: if(key in this) null as V else defaultValue().also { put(key, it) }

/**
 * Returns the value of the entry associated with the given [key],
 * or returns and puts a new entry with the [defaultValue].
 */
inline fun <K, V: Any> MutableMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V = get(key) ?: defaultValue().also { put(key, it) }

/**
 * Shortcut for [MutableMap.put].
 */
operator fun <K, V> MutableMap<K, V>.set(key: K, value: V) {
	put(key, value)
}

/**
 * Replaces all values in this map with the [transform] of each entry.
 */
inline fun <K, V> MutableMap<K, V>.replaceAll(transform: (kotlin.collections.Map.Entry<K, V>) -> V) {
	for(e in this) e.setValue(transform(e))
}
