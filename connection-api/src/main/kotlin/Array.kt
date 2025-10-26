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
 * Collects all elements in this collection to a new array by their encounter order.
 */
inline fun <reified T> CollectionView<T>.toTypedArray(): Array<T> {
	val iterator = iterator()
	return Array(size()) { iterator.next() }
}

/**
 * Collects all keys and values in this map to a new array by their encounter order.
 */
fun <K, V> MapView<out K, V>.toArray(): Array<Pair<K, V>> {
	val iterator = iterator()
	return Array(size()) { iterator.next().toPair() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Long>.toLongArray(): LongArray {
	val iterator = iterator()
	return LongArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Int>.toIntArray(): IntArray {
	val iterator = iterator()
	return IntArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Short>.toShortArray(): ShortArray {
	val iterator = iterator()
	return ShortArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Byte>.toByteArray(): ByteArray {
	val iterator = iterator()
	return ByteArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Double>.toDoubleArray(): DoubleArray {
	val iterator = iterator()
	return DoubleArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Float>.toFloatArray(): FloatArray {
	val iterator = iterator()
	return FloatArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Char>.toCharArray(): CharArray {
	val iterator = iterator()
	return CharArray(size()) { iterator.next() }
}

/**
 * Collects all elements in this collection to a new array by their encounter order.
 */
fun CollectionView<Boolean>.toBooleanArray(): BooleanArray {
	val iterator = iterator()
	return BooleanArray(size()) { iterator.next() }
}

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun <T> Array<out T>.asList(): ListView<T> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun DoubleArray.asList(): ListView<Double> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun FloatArray.asList(): ListView<Float> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun LongArray.asList(): ListView<Long> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun IntArray.asList(): ListView<Int> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun ShortArray.asList(): ListView<Short> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun ByteArray.asList(): ListView<Byte> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun CharArray.asList(): ListView<Char> = kotlinAsList().asViewConnection()

/**
 * Returns a list that reflects the elements in this array.
 *
 * Changes to this array are visible to the list.
 */
fun BooleanArray.asList(): ListView<Boolean> = kotlinAsList().asViewConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun <T> Array<out T>.toList(): List<T> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun DoubleArray.toList(): List<Double> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun FloatArray.toList(): List<Float> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun LongArray.toList(): List<Long> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun IntArray.toList(): List<Int> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun ShortArray.toList(): List<Short> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun ByteArray.toList(): List<Byte> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun CharArray.toList(): List<Char> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a list by their iteration order.
 */
fun BooleanArray.toList(): List<Boolean> = kotlinToList().asConnection()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun <T> Array<out T>.toMutableList(): MutableList<T> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun DoubleArray.toMutableList(): MutableList<Double> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun FloatArray.toMutableList(): MutableList<Float> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun LongArray.toMutableList(): MutableList<Long> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun IntArray.toMutableList(): MutableList<Int> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun ShortArray.toMutableList(): MutableList<Short> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun ByteArray.toMutableList(): MutableList<Byte> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun CharArray.toMutableList(): MutableList<Char> = asList().toMutableList()

/**
 * Collects all elements in this array to a new mutable list by their iteration order.
 */
fun BooleanArray.toMutableList(): MutableList<Boolean> = asList().toMutableList()

/**
 * Collects all elements in this array to a set by their iteration order.
 */
fun <T> Array<out T>.toSet(): Set<T> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun DoubleArray.toSet(): Set<Double> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun FloatArray.toSet(): Set<Float> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun LongArray.toSet(): Set<Long> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun IntArray.toSet(): Set<Int> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun ShortArray.toSet(): Set<Short> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun ByteArray.toSet(): Set<Byte> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun CharArray.toSet(): Set<Char> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a set.
 */
fun BooleanArray.toSet(): Set<Boolean> = kotlinToSet().asConnection()

/**
 * Collects all elements in this array to a new mutable set by their iteration order.
 */
fun <T> Array<out T>.toMutableSet(): MutableSet<T> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun DoubleArray.toMutableSet(): MutableSet<Double> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun FloatArray.toMutableSet(): MutableSet<Float> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun LongArray.toMutableSet(): MutableSet<Long> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun IntArray.toMutableSet(): MutableSet<Int> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun ShortArray.toMutableSet(): MutableSet<Short> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun ByteArray.toMutableSet(): MutableSet<Byte> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun CharArray.toMutableSet(): MutableSet<Char> = kotlinToMutableSet().asMutableConnection()

/**
 * Collects all elements in this array to a new mutable set.
 */
fun BooleanArray.toMutableSet(): MutableSet<Boolean> = kotlinToMutableSet().asMutableConnection()
