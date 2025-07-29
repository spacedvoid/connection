/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(ExperimentalContracts::class)

package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.ListImpl
import io.github.spacedvoid.connection.impl.MapImpl
import io.github.spacedvoid.connection.impl.MutableListImpl
import io.github.spacedvoid.connection.impl.MutableMapImpl
import io.github.spacedvoid.connection.impl.MutableSequencedMapImpl
import io.github.spacedvoid.connection.impl.MutableSequencedSetImpl
import io.github.spacedvoid.connection.impl.MutableSetImpl
import io.github.spacedvoid.connection.impl.SequencedMapImpl
import io.github.spacedvoid.connection.impl.SequencedSetImpl
import io.github.spacedvoid.connection.impl.SetImpl
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Dynamically builds an immutable list with an expected capacity.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * The supplied [MutableList] instance will be available only from the [filler].
 * Behavior of the list outside the lambda will be undefined.
 */
inline fun <T> buildList(initialCapacity: Int = 10, filler: MutableList<T>.() -> Unit): List<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableListImpl<T>(ArrayList(initialCapacity)).apply(filler)
	return if(result.isNotEmpty()) ListImpl(result.kotlin) else listOf()
}


/**
 * Dynamically builds an immutable set with an expected capacity.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * The supplied [MutableSet] instance will be available only from the [filler].
 * Behavior of the set outside the lambda will be undefined.
 */
inline fun <T> buildSet(initialCapacity: Int = 12, filler: MutableSet<T>.() -> Unit): Set<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableSetImpl<T>(HashSet.newHashSet(initialCapacity)).apply(filler)
	return if(result.isNotEmpty()) SetImpl(result.kotlin) else setOf()
}

/**
 * Dynamically builds an immutable sequenced set with an expected capacity.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * The supplied [MutableSet] instance will be available only from the [filler].
 * Behavior of the set outside the lambda will be undefined.
 */
inline fun <T> buildSequencedSet(initialCapacity: Int = 12, filler: MutableSequencedSet<T>.() -> Unit): SequencedSet<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableSequencedSetImpl<T>(LinkedHashSet(initialCapacity)).apply(filler)
	return if(result.isNotEmpty()) SequencedSetImpl(result.kotlin) else sequencedSetOf()
}

/**
 * Dynamically builds an immutable map with an expected capacity.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * The supplied [MutableMap] instance will be available only from the [filler].
 * Behavior of the map outside the lambda will be undefined.
 */
inline fun <K, V> buildMap(initialCapacity: Int = 12, filler: MutableMap<K, V>.() -> Unit): Map<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableMapImpl<K, V>(HashMap.newHashMap(initialCapacity)).apply(filler)
	return if(result.isNotEmpty()) MapImpl(result.kotlin) else mapOf()
}

/**
 * Dynamically builds an immutable sequenced map with an expected capacity.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * The supplied [MutableMap] instance will be available only from the [filler].
 * Behavior of the map outside the lambda will be undefined.
 */
inline fun <K, V> buildSequencedMap(initialCapacity: Int = 12, filler: MutableMap<K, V>.() -> Unit): SequencedMap<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableSequencedMapImpl<K, V>(LinkedHashMap.newLinkedHashMap(initialCapacity)).apply(filler)
	return if(result.isNotEmpty()) SequencedMapImpl(result.kotlin) else sequencedMapOf()
}
