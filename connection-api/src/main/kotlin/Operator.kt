package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.MutableListImpl
import kotlin.random.Random
import kotlin.collections.shuffle as kotlinShuffle
import kotlin.collections.shuffled as kotlinShuffled

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
fun <T> MutableCollection<T>.addAll(elements: Iterable<T>): Boolean {
	return when(elements) {
		is CollectionView<T> -> addAll(elements)
		is kotlin.collections.Collection<T> -> addAll(elements.asViewConnection())
		else -> {
			var result = false
			for(e in elements) if(add(e)) result = true
			return result
		}
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
 * Shortcut for [plus].
 *
 * Useful when the [element] is a container, such as [Iterable], [Array], or [Sequence].
 */
fun <T> Iterable<T>.plusElement(element: T): List<T> = plus(element)

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
 * Returns the [index]-th element based on the encounter order.
 * Throws [IndexOutOfBoundsException] if less than [index] elements were found.
 */
fun <T> Iterable<T>.elementAt(index: Int): T {
	return when(this) {
		is ListView<T> -> get(index)
		is kotlin.collections.List<T> -> get(index)
		else -> {
			var current = 0
			val iterator = iterator()
			while(iterator.hasNext()) {
				if(current == index) return iterator.next()
				current++
			}
			throw IndexOutOfBoundsException("Requested index was $index but found only $current elements")
		}
	}
}

/**
 * Returns the last element of this list, or `null` if this list is empty.
 */
fun <T> ListView<T>.lastOrNull(): T? = if(isEmpty()) null else last()

/**
 * Returns the last element that matches the [predicate], or `null` if no matching elements were found.
 */
inline fun <T> ListView<T>.lastOrNull(predicate: (T) -> Boolean): T? {
	val iterator = iterator(size())
	while(iterator.hasPrevious()) iterator.previous().let { if(predicate(it)) return it }
	return null
}

/**
 * Returns the last element that matches the [predicate], or throws a [NoSuchElementException].
 */
inline fun <T> ListView<T>.last(predicate: (T) -> Boolean): T {
	val iterator = iterator(size())
	while(iterator.hasPrevious()) iterator.previous().let { if(predicate(it)) return it }
	throw NoSuchElementException("No elements match the predicate")
}

/**
 * Returns a random element from the collection, or throws [NoSuchElementException] if the collection is empty.
 */
fun <T> CollectionView<T>.random(random: Random = Random.Default): T =
	if(isEmpty()) throw NoSuchElementException("Collection is empty") else elementAt(random.nextInt(this.size()))

/**
 * Returns a random element from the collection, or `null` if the collection is empty.
 */
fun <T> CollectionView<T>.randomOrNull(random: Random = Random.Default): T? =
	if(isEmpty()) null else elementAt(random.nextInt(this.size()))

/**
 * Performs a stable sort on the elements in this list in-place.
 *
 * The [natural ordering][naturalOrder] is used.
 */
fun <T: Comparable<T>> MutableList<T>.sort() = if(this is MutableListImpl<T>) this.kotlin.sort() else asKotlin().sort()

/**
 * Performs a stable reversed sort on the elements in this list in-place.
 *
 * The [reverse ordering][reverseOrder] is used.
 */
fun <T: Comparable<T>> MutableList<T>.sortDescending() = if(this is MutableListImpl<T>) this.kotlin.sortDescending() else asKotlin().sortDescending()

/**
 * Performs a stable sort on the elements in this list in-place.
 *
 * The given [comparator] is used.
 */
fun <T> MutableList<T>.sort(comparator: Comparator<in T>) = if(this is MutableListImpl<T>) this.kotlin.sortWith(comparator) else asKotlin().sortWith(comparator)

/**
 * Returns a stable sorted list with the elements in this list.
 *
 * The [natural ordering][naturalOrder] is used.
 */
fun <T: Comparable<T>> CollectionView<T>.sorted(): List<T> = buildList(size()) {
	addAll(this@sorted)
	sort()
}

/**
 * Returns a stable reverse sorted list from the elements in this list.
 *
 * The [reverse ordering][reverseOrder] is used.
 */
fun <T: Comparable<T>> CollectionView<T>.sortedDescending(): List<T> = buildList(size()) {
	addAll(this@sortedDescending)
	sortDescending()
}

/**
 * Returns a stable sorted list from the elements in this list.
 *
 * The given [comparator] is used.
 */
fun <T> CollectionView<T>.sorted(comparator: Comparator<in T>): List<T> = buildList(size()) {
	addAll(this@sorted)
	sort(comparator)
}

/**
 * Shuffles the list in-place, using the given [random] generator.
 */
fun MutableList<*>.shuffle(random: Random = Random.Default) = if(this is MutableListImpl<*>) this.kotlin.kotlinShuffle(random) else asKotlin().kotlinShuffle(random)

/**
 * Returns a list that shuffles the elements from their encounter order, using the given [random] generator.
 */
fun <T> Iterable<T>.shuffled(random: Random = Random.Default): List<T> = kotlinShuffled(random).asConnection()

/**
 * Shortcut for [MutableMap.put].
 */
operator fun <K, V> MutableMap<K, V>.set(key: K, value: V) {
	put(key, value)
}

/**
 * Shortcut for [MapView.containsKey].
 */
operator fun <K> MapView<K, *>.contains(key: K): Boolean = containsKey(key)

/**
 * Shortcut for `this.entries.iterator`.
 */
operator fun <K, V> Map<K, V>.iterator(): Iterator<kotlin.collections.Map.Entry<K, V>> = this.entries.iterator()
