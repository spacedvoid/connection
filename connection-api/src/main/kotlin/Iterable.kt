/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.experimental.ExperimentalTypeInference
import kotlin.collections.groupBy as kotlinGroupBy

/**
 * Returns the [index]-th element by the encounter order.
 * Throws [IndexOutOfBoundsException] if less than or equal to [index] elements were found.
 */
fun <T> Iterable<T>.elementAt(index: Int): T = when(this) {
	is ListView<T> -> get(index)
	is kotlin.collections.List<T> -> get(index)
	else -> run {
		var current = 0
		for(e in this) if(current++ == index) return@run e
		throw IndexOutOfBoundsException("Requested index was $index but found only $current elements")
	}
}

/**
 * Removes all elements that match the given [predicate].
 * Returns `true` if any elements were removed, `false` otherwise.
 *
 * The [predicate] is applied to the elements by their encounter order.
 */
inline fun <T> MutableIterable<T>.removeAll(predicate: (T) -> Boolean): Boolean {
	val iterator = iterator()
	var result = false
	for(e in iterator) {
		if(predicate(e)) {
			iterator.remove()
			result = true
		}
	}
	return result
}

/**
 * Collects the results of applying the [transform] to each element by their encounter order.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are collected by their corresponding elements' encounter order.
 */
inline fun <T, U> Iterable<T>.map(transform: (T) -> U): List<U> = buildList(sizeOrDefault(10)) {
	for(e in this@map) add(transform(e))
}

/**
 * Collects the results of applying the [transform] to each element, additionally providing the encounter number of each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are collected by their corresponding elements' encounter order.
 */
inline fun <T, U> Iterable<T>.mapIndexed(transform: (index: Int, T) -> U): List<U> = buildList(sizeOrDefault(10)) {
	var index = 0
	for(e in this@mapIndexed) add(transform(index++, e))
}

/**
 * Collects the elements provided through the [accumulator] applied to each element.
 *
 * Alike [sequence], the [accumulator] provides a [sink][MultiMapScope]
 * accessible with [yield][MultiMapScope.yield] and [yieldAll][MultiMapScope.yieldAll].
 * However, the accumulation is not lazy, so the behavior of this operation when an infinite [Sequence] is provided is undefined.
 *
 * This operation preserves the encounter order:
 * the [accumulator] is applied to the elements by their encounter order,
 * and the elements provided to the sink are (unpacked and) collected by their corresponding elements' encounter order.
 *
 * Behavior of the [MultiMapScope] and the resulting list when using the [MultiMapScope] outside the [accumulator] is undefined.
 */
inline fun <T, U> Iterable<T>.mapMulti(accumulator: MultiMapScope<U>.(T) -> Unit): List<U> = buildList {
	val acceptor = object: MultiMapScope<U> {
		override fun yield(element: U) {
			add(element)
		}

		override fun yieldAll(elements: Iterator<U>) {
			for(e in elements) add(e)
		}

		override fun yieldAll(elements: Iterable<U>) {
			addAll(elements)
		}
	}
	for(e in this@mapMulti) acceptor.accumulator(e)
}

/**
 * Class to wrap sinks of [mapMulti].
 *
 * The usage is similar to [SequenceScope]; use [yield] and [yieldAll] to provide elements to the sink.
 * However, the accumulation is not lazy, so the behavior of this operation when an infinite sequence is provided is undefined.
 */
interface MultiMapScope<T> {
	/**
	 * Provides the given [element] to the sink.
	 */
	fun yield(element: T)

	/**
	 * Provides the given [elements] to the sink, by their encounter order.
	 */
	fun yieldAll(elements: Iterator<T>)

	/**
	 * Provides the given [elements] to the sink, by their encounter order.
	 */
	fun yieldAll(elements: Iterable<T>) = yieldAll(elements.iterator())

	/**
	 * Provides the given [elements] to the sink, by their encounter order.
	 *
	 * The behavior of this operation when the given sequence is infinite is undefined.
	 */
	fun yieldAll(elements: Sequence<T>) = yieldAll(elements.iterator())
}

/**
 * Unpacks and collects the results of applying the [transform] to each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are unpacked and collected by their corresponding elements' encounter order.
 * The encounter order of the elements from each [Iterable] will also be preserved.
 */
inline fun <T, U> Iterable<T>.flatMap(transform: (T) -> Iterable<U>): List<U> = buildList {
	for(e in this@flatMap) addAll(transform(e))
}

/**
 * Unpacks and collects the results of applying the [transform] to each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are unpacked and collected by their corresponding elements' encounter order.
 * The encounter order of the elements from each [Sequence] will also be preserved.
 *
 * The behavior of this operation when an infinite sequence is provided is undefined.
 */
@JvmName("flatMapSequence")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T, U> Iterable<T>.flatMap(transform: (T) -> Sequence<U>): List<U> = buildList {
	for(e in this@flatMap) addAll(transform(e))
}

/**
 * Unpacks and collects the results of applying the [transform] to each element,
 * additionally providing the encounter number of each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are unpacked and collected by their corresponding elements' encounter order.
 * The encounter order of the elements from each [Iterable] will also be preserved.
 */
inline fun <T, U> Iterable<T>.flatMapIndexed(transform: (index: Int, T) -> Iterable<U>): List<U> = buildList {
	var index = 0
	for(e in this@flatMapIndexed) addAll(transform(index++, e))
}

/**
 * Unpacks and collects the results of applying the [transform] to each element,
 * additionally providing the encounter number of each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are unpacked and collected by their corresponding elements' encounter order.
 * The encounter order of the elements from each [Sequence] will also be preserved.
 *
 * The behavior of this operation when an infinite sequence is provided is undefined.
 */
@JvmName("flatMapIndexedSequence")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T, U> Iterable<T>.flatMapIndexed(transform: (index: Int, T) -> Sequence<U>): List<U> = buildList {
	var index = 0
	for(e in this@flatMapIndexed) addAll(transform(index++, e))
}

/**
 * Collects the results, except for `null`, of applying the [transform] to each element.
 *
 * This operation preserves the encounter order:
 * the [transform] is applied to the elements by their encounter order,
 * and the mapped elements are collected by their corresponding elements' encounter order.
 */
inline fun <T, U: Any> Iterable<T>.mapNotNull(transform: (T) -> U?): List<U> = buildList {
	for(e in this@mapNotNull) transform(e)?.let { add(it) }
}

/**
 * Collects only the elements that match the given [predicate].
 *
 * This operation preserves the encounter order:
 * the [predicate] is applied to the elements by their encounter order,
 * and the remaining elements after the filter are collected by their encounter order.
 */
inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> = buildList {
	for(e in this@filter) if(predicate(e)) add(e)
}

/**
 * Collects only the elements that match the given [predicate], additionally providing the encounter number of each element.
 *
 * This operation preserves the encounter order:
 * the [predicate] is applied to the elements by their encounter order,
 * and the remaining elements after the filter are collected by their encounter order.
 */
inline fun <T> Iterable<T>.filterIndexed(predicate: (index: Int, T) -> Boolean): List<T> = buildList {
	var index = 0
	for(e in this@filterIndexed) if(predicate(index++, e)) add(e)
}

/**
 * Collects only the elements that are instances of [U].
 *
 * This operation preserves the encounter order.
 */
inline fun <reified U> Iterable<*>.filterIsInstance(): List<U> = buildList {
	for(e in this@filterIsInstance) if(e is U) add(e)
}

/**
 * Groups the elements based on the extracted key from applying the [keySelector] to each element.
 *
 * This operation preserves the encounter order:
 * the [keySelector] is applied to the elements by their encounter order,
 * and each [List] will preserve the encounter order.
 */
inline fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
	val grouped = kotlinGroupBy(keySelector)
	return buildMap(grouped.size) {
		for((key, list) in grouped) put(key, list.asConnection())
	}
}

/**
 * Collects the elements after filtering out any [equal][Any.equals] elements.
 *
 * For each equal element, only the first encountered instance will be collected into the list,
 * and the elements' encounter order will be preserved.
 */
fun <T> Iterable<T>.distinct(): List<T> = toSequencedSet().toList()

/**
 * Collects the elements after filtering out any [equal][Any.equals] elements,
 * based on the extracted key from applying the [keySelector] to each element.
 *
 * For each element with equal keys, only the first encountered instance will be collected into the resulting list,
 * and the elements' encounter order will be preserved.
 */
inline fun <T, K> Iterable<T>.distinctBy(keySelector: (T) -> K): List<T> = buildList {
	val set = mutableSetOf<K>()
	for(e in this@distinctBy) if(set.add(keySelector(e))) add(e)
}

/**
 * Collects the entries to a map from applying the [transform] to each element.
 *
 * This operation preserves the encounter order:
 * the [transform] will be applied to the elements by their encounter order,
 * and if multiple entries have the same key, the last entry by the encounter order will be used.
 */
inline fun <T, K, V> Iterable<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> = buildMap(sizeOrDefault(12)) {
	for(e in this@associate) transform(e).let { put(it.first, it.second) }
}

/**
 * Collects the entries to a map where keys are the extracted keys from applying the [keySelector] to each element,
 * and values are the element themselves.
 *
 * This operation preserves the encounter order:
 * the [keySelector] will be applied to the elements by their encounter order,
 * and if multiple entries have the same key, the last entry by the encounter order will be used.
 */
inline fun <T, K> Iterable<T>.associateBy(keySelector: (T) -> K): Map<K, T> = buildMap(sizeOrDefault(12)) {
	for(e in this@associateBy) put(keySelector(e), e)
}

/**
 * Collects the entries to a map where keys are the element themselves,
 * and values are the extracted values from applying the [valueSelector] to each element.
 *
 * This operation preserves the encounter order:
 * the [valueSelector] will be applied to the elements by their encounter order,
 * and if multiple entries have the same key, the last entry by the encounter order will be used.
 */
inline fun <T, V> Iterable<T>.associateWith(valueSelector: (T) -> V): Map<T, V> = buildMap(sizeOrDefault(12)) {
	for(e in this@associateWith) put(e, valueSelector(e))
}

@PublishedApi
internal fun <T> Iterable<T>.sizeOrDefault(default: Int): Int = when(this) {
	is CollectionView<T> -> size()
	is kotlin.collections.Collection<T> -> this.size
	else -> default
}
