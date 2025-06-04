/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.collections.asList as kotlinAsList

/**
 * Returns an array that contains all elements copied from the collection.
 *
 * The order is defined as the encounter order.
 */
inline fun <reified T> CollectionView<T>.toTypedArray(): Array<T> {
	val iterator = iterator()
	return Array(size()) { iterator.next() }
}

/**
 * Returns an array that contains all entries copied from the map.
 *
 * The order is defined as the encounter order.
 */
fun <K, V> MapView<K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = this.entries.iterator()
	return Array(size()) {
		iterator.next().let { it.key to it.value }
	}
}

/**
 * Copies all elements from this collection to a [LongArray], by their encounter order.
 */
fun CollectionView<Long>.toLongArray(): LongArray = iterator().let { LongArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [IntArray], by their encounter order.
 */
fun CollectionView<Int>.toIntArray(): IntArray = iterator().let { IntArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [ShortArray], by their encounter order.
 */
fun CollectionView<Short>.toShortArray(): ShortArray = iterator().let { ShortArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [ByteArray], by their encounter order.
 */
fun CollectionView<Byte>.toByteArray(): ByteArray = iterator().let { ByteArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [DoubleArray], by their encounter order.
 */
fun CollectionView<Double>.toDoubleArray(): DoubleArray = iterator().let { DoubleArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [FloatArray], by their encounter order.
 */
fun CollectionView<Float>.toFloatArray(): FloatArray = iterator().let { FloatArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [CharArray], by their encounter order.
 */
fun CollectionView<Char>.toCharArray(): CharArray = iterator().let { CharArray(size()) { _ -> it.next() } }

/**
 * Copies all elements from this collection to a [BooleanArray], by their encounter order.
 */
fun CollectionView<Boolean>.toBooleanArray(): BooleanArray = iterator().let { BooleanArray(size()) { _ -> it.next() } }

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun <T> Array<T>.asList(): ListView<T> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun DoubleArray.asList(): ListView<Double> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun FloatArray.asList(): ListView<Float> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun LongArray.asList(): ListView<Long> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun IntArray.asList(): ListView<Int> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun ShortArray.asList(): ListView<Short> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun ByteArray.asList(): ListView<Byte> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun CharArray.asList(): ListView<Char> = kotlinAsList().asViewConnection()

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun BooleanArray.asList(): ListView<Boolean> = kotlinAsList().asViewConnection()
