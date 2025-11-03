/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(ExperimentalContracts::class)

package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.LinkedMapImpl
import io.github.spacedvoid.connection.impl.LinkedSetImpl
import io.github.spacedvoid.connection.impl.ListImpl
import io.github.spacedvoid.connection.impl.MapImpl
import io.github.spacedvoid.connection.impl.MutableListImpl
import io.github.spacedvoid.connection.impl.MutableMapImpl
import io.github.spacedvoid.connection.impl.MutableSetImpl
import io.github.spacedvoid.connection.impl.SequencedMapImpl
import io.github.spacedvoid.connection.impl.SequencedSetImpl
import io.github.spacedvoid.connection.impl.SetImpl
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Builds an immutable list as according to the [filler].
 *
 * Behavior of the [MutableList] and the resulting list when using the [MutableList] outside the lambda is undefined.
 */
inline fun <T> buildList(filler: MutableList<T>.() -> Unit): List<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableListImpl<T>(ArrayList()).apply(filler)
	return if(result.isEmpty()) listOf() else ListImpl(result.kotlin)
}

/**
 * Builds an immutable list as according to the [filler], with an expected element count.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * Behavior of the [MutableList] and the resulting list when using the [MutableList] outside the lambda is undefined.
 */
inline fun <T> buildList(initialCapacity: Int, filler: MutableList<T>.() -> Unit): List<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableListImpl<T>(ArrayList(initialCapacity)).apply(filler)
	return if(result.isEmpty()) listOf() else ListImpl(result.kotlin)
}

/**
 * Builds an immutable set as according to the [filler].
 *
 * Behavior of the [MutableSet] and the resulting set when using the [MutableSet] outside the lambda is undefined.
 */
inline fun <T> buildSet(filler: MutableSet<T>.() -> Unit): Set<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableSetImpl<T>(HashSet()).apply(filler)
	return if(result.isEmpty()) setOf() else SetImpl(result.kotlin)
}

/**
 * Builds an immutable set as according to the [filler], with an expected element count.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * Behavior of the [MutableSet] and the resulting set when using the [MutableSet] outside the lambda is undefined.
 */
inline fun <T> buildSet(initialCapacity: Int, filler: MutableSet<T>.() -> Unit): Set<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableSetImpl<T>(HashSet.newHashSet(initialCapacity)).apply(filler)
	return if(result.isEmpty()) setOf() else SetImpl(result.kotlin)
}

/**
 * Builds an immutable sequenced set as according to the [filler].
 *
 * Behavior of the [MutableSequencedSet] and the resulting set when using the [MutableSequencedSet] outside the lambda is undefined.
 */
inline fun <T> buildSequencedSet(filler: LinkedSet<T>.() -> Unit): SequencedSet<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = LinkedSetImpl<T>(LinkedHashSet()).apply(filler)
	return if(result.isEmpty()) sequencedSetOf() else SequencedSetImpl(result.kotlin)
}

/**
 * Builds an immutable sequenced set as according to the [filler], with an expected element count.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * Behavior of the [MutableSequencedSet] and the resulting set when using the [MutableSequencedSet] outside the lambda is undefined.
 */
inline fun <T> buildSequencedSet(initialCapacity: Int, filler: LinkedSet<T>.() -> Unit): SequencedSet<T> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = LinkedSetImpl<T>(LinkedHashSet.newLinkedHashSet(initialCapacity)).apply(filler)
	return if(result.isEmpty()) sequencedSetOf() else SequencedSetImpl(result.kotlin)
}

/**
 * Builds an immutable map as according to the [filler].
 *
 * Behavior of the [MutableMap] and the resulting map when using the [MutableMap] outside the lambda is undefined.
 */
inline fun <K, V> buildMap(filler: MutableMap<K, V>.() -> Unit): Map<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableMapImpl<K, V>(HashMap()).apply(filler)
	return if(result.isEmpty()) mapOf() else MapImpl(result.kotlin)
}

/**
 * Builds an immutable map as according to the [filler], with an expected entry count.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * Behavior of the [MutableMap] and the resulting map when using the [MutableMap] outside the lambda is undefined.
 */
inline fun <K, V> buildMap(initialCapacity: Int, filler: MutableMap<K, V>.() -> Unit): Map<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = MutableMapImpl<K, V>(HashMap.newHashMap(initialCapacity)).apply(filler)
	return if(result.isEmpty()) mapOf() else MapImpl(result.kotlin)
}

/**
 * Builds an immutable sequenced map as according to the [filler].
 *
 * Behavior of the [MutableSequencedMap] and the resulting map when using the [MutableSequencedMap] outside the lambda is undefined.
 */
inline fun <K, V> buildSequencedMap(filler: LinkedMap<K, V>.() -> Unit): SequencedMap<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = LinkedMapImpl<K, V>(LinkedHashMap()).apply(filler)
	return if(result.isEmpty()) sequencedMapOf() else SequencedMapImpl(result.kotlin)
}

/**
 * Builds an immutable sequenced map as according to the [filler], with an expected entry count.
 * Throws [IllegalArgumentException] if the [initialCapacity] is negative.
 *
 * Behavior of the [MutableSequencedMap] and the resulting map when using the [MutableSequencedMap] outside the lambda is undefined.
 */
inline fun <K, V> buildSequencedMap(initialCapacity: Int, filler: LinkedMap<K, V>.() -> Unit): SequencedMap<K, V> {
	contract {
		callsInPlace(filler, InvocationKind.EXACTLY_ONCE)
	}

	val result = LinkedMapImpl<K, V>(LinkedHashMap.newLinkedHashMap(initialCapacity)).apply(filler)
	return if(result.isEmpty()) sequencedMapOf() else SequencedMapImpl(result.kotlin)
}
