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

fun <T> MutatingCollectionView<T>.toKotlin(): KotlinCollection<T> = this.copy().asView().asKotlin()

fun <T> RemoveOnlyCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.copy().asRemoveOnly().asKotlin()

fun <T> MutableCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.copy().asKotlin()

// SequencedCollection -> java.util.SequencedCollection

fun <T> SequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.asKotlin()

fun <T> MutatingSequencedCollectionView<T>.toKotlin(): java.util.SequencedCollection<T> = this.copy().asView().asKotlin()

fun <T> RemoveOnlySequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.copy().asRemoveOnly().asKotlin()

fun <T> MutableSequencedCollection<T>.toKotlin(): java.util.SequencedCollection<T> = this.copy().asKotlin()

// List -> kotlin.collections.List

fun <T> List<T>.toKotlin(): KotlinList<T> = this.asKotlin()

fun <T> MutatingListView<T>.toKotlin(): KotlinList<T> = this.copy().asView().asKotlin()

fun <T> MutableList<T>.toKotlin(): KotlinMutableList<T> = this.copy().asKotlin()

// Set -> kotlin.collections.Set

fun <T> Set<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> MutatingSetView<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> RemoveOnlySet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin()

fun <T> MutableSet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin().toMutableSet()

// SequencedSet -> java.util.SequencedSet

fun <T> SequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

fun <T> MutatingSequencedSetView<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

fun <T> RemoveOnlySequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

fun <T> MutableSequencedSet<T>.toKotlin(): java.util.SequencedSet<T> = this.asKotlin()

// SortedNavigableSet -> java.util.SortedSet

fun <T> SortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.asKotlin()

fun <T> MutatingSortedNavigableSetView<T>.toKotlin(): java.util.SortedSet<T> = this.copy().asView().asKotlin()

fun <T> RemoveOnlySortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.copy().asRemoveOnly().asKotlin()

fun <T> MutableSortedNavigableSet<T>.toKotlin(): java.util.SortedSet<T> = this.copy().asKotlin()

// NavigableSet -> java.util.NavigableSet

fun <T> NavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.asKotlin()

fun <T> MutatingNavigableSetView<T>.toKotlin(): java.util.NavigableSet<T> = this.copy().asView().asKotlin()

fun <T> RemoveOnlyNavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.copy().asRemoveOnly().asKotlin()

fun <T> MutableNavigableSet<T>.toKotlin(): java.util.NavigableSet<T> = this.copy().asKotlin()

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin()

fun <K, V> MutatingMapView<K, V>.toKotlin(): KotlinMap<K, V> = this.copy().asView().asKotlin()

fun <K, V> MutableMap<K, V>.toKotlin(): KotlinMutableMap<K, V> = this.copy().asKotlin()

// SequencedMap -> java.util.SequencedMap

fun <K, V> SequencedMap<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.asKotlin()

fun <K, V> MutatingSequencedMapView<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.copy().asView().asKotlin()

fun <K, V> MutableSequencedMap<K, V>.toKotlin(): java.util.SequencedMap<K, V> = this.copy().asKotlin()

// NavigableMap -> kotlin.collections.Map

fun <K, V> NavigableMap<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.asKotlin()

fun <K, V> MutatingNavigableMapView<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.copy().asView().asKotlin()

fun <K, V> MutableNavigableMap<K, V>.toKotlin(): java.util.NavigableMap<K, V> = this.copy().asKotlin()
