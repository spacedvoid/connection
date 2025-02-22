/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

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
