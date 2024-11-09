package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper
import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// Collection -> kotlin.collections.Collection

fun <T> Collection<T>.asKotlin(): KotlinCollection<T> = this.origin()

fun <T> CollectionView<T>.asKotlin(): KotlinCollection<T> = this.origin()

fun <T> RemoveOnlyCollection<T>.asKotlin(): KotlinMutableCollection<T> = this.origin()

fun <T> MutableCollection<T>.asKotlin(): KotlinMutableCollection<T> = this.origin()

// SequencedCollection -> java.util.SequencedCollection

fun <T> SequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = this.origin()

fun <T> SequencedCollectionView<T>.asKotlin(): java.util.SequencedCollection<T> = this.origin()

fun <T> RemoveOnlySequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = this.origin()

fun <T> MutableSequencedCollection<T>.asKotlin(): java.util.SequencedCollection<T> = this.origin()

// List -> kotlin.collections.List

fun <T> List<T>.asKotlin(): KotlinList<T> = this.origin()

fun <T> ListView<T>.asKotlin(): KotlinList<T> = this.origin()

fun <T> MutableList<T>.asKotlin(): KotlinMutableList<T> = this.origin()

// Set -> kotlin.collections.Set

fun <T> Set<T>.asKotlin(): KotlinSet<T> = this.origin()

fun <T> SetView<T>.asKotlin(): KotlinSet<T> = this.origin()

fun <T> RemoveOnlySet<T>.asKotlin(): KotlinMutableSet<T> = this.origin()

fun <T> MutableSet<T>.asKotlin(): KotlinMutableSet<T> = this.origin()

// SequencedSet -> java.util.SequencedSet

fun <T> SequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = this.origin()

fun <T> SequencedSetView<T>.asKotlin(): java.util.SequencedSet<T> = this.origin()

fun <T> RemoveOnlySequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = this.origin()

fun <T> MutableSequencedSet<T>.asKotlin(): java.util.SequencedSet<T> = this.origin()

// SortedNavigableSet -> java.util.SortedSet

fun <T> SortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = this.origin()

fun <T> SortedNavigableSetView<T>.asKotlin(): java.util.SortedSet<T> = this.origin()

fun <T> RemoveOnlySortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = this.origin()

fun <T> MutableSortedNavigableSet<T>.asKotlin(): java.util.SortedSet<T> = this.origin()

// NavigableSet -> java.util.NavigableSet

fun <T> NavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = this.origin()

fun <T> NavigableSetView<T>.asKotlin(): java.util.NavigableSet<T> = this.origin()

fun <T> RemoveOnlyNavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = this.origin()

fun <T> MutableNavigableSet<T>.asKotlin(): java.util.NavigableSet<T> = this.origin()

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.asKotlin(): KotlinMap<K, V> = this.origin()

fun <K, V> MapView<K, V>.asKotlin(): KotlinMap<K, V> = this.origin()

fun <K, V> MutableMap<K, V>.asKotlin(): KotlinMutableMap<K, V> = this.origin()

// SequencedMap -> java.util.SequencedMap

fun <K, V> SequencedMap<K, V>.asKotlin(): java.util.SequencedMap<K, V> = this.origin()

fun <K, V> SequencedMapView<K, V>.asKotlin(): java.util.SequencedMap<K, V> = this.origin()

fun <K, V> MutableSequencedMap<K, V>.asKotlin(): java.util.SequencedMap<K, V> = this.origin()

// SortedMap -> java.util.SortedMap

fun <K, V> SortedNavigableMap<K, V>.asKotlin(): java.util.SortedMap<K, V> = this.origin()

fun <K, V> SortedNavigableMapView<K, V>.asKotlin(): java.util.SortedMap<K, V> = this.origin()

fun <K, V> MutableSortedNavigableMap<K, V>.asKotlin(): java.util.SortedMap<K, V> = this.origin()

// NavigableMap -> java.util.NavigableMap

fun <K, V> NavigableMap<K, V>.asKotlin(): java.util.NavigableMap<K, V> = this.origin()

fun <K, V> NavigableMapView<K, V>.asKotlin(): java.util.NavigableMap<K, V> = this.origin()

fun <K, V> MutableNavigableMap<K, V>.asKotlin(): java.util.NavigableMap<K, V> = this.origin()

@Suppress("UNCHECKED_CAST")
internal fun <T> Any.origin(): T = (this as Wrapper<T>).origin
