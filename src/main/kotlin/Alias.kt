/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

typealias KotlinCollection<T> = kotlin.collections.Collection<T>
typealias KotlinMutableCollection<T> = kotlin.collections.MutableCollection<T>

typealias KotlinList<T> = kotlin.collections.List<T>
typealias KotlinMutableList<T> = kotlin.collections.MutableList<T>

typealias KotlinSet<T> = kotlin.collections.Set<T>
typealias KotlinMutableSet<T> = kotlin.collections.MutableSet<T>

typealias KotlinMap<K, V> = kotlin.collections.Map<K, V>
typealias KotlinMutableMap<K, V> = kotlin.collections.MutableMap<K, V>

typealias KotlinMapEntry<K, V> = kotlin.collections.Map.Entry<K, V>
typealias KotlinMutableMapEntry<K, V> = kotlin.collections.MutableMap.MutableEntry<K, V>

/**
 * Alias for [kotlin.collections.toList].
 */
fun <T> Iterable<T>.toKotlinList(): KotlinList<T> = toList()
/**
 * Alias for [kotlin.collections.toMutableList].
 */
fun <T> Iterable<T>.toKotlinMutableList(): KotlinMutableList<T> = toMutableList()

/**
 * Alias for [kotlin.collections.toSet].
 */
fun <T> Iterable<T>.toKotlinSet(): KotlinSet<T> = toSet()
/**
 * Alias for [kotlin.collections.toMutableSet].
 */
fun <T> Iterable<T>.toKotlinMutableSet(): KotlinMutableSet<T> = toMutableSet()

/**
 * Alias for [kotlin.collections.toMap].
 */
fun <K, V> Iterable<Pair<K, V>>.toKotlinMap(): KotlinMap<K, V> = toMap()
