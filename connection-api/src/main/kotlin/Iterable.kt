/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.experimental.ExperimentalTypeInference
import kotlin.collections.distinct as kotlinDistinct
import kotlin.collections.distinctBy as kotlinDistinctBy
import kotlin.collections.filter as kotlinFilter
import kotlin.collections.filterIndexed as kotlinFilterIndexed
import kotlin.collections.filterIsInstance as kotlinFilterIsInstance
import kotlin.collections.flatMap as kotlinFlatMap
import kotlin.collections.flatMapIndexed as kotlinFlatMapIndexed
import kotlin.collections.groupBy as kotlinGroupBy
import kotlin.collections.map as kotlinMap
import kotlin.collections.mapIndexed as kotlinMapIndexed
import kotlin.collections.mapNotNull as kotlinMapNotNull

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
			for(e in iterator()) {
				if(current == index) return e
				current++
			}
			throw IndexOutOfBoundsException("Requested index was $index but found only $current elements")
		}
	}
}

/**
 * Removes all elements from this collection that matches the given [predicate].
 * Returns `true` if any elements were removed, `false` otherwise.
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
 * Returns a list that contains the elements after the [transform], in their encounter order.
 */
inline fun <T, U> Iterable<T>.map(transform: (T) -> U): List<U> = kotlinMap(transform).asConnection()

/**
 * Returns a list that contains the elements after the [transform] in their encounter order,
 * additionally providing an [Int] representing the encounter number of the element.
 */
inline fun <T, U> Iterable<T>.mapIndexed(transform: (index: Int, T) -> U): List<U> = kotlinMapIndexed(transform).asConnection()

/**
 * Returns a list that contains the elements provided through the [accumulator].
 *
 * The [accumulator] provides a sink, accessible with [MultiMapScope.yield] and [MultiMapScope.yieldAll].
 * The list will contain the elements in the invocation order of the sink.
 *
 * Note that unlike [SequenceScope], the accumulation is not lazy.
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
 * However, note that the accumulation is not lazy.
 */
interface MultiMapScope<T> {
	/**
	 * Provides a single element to the sink.
	 */
	fun yield(element: T)

	/**
	 * Provides multiple elements to the sink, in their encounter order.
	 */
	fun yieldAll(elements: Iterator<T>)

	/**
	 * Provides multiple elements to the sink, in their encounter order.
	 */
	fun yieldAll(elements: Iterable<T>) = yieldAll(elements.iterator())

	/**
	 * Provides multiple elements to the sink, in their encounter order.
	 */
	fun yieldAll(elements: Sequence<T>) = yieldAll(elements.iterator())
}

/**
 * Returns a list that contains the unpacked elements after the [transform], in their encounter order.
 *
 * The resulting [Iterable] will be collected into the resulting list in their encounter order.
 */
inline fun <T, U> Iterable<T>.flatMap(transform: (T) -> Iterable<U>): List<U> = kotlinFlatMap(transform).asConnection()

/**
 * Returns a list that contains the unpacked elements after the [transform], in their encounter order.
 *
 * The resulting [Sequence] will be collected into the resulting list in their encounter order.
 */
@JvmName("flatMapSequence")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T, U> Iterable<T>.flatMap(transform: (T) -> Sequence<U>): List<U> = kotlinFlatMap(transform).asConnection()

/**
 * Returns a list that contains the unpacked elements after the [transform] in their encounter order,
 * additionally providing an [Int] representing the encounter number of the element.
 *
 * The resulting [Iterable] will be collected into the resulting list in their encounter order.
 */
inline fun <T, U> Iterable<T>.flatMapIndexed(transform: (index: Int, T) -> Iterable<U>): List<U> = kotlinFlatMapIndexed(transform).asConnection()

/**
 * Returns a list that contains the unpacked elements after the [transform] in their encounter order,
 * additionally providing an [Int] representing the encounter number of the element.
 *
 * The resulting [Sequence] will be collected into the resulting list in their encounter order.
 */
@JvmName("flatMapIndexedSequence")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T, U> Iterable<T>.flatMapIndexed(transform: (index: Int, T) -> Sequence<U>): List<U> = kotlinFlatMapIndexed(transform).asConnection()

/**
 * Returns a list that contains the elements after the [transform] except for `null`, in their encounter order.
 */
inline fun <T, R: Any> Iterable<T>.mapNotNull(transform: (T) -> R?): List<R> = kotlinMapNotNull(transform).asConnection()

/**
 * Returns a list that contains only the elements that match the given [predicate], in their encounter order.
 */
inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> = kotlinFilter(predicate).asConnection()

/**
 * Returns a list that contains only the elements that match the given [predicate] in their encounter order,
 * additionally providing an [Int] representing the encounter number of the element.
 */
inline fun <T> Iterable<T>.filterIndexed(predicate: (index: Int, T) -> Boolean): List<T> = kotlinFilterIndexed(predicate).asConnection()

/**
 * Returns a list that contains only the elements that are instances of [R], in their encounter order.
 */
inline fun <reified R> Iterable<*>.filterIsInstance(): List<R> = kotlinFilterIsInstance<R>().asConnection()

/**
 * Groups the elements based on the extracted key from each element.
 *
 * The lists will contain the elements in their encounter order.
 */
inline fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
	val grouped = kotlinGroupBy(keySelector)
	return buildMap(grouped.size) {
		for((key, list) in grouped) put(key, list.asConnection())
	}
}

/**
 * Filters out any [equal][Any.equals] elements.
 *
 * The result will be computed as if this was a fold operation, based on a sequenced set;
 * for each equal element, only the first encountered instance will be collected into the resulting list,
 * and elements will be collected by their encounter order.
 */
fun <T> Iterable<T>.distinct(): List<T> = kotlinDistinct().asConnection()

/**
 * Filters out any [equal][Any.equals] elements, based on the extracted key.
 *
 * The result will be computed as if this was a fold operation, based on a sequenced set;
 * for each element with equal keys, only the first encountered instance will be collected into the resulting list,
 * and elements will be collected by their encounter order.
 */
inline fun <T, K> Iterable<T>.distinctBy(keySelector: (T) -> K): List<T> = kotlinDistinctBy(keySelector).asConnection()

/**
 * Returns a map that collects the entries after the [transform] of each element.
 */
inline fun <T, K, V> Iterable<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> = buildMap {
	for(e in this@associate) transform(e).let { put(it.first, it.second) }
}

/**
 * Returns a map that collects the entries by associating the [transform] of each element as the key and the element as the value.
 */
inline fun <T, K> Iterable<T>.associateBy(transform: (T) -> K): Map<K, T> = buildMap {
	for(e in this@associateBy) put(transform(e), e)
}

/**
 * Returns a map that collects the entries by associating the element as the key and the [transform] of each element as the value.
 */
inline fun <T, V> Iterable<T>.associateWith(transform: (T) -> V): Map<T, V> = buildMap {
	for(e in this@associateWith) put(e, transform(e))
}
