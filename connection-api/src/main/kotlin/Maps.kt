/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(ExperimentalContracts::class)

package io.github.spacedvoid.connection

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Copies all entries from the given [map] to this map.
 *
 * The key of an entry in this map is not replaced when a copied entry has the same key.
 *
 * This method is equivalent with [putAll][MutableMap.putAll].
 */
operator fun <K, V> MutableMap<in K, in V>.plusAssign(map: MapView<K, V>) {
	putAll(map)
}

/**
 * Creates a new map by collecting all entries from this map and the given [map].
 *
 * Values from this map are replaced by the values from the given [map] associated with the same key.
 * Keys from this map are not affected.
 */
operator fun <K, V> MapView<out K, V>.plus(map: MapView<K, V>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(map)
}

/**
 * Returns `true` if this map is not empty, `false` otherwise.
 *
 * A map is not empty if its [size][MapView.size] is not `0`:
 * therefore, this method is equivalent with `size() != 0`.
 */
fun MapView<*, *>.isNotEmpty(): Boolean = !isEmpty()

/**
 * Returns `true` if an entry has the given [key], `false` otherwise.
 */
operator fun <K> MapView<K, *>.contains(key: K): Boolean = containsKey(key)

/**
 * Returns the iterator for this map's [entries][MapView.entries].
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
 *
 * @see getOrElse
 */
@JvmName("getNullableValue")
fun <K, V> MapView<K, V>.getValue(key: K): V = getOrElse(key) { throw NoSuchElementException("Value for key $key is not present") }

/**
 * Returns the value of the entry associated with the given [key].
 * Throws [NoSuchElementException] if no entries have the [key].
 *
 * @see getOrElse
 */
fun <K, V: Any> MapView<K, V>.getValue(key: K): V = getOrElse(key) { throw NoSuchElementException("Value for key $key is not present") }

/**
 * Returns the value of the entry associated with the given [key],
 * or puts a new entry and returns its value using the [defaultValue].
 */
@Suppress("UNCHECKED_CAST")
@JvmName("getOrPutNullable")
inline fun <K, V> MutableMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
	contract {
		callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
	}

	return get(key) ?: if(key in this) null as V else defaultValue().also { put(key, it) }
}

/**
 * Returns the value of the entry associated with the given [key],
 * or puts a new entry and returns its value using the [defaultValue].
 */
inline fun <K, V: Any> MutableMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
	contract {
		callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
	}

	return get(key) ?: defaultValue().also { put(key, it) }
}

/**
 * Returns the value of the entry associated with the given [key], or returns the [defaultValue].
 *
 * This method can be used as an alternative for [getValue] to customize the exception type or its message.
 */
@JvmName("getNullableOrElse")
@Suppress("UNCHECKED_CAST")
inline fun <K, V> MapView<K, V>.getOrElse(key: K, defaultValue: () -> V): V {
	contract {
		callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
	}

	return get(key) ?: if(key in this) null as V else defaultValue()
}

/**
 * Returns the value of the entry associated with the given [key], or returns the [defaultValue].
 *
 * This method can be used as an alternative for [getValue] to customize the exception type or its message.
 */
@Suppress("UNCHECKED_CAST")
inline fun <K, V: Any> MapView<K, V>.getOrElse(key: K, defaultValue: () -> V): V {
	contract {
		callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
	}

	return get(key) ?: defaultValue()
}

/**
 * Associates the given [key] with the [value].
 * Returns `null` if no entries have the [key], or the old value of the entry with the [key].
 *
 * The existing entry's key is not replaced when the entry has the given [key].
 *
 * This method is equivalent with [put][MutableMap.put].
 */
operator fun <K, V> MutableMap<K, V>.set(key: K, value: V) {
	put(key, value)
}

/**
 * Replaces all values in this map with the [transform] of each entry.
 *
 * The [transform] is applied to the entries by their encounter order.
 */
inline fun <K, V> MutableMap<K, V>.replaceAll(transform: (kotlin.collections.Map.Entry<K, V>) -> V) {
	for(e in this) e.setValue(transform(e))
}
