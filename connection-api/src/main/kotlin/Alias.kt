/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.collections.distinct
import kotlin.collections.distinctBy
import kotlin.collections.flatMap
import kotlin.collections.flatMapIndexed
import kotlin.collections.groupBy
import kotlin.collections.map
import kotlin.collections.mapIndexed
import kotlin.collections.mapNotNull
import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.collections.toMutableSet
import kotlin.collections.toSet

/**
 * Alias for [kotlin.collections.Collection].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.Collection"))
typealias KotlinCollection<T> = kotlin.collections.Collection<T>
/**
 * Alias for [kotlin.collections.MutableCollection].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.MutableCollection"))
typealias KotlinMutableCollection<T> = kotlin.collections.MutableCollection<T>

/**
 * Alias for [kotlin.collections.List].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.List"))
typealias KotlinList<T> = kotlin.collections.List<T>
/**
 * Alias for [kotlin.collections.MutableList].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.MutableList"))
typealias KotlinMutableList<T> = kotlin.collections.MutableList<T>

/**
 * Alias for [kotlin.collections.Set].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.Set"))
typealias KotlinSet<T> = kotlin.collections.Set<T>
/**
 * Alias for [kotlin.collections.MutableSet].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.MutableSet"))
typealias KotlinMutableSet<T> = kotlin.collections.MutableSet<T>

/**
 * Alias for [kotlin.collections.Map].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.Map"))
typealias KotlinMap<K, V> = kotlin.collections.Map<K, V>
/**
 * Alias for [kotlin.collections.MutableMap].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.MutableMap"))
typealias KotlinMutableMap<K, V> = kotlin.collections.MutableMap<K, V>

/**
 * Alias for [kotlin.collections.Map.Entry].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.Map.Entry"))
typealias KotlinMapEntry<K, V> = kotlin.collections.Map.Entry<K, V>
/**
 * Alias for [kotlin.collections.MutableMap.MutableEntry].
 */
@Deprecated("Useless alias.", ReplaceWith("kotlin.collections.MutableMap.MutableEntry"))
typealias KotlinMutableMapEntry<K, V> = kotlin.collections.MutableMap.MutableEntry<K, V>

/**
 * Alias for [toList].
 */
@Deprecated("Useless alias.", ReplaceWith("toList()", "kotlin.collections.toList"))
fun <T> Iterable<T>.toKotlinList(): KotlinList<T> = toList()
/**
 * Alias for [toMutableList].
 */
@Deprecated("Useless alias.", ReplaceWith("toMutableList()", "kotlin.collections.toMutableList"))
fun <T> Iterable<T>.toKotlinMutableList(): KotlinMutableList<T> = toMutableList()

/**
 * Alias for [toSet].
 */
@Deprecated("Useless alias.", ReplaceWith("toSet()", "kotlin.collections.toSet"))
fun <T> Iterable<T>.toKotlinSet(): KotlinSet<T> = toSet()
/**
 * Alias for [toMutableSet].
 */
@Deprecated("Useless alias.", ReplaceWith("toMutableSet()", "kotlin.collections.toMutableSet"))
fun <T> Iterable<T>.toKotlinMutableSet(): KotlinMutableSet<T> = toMutableSet()

/**
 * Alias for [toMap].
 */
@Deprecated("Useless alias.", ReplaceWith("toMap()", "kotlin.collections.toMap"))
fun <K, V> Iterable<Pair<K, V>>.toKotlinMap(): KotlinMap<K, V> = toMap()

/**
 * Alias for [kotlin.collections.map].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("map(transform)", "kotlin.collections.map"))
fun <T, R> Iterable<T>.kotlinMap(transform: (T) -> R): KotlinList<R> = map(transform)

/**
 * Alias for [mapIndexed].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("mapIndexed(transform)", "kotlin.collections.mapIndexed"))
fun <T, R> Iterable<T>.kotlinMapIndexed(transform: (index: Int, T) -> R): KotlinList<R> = mapIndexed(transform)

/**
 * Alias for [mapNotNull].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("mapNotNull(transform)", "kotlin.collections.mapNotNull"))
fun <T, R: Any> Iterable<T>.kotlinMapNotNull(transform: (T) -> R?): KotlinList<R> = mapNotNull(transform)

/**
 * Alias for [flatMap].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("flatMap(transform)", "kotlin.collections.flatMap"))
fun <T, R> Iterable<T>.kotlinFlatMap(transform: (T) -> Iterable<R>): KotlinList<R> = flatMap(transform)

/**
 * Alias for [flatMapIndexed].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("flatMapIndexed(transform)", "kotlin.collections.flatMapIndexed"))
fun <T, R> Iterable<T>.kotlinFlatMapIndexed(transform: (index: Int, T) -> Iterable<R>): KotlinList<R> = flatMapIndexed(transform)

/**
 * Alias for [groupBy].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("groupBy(keySelector)", "kotlin.collections.groupBy"))
fun <T, K> Iterable<T>.kotlinGroupBy(keySelector: (T) -> K): KotlinMap<K, KotlinList<T>> = groupBy(keySelector)

/**
 * Alias for [groupBy].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("groupBy(keySelector, valueTransform)", "kotlin.collections.groupBy"))
fun <T, K, V> Iterable<T>.kotlinGroupBy(keySelector: (T) -> K, valueTransform: (T) -> V): KotlinMap<K, KotlinList<V>> = groupBy(keySelector, valueTransform)

/**
 * Alias for [distinct].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("distinct()", "kotlin.collections.distinct"))
fun <T> Iterable<T>.kotlinDistinct(): KotlinList<T> = distinct()

/**
 * Alias for [distinctBy].
 */
@Deprecated("Useless alias, will be removed when operation for Connection is added.", ReplaceWith("distinctBy(selector)", "kotlin.collections.distinctBy"))
fun <T, K> Iterable<T>.kotlinDistinctBy(selector: (T) -> K): KotlinList<T> = distinctBy(selector)
