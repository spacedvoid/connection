/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("IfThenToElvis")

package io.github.spacedvoid.connection

/**
 * Returns an immutable copy of this collection.
 */
fun <T> CollectionView<T>.snapshot(): Collection<T> = toList()

/**
 * Returns an immutable copy of this collection.
 *
 * The iteration order will be preserved.
 */
fun <T> SequencedCollectionView<T>.snapshot(): SequencedCollection<T> = toList()

/**
 * Returns an immutable copy of this collection.
 *
 * The iteration order will be preserved.
 */
fun <T> ListView<T>.snapshot(): List<T> = toList()

/**
 * Returns an immutable copy of this collection.
 */
fun <T> SetView<T>.snapshot(): Set<T> = toSet()

/**
 * Returns an immutable copy of this collection.
 *
 * The iteration order will be preserved.
 */
fun <T> SequencedSetView<T>.snapshot(): SequencedSet<T> = toSequencedSet()

/**
 * Returns an immutable copy of this collection.
 *
 * The iteration order will be preserved.
 */
fun <T> NavigableSetView<T>.snapshot(): NavigableSet<T> = toNavigableSet(this.comparator)

/**
 * Returns an immutable copy of this map.
 */
fun <K, V> MapView<out K, V>.snapshot(): Map<K, V> = if(this is Map<K, V>) this else buildMap { putAll(this@snapshot) }

/**
 * Returns an immutable copy of this map.
 *
 * The iteration order will be preserved.
 */
fun <K ,V> SequencedMapView<out K, V>.snapshot(): SequencedMap<K, V> = toSequencedMap()

/**
 * Returns an immutable copy of this map.
 *
 * The iteration order will be preserved.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> NavigableMapView<out K, V>.snapshot(): NavigableMap<K, V> = toNavigableMap(this.comparator as Comparator<in K>)
