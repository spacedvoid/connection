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

fun <T> Collection<T>.toKotlin(): KotlinCollection<T> = this.asKotlin()

fun <T> CollectionView<T>.toKotlin(): KotlinCollection<T> = this.asKotlin()

fun <T> RemoveOnlyCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.snapshot().toMutable().asKotlin()

// SequencedCollection -> java.util.SequencedCollection

fun <T> SequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.asKotlin()

fun <T> SequencedCollectionView<T>.toKotlin(): java.util.SequencedCollection<T> = this.asKotlin()

fun <T> RemoveOnlySequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableSequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.snapshot().toMutable().asKotlin()

// List -> kotlin.collections.List

fun <T> List<T>.toKotlin(): KotlinList<T> = this.asKotlin()

fun <T> ListView<T>.toKotlin(): KotlinList<T> = this.asKotlin()

fun <T> MutableList<T>.toKotlin(): KotlinMutableList<T> = this.snapshot().toMutable().asKotlin()

// List -> java.util.SequencedCollection

fun <T> List<T>.toSequencedKotlin(): java.util.SequencedCollection<T> = this.asSequencedKotlin()

fun <T> ListView<T>.toSequencedKotlin(): java.util.SequencedCollection<T> = this.asSequencedKotlin()

fun <T> MutableList<T>.toSequencedKotlin(): java.util.SequencedCollection<T> = this.snapshot().toMutable().asSequencedKotlin()

// Set -> kotlin.collections.Set

fun <T> Set<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> SetView<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> RemoveOnlySet<T>.toKotlin(): KotlinMutableSet<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableSet<T>.toKotlin(): KotlinMutableSet<T> = this.snapshot().toMutable().asKotlin()

// SequencedSet -> java.util.SequencedSet

fun <T> SequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

fun <T> SequencedSetView<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

fun <T> RemoveOnlySequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableSequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.snapshot().toMutable().asKotlin()

// SortedNavigableSet -> java.util.SortedSet

fun <T> SortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.asKotlin()

fun <T> SortedNavigableSetView<T>.toKotlin(): java.util.SortedSet<T> = this.asKotlin()

fun <T> RemoveOnlySortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableSortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.snapshot().toMutable().asKotlin()

// NavigableSet -> java.util.NavigableSet

fun <T> NavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.asKotlin()

fun <T> NavigableSetView<T>.toKotlin(): java.util.NavigableSet<T> = this.asKotlin()

fun <T> RemoveOnlyNavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.snapshot().toMutable().asRemoveOnly().asKotlin()

fun <T> MutableNavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.snapshot().toMutable().asKotlin()

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin()

fun <K, V> MapView<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin()

fun <K, V> MutableMap<K, V>.toKotlin(): KotlinMutableMap<K, V> = this.snapshot().toMutable().asKotlin()

// SequencedMap -> java.util.SequencedMap

fun <K, V> SequencedMap<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.asKotlin()

fun <K, V> SequencedMapView<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.asKotlin()

fun <K, V> MutableSequencedMap<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.snapshot().toMutable().asKotlin()

// NavigableMap -> kotlin.collections.Map

fun <K, V> NavigableMap<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.asKotlin()

fun <K, V> NavigableMapView<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.asKotlin()

fun <K, V> MutableNavigableMap<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.snapshot().toMutable().asKotlin()
