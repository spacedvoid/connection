/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import kotlin.collections.asList as kotlinAsList
import kotlin.collections.toList as kotlinToList
import kotlin.collections.toMutableSet as kotlinToMutableSet
import kotlin.collections.toSet as kotlinToSet

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
fun <K, V> MapView<out K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = iterator()
	return Array(size()) { iterator.next().toPair() }
}

/**
 * Copies all elements from this collection to a [LongArray], by their encounter order.
 */
fun CollectionView<Long>.toLongArray(): LongArray {
	val iterator = iterator()
	return LongArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [IntArray], by their encounter order.
 */
fun CollectionView<Int>.toIntArray(): IntArray {
	val iterator = iterator()
	return IntArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [ShortArray], by their encounter order.
 */
fun CollectionView<Short>.toShortArray(): ShortArray {
	val iterator = iterator()
	return ShortArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [ByteArray], by their encounter order.
 */
fun CollectionView<Byte>.toByteArray(): ByteArray {
	val iterator = iterator()
	return ByteArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [DoubleArray], by their encounter order.
 */
fun CollectionView<Double>.toDoubleArray(): DoubleArray {
	val iterator = iterator()
	return DoubleArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [FloatArray], by their encounter order.
 */
fun CollectionView<Float>.toFloatArray(): FloatArray {
	val iterator = iterator()
	return FloatArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [CharArray], by their encounter order.
 */
fun CollectionView<Char>.toCharArray(): CharArray {
	val iterator = iterator()
	return CharArray(size()) { iterator.next() }
}

/**
 * Copies all elements from this collection to a [BooleanArray], by their encounter order.
 */
fun CollectionView<Boolean>.toBooleanArray(): BooleanArray {
	val iterator = iterator()
	return BooleanArray(size()) { iterator.next() }
}

/**
 * Wraps an array to a list.
 *
 * Changes to the array are visible to the list.
 */
fun <T> Array<out T>.asList(): ListView<T> = kotlinAsList().asViewConnection()

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

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun <T> Array<out T>.toList(): List<T> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun DoubleArray.toList(): List<Double> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun FloatArray.toList(): List<Float> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun LongArray.toList(): List<Long> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun IntArray.toList(): List<Int> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun ShortArray.toList(): List<Short> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun ByteArray.toList(): List<Byte> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun CharArray.toList(): List<Char> = kotlinToList().asConnection()

/**
 * Creates a list that contains all elements from the array by their iteration order.
 */
fun BooleanArray.toList(): List<Boolean> = kotlinToList().asConnection()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun <T> Array<out T>.toMutableList(): MutableList<T> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun DoubleArray.toMutableList(): MutableList<Double> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun FloatArray.toMutableList(): MutableList<Float> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun LongArray.toMutableList(): MutableList<Long> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun IntArray.toMutableList(): MutableList<Int> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun ShortArray.toMutableList(): MutableList<Short> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun ByteArray.toMutableList(): MutableList<Byte> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun CharArray.toMutableList(): MutableList<Char> = asList().toMutableList()

/**
 * Creates a mutable list that contains all elements from the array by their iteration order.
 */
fun BooleanArray.toMutableList(): MutableList<Boolean> = asList().toMutableList()

/**
 * Creates a set that contains all elements from the array.
 */
fun <T> Array<out T>.toSet(): Set<T> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun DoubleArray.toSet(): Set<Double> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun FloatArray.toSet(): Set<Float> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun LongArray.toSet(): Set<Long> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun IntArray.toSet(): Set<Int> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun ShortArray.toSet(): Set<Short> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun ByteArray.toSet(): Set<Byte> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun CharArray.toSet(): Set<Char> = kotlinToSet().asConnection()

/**
 * Creates a set that contains all elements from the array.
 */
fun BooleanArray.toSet(): Set<Boolean> = kotlinToSet().asConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun <T> Array<out T>.toMutableSet(): MutableSet<T> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun DoubleArray.toMutableSet(): MutableSet<Double> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun FloatArray.toMutableSet(): MutableSet<Float> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun LongArray.toMutableSet(): MutableSet<Long> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun IntArray.toMutableSet(): MutableSet<Int> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun ShortArray.toMutableSet(): MutableSet<Short> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun ByteArray.toMutableSet(): MutableSet<Byte> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun CharArray.toMutableSet(): MutableSet<Char> = kotlinToMutableSet().asMutableConnection()

/**
 * Creates a mutable set that contains all elements from the array.
 */
fun BooleanArray.toMutableSet(): MutableSet<Boolean> = kotlinToMutableSet().asMutableConnection()
