/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.collections.toMutableSet
import kotlin.collections.toSet

/**
 * Alias for [kotlin.collections.Collection].
 */
typealias KotlinCollection<T> = kotlin.collections.Collection<T>
/**
 * Alias for [kotlin.collections.MutableCollection].
 */
typealias KotlinMutableCollection<T> = kotlin.collections.MutableCollection<T>

/**
 * Alias for [kotlin.collections.List].
 */
typealias KotlinList<T> = kotlin.collections.List<T>
/**
 * Alias for [kotlin.collections.MutableList].
 */
typealias KotlinMutableList<T> = kotlin.collections.MutableList<T>

/**
 * Alias for [kotlin.collections.Set].
 */
typealias KotlinSet<T> = kotlin.collections.Set<T>
/**
 * Alias for [kotlin.collections.MutableSet].
 */
typealias KotlinMutableSet<T> = kotlin.collections.MutableSet<T>

/**
 * Alias for [kotlin.collections.Map].
 */
typealias KotlinMap<K, V> = kotlin.collections.Map<K, V>
/**
 * Alias for [kotlin.collections.MutableMap].
 */
typealias KotlinMutableMap<K, V> = kotlin.collections.MutableMap<K, V>

/**
 * Alias for [kotlin.collections.Map.Entry].
 */
typealias KotlinMapEntry<K, V> = kotlin.collections.Map.Entry<K, V>
/**
 * Alias for [kotlin.collections.MutableMap.MutableEntry].
 */
typealias KotlinMutableMapEntry<K, V> = kotlin.collections.MutableMap.MutableEntry<K, V>

/**
 * Alias for [toList].
 */
fun <T> Iterable<T>.toKotlinList(): KotlinList<T> = toList()
/**
 * Alias for [toMutableList].
 */
fun <T> Iterable<T>.toKotlinMutableList(): KotlinMutableList<T> = toMutableList()

/**
 * Alias for [toSet].
 */
fun <T> Iterable<T>.toKotlinSet(): KotlinSet<T> = toSet()
/**
 * Alias for [toMutableSet].
 */
fun <T> Iterable<T>.toKotlinMutableSet(): KotlinMutableSet<T> = toMutableSet()

/**
 * Alias for [toMap].
 */
fun <K, V> Iterable<Pair<K, V>>.toKotlinMap(): KotlinMap<K, V> = toMap()

/**
 * Alias for [kotlin.collections.map].
 */
fun <T, R> Iterable<T>.kotlinMap(transform: (T) -> R): KotlinList<R> = map(transform)

/**
 * Alias for [mapIndexed].
 */
fun <T, R> Iterable<T>.kotlinMapIndexed(transform: (index: Int, T) -> R): KotlinList<R> = mapIndexed(transform)

/**
 * Alias for [mapNotNull].
 */
fun <T, R> Iterable<T>.kotlinMapNotNull(transform: (T) -> R?): KotlinList<R> = mapNotNull(transform)

/**
 * Alias for [flatMap].
 */
fun <T, R> Iterable<T>.kotlinFlatMap(transform: (T) -> Iterable<R>): KotlinList<R> = flatMap(transform)

/**
 * Alias for [flatMapIndexed].
 */
fun <T, R> Iterable<T>.kotlinFlatMapIndexed(transform: (index: Int, T) -> Iterable<R>): KotlinList<R> = flatMapIndexed(transform)

/**
 * Alias for [groupBy].
 */
fun <T, K> Iterable<T>.kotlinGroupBy(keySelector: (T) -> K): KotlinMap<K, KotlinList<T>> = groupBy(keySelector)

/**
 * Alias for [groupBy].
 */
fun <T, K, V> Iterable<T>.kotlinGroupBy(keySelector: (T) -> K, valueTransform: (T) -> V): KotlinMap<K, KotlinList<V>> = groupBy(keySelector, valueTransform)

/**
 * Alias for [distinct].
 */
fun <T> Iterable<T>.kotlinDistinct(): KotlinList<T> = distinct()

/**
 * Alias for [distinctBy].
 */
fun <T, K> Iterable<T>.kotlinDistinctBy(selector: (T) -> K): KotlinList<T> = distinctBy(selector)
