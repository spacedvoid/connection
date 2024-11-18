package io.github.spacedvoid.connection

import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// Collection -> kotlin.collections.Collection

fun <T> Collection<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> by this.kotlin {}

fun <T> CollectionView<T>.asKotlin(): KotlinCollection<T> = object: KotlinCollection<T> by this.kotlin {}

fun <T> RemoveOnlyCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> by this.kotlin {}

fun <T> MutableCollection<T>.asKotlin(): KotlinMutableCollection<T> = object: KotlinMutableCollection<T> by this.kotlin {}

// SequencedCollection -> java.util.SequencedCollection

fun <T> SequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = object: java.util.SequencedCollection<T> by this.kotlin {}

fun <T> SequencedCollectionView<T>.asKotlin(): java.util.SequencedCollection<T> = object: java.util.SequencedCollection<T> by this.kotlin {}

fun <T> RemoveOnlySequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = object: java.util.SequencedCollection<T> by this.kotlin {}

fun <T> MutableSequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = object: java.util.SequencedCollection<T> by this.kotlin {}

// List -> kotlin.collections.List

fun <T> List<T>.asKotlin(): KotlinList<T> = object: KotlinList<T> by this.kotlin {}

fun <T> ListView<T>.asKotlin(): KotlinList<T> = object: KotlinList<T> by this.kotlin {}

fun <T> MutableList<T>.asKotlin(): KotlinMutableList<T> = object: KotlinMutableList<T> by this.kotlin {}

// Set -> kotlin.collections.Set

fun <T> Set<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> by this.kotlin {}

fun <T> SetView<T>.asKotlin(): KotlinSet<T> = object: KotlinSet<T> by this.kotlin {}

fun <T> RemoveOnlySet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> by this.kotlin {}

fun <T> MutableSet<T>.asKotlin(): KotlinMutableSet<T> = object: KotlinMutableSet<T> by this.kotlin {}

// SequencedSet -> java.util.SequencedSet

fun <T> SequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = object: java.util.SequencedSet<T> by this.kotlin {}

fun <T> SequencedSetView<T>.asKotlin(): java.util.SequencedSet<T> = object: java.util.SequencedSet<T> by this.kotlin {}

fun <T> RemoveOnlySequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = object: java.util.SequencedSet<T> by this.kotlin {}

fun <T> MutableSequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = object: java.util.SequencedSet<T> by this.kotlin {}

// SortedNavigableSet -> java.util.SortedSet

fun <T> SortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = object: java.util.SortedSet<T> by this.kotlin {}

fun <T> SortedNavigableSetView<T>.asKotlin(): java.util.SortedSet<T> = object: java.util.SortedSet<T> by this.kotlin {}

fun <T> RemoveOnlySortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = object: java.util.SortedSet<T> by this.kotlin {}

fun <T> MutableSortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = object: java.util.SortedSet<T> by this.kotlin {}

// NavigableSet -> java.util.NavigableSet

fun <T> NavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = object: java.util.NavigableSet<T> by this.kotlin {}

fun <T> NavigableSetView<T>.asKotlin(): java.util.NavigableSet<T> = object: java.util.NavigableSet<T> by this.kotlin {}

fun <T> RemoveOnlyNavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = object: java.util.NavigableSet<T> by this.kotlin {}

fun <T> MutableNavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = object: java.util.NavigableSet<T> by this.kotlin {}

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> by this.kotlin {}

fun <K, V> MapView<K, V>.asKotlin(): KotlinMap<K, V> = object: KotlinMap<K, V> by this.kotlin {}

fun <K, V> MutableMap<K, V>.asKotlin(): KotlinMutableMap<K, V> = object: KotlinMutableMap<K, V> by this.kotlin {}

// SequencedMap -> java.util.SequencedMap

fun <K, V> SequencedMap<K, V>.asKotlin(): java.util.SequencedMap<K, V> = object: java.util.SequencedMap<K, V> by this.kotlin {}

fun <K, V> SequencedMapView<K, V>.asKotlin(): java.util.SequencedMap<K, V> = object: java.util.SequencedMap<K, V> by this.kotlin {}

fun <K, V> MutableSequencedMap<K, V>.asKotlin(): java.util.SequencedMap<K, V> = object: java.util.SequencedMap<K, V> by this.kotlin {}

// SortedMap -> java.util.SortedMap

fun <K, V> SortedNavigableMap<K, V>.asKotlin(): java.util.SortedMap<K, V> = object: java.util.SortedMap<K, V> by this.kotlin {}

fun <K, V> SortedNavigableMapView<K, V>.asKotlin(): java.util.SortedMap<K, V> = object: java.util.SortedMap<K, V> by this.kotlin {}

fun <K, V> MutableSortedNavigableMap<K, V>.asKotlin(): java.util.SortedMap<K, V> = object: java.util.SortedMap<K, V> by this.kotlin {}

// NavigableMap -> java.util.NavigableMap

fun <K, V> NavigableMap<K, V>.asKotlin(): java.util.NavigableMap<K, V> = object: java.util.NavigableMap<K, V> by this.kotlin {}

fun <K, V> NavigableMapView<K, V>.asKotlin(): java.util.NavigableMap<K, V> = object: java.util.NavigableMap<K, V> by this.kotlin {}

fun <K, V> MutableNavigableMap<K, V>.asKotlin(): java.util.NavigableMap<K, V> = object: java.util.NavigableMap<K, V> by this.kotlin {}
