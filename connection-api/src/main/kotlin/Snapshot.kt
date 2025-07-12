/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("IfThenToElvis")

package io.github.spacedvoid.connection

/**
 * Creates an immutable copy of this collection.
 */
fun <T> CollectionView<T>.snapshot(): Collection<T> = toList()

/**
 * Creates an immutable copy of this collection.
 */
fun <T> SequencedCollectionView<T>.snapshot(): SequencedCollection<T> = toList()

/**
 * Creates an immutable copy of this collection.
 */
fun <T> ListView<T>.snapshot(): List<T> = toList()

/**
 * Creates an immutable copy of this collection.
 */
fun <T> SetView<T>.snapshot(): Set<T> = toSet()

/**
 * Creates an immutable copy of this collection.
 */
fun <T> SequencedSetView<T>.snapshot(): SequencedSet<T> = toSequencedSet()

/**
 * Creates an immutable copy of this collection.
 */
fun <T> NavigableSetView<T>.snapshot(): NavigableSet<T> = toNavigableSet(this.comparator)

/**
 * Creates an immutable copy of this map.
 */
fun <K, V> MapView<K, V>.snapshot(): Map<K, V> = if(this is Map<K, V>) this else buildMap { putAll(this@snapshot) }

/**
 * Creates an immutable copy of this map.
 */
fun <K ,V> SequencedMapView<K, V>.snapshot(): SequencedMap<K, V> = toSequencedMap()

/**
 * Creates an immutable copy of this map.
 */
fun <K, V> NavigableMapView<K, V>.snapshot(): NavigableMap<K, V> = toNavigableMap(this.comparator)
