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
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MutableMap<in K, in V>.plusAssign(map: MapView<K, V>) {
	putAll(map)
}

/**
 * Shortcut for [MutableMap.putAll].
 */
operator fun <K, V> MapView<out K, V>.plus(map: MapView<K, V>): Map<K, V> = buildMap {
	putAll(this@plus)
	putAll(map)
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
 * or returns and puts a new entry with the [defaultValue].
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
 * or returns and puts a new entry with the [defaultValue].
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
