package io.github.spacedvoid.connection

import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// kotlin.collections.Collection -> Collection

fun <T> KotlinCollection<T>.toConnection(): Collection<T> = this.asConnection()

fun <T> KotlinCollection<T>.toViewConnection(): CollectionView<T> = this.asViewConnection()

fun <T> KotlinMutableCollection<T>.toRemoveOnlyConnection(): RemoveOnlyCollection<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> KotlinMutableCollection<T>.toConnection(): MutableCollection<T> = this.asViewConnection().snapshot().toMutable()

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.toConnection(): SequencedCollection<T> = this.asConnection()

fun <T> java.util.SequencedCollection<T>.toViewConnection(): SequencedCollectionView<T> = this.asViewConnection()

fun <T> java.util.SequencedCollection<T>.toRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> java.util.SequencedCollection<T>.toMutableConnection(): MutableSequencedCollection<T> = this.asViewConnection().snapshot().toMutable()

// kotlin.collections.List -> List

fun <T> KotlinList<T>.toConnection(): List<T> = this.asConnection()

fun <T> KotlinList<T>.toViewConnection(): ListView<T> = this.asViewConnection()

fun <T> KotlinMutableList<T>.toConnection(): MutableList<T> = this.asViewConnection().snapshot().toMutable()

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.toConnection(): Set<T> = this.asConnection()

fun <T> KotlinSet<T>.toViewConnection(): SetView<T> = this.asViewConnection()

fun <T> KotlinMutableSet<T>.toRemoveOnlyConnection(): RemoveOnlySet<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> KotlinMutableSet<T>.toConnection(): MutableSet<T> = this.asViewConnection().snapshot().toMutable()

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.toConnection(): SequencedSet<T> = this.asConnection()

fun <T> java.util.SequencedSet<T>.toViewConnection(): SequencedSetView<T> = this.asViewConnection()

fun <T> java.util.SequencedSet<T>.toRemoveOnlyConnection(): RemoveOnlySequencedSet<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> java.util.SequencedSet<T>.toMutableConnection(): MutableSequencedSet<T> = this.asViewConnection().snapshot().toMutable()

// java.util.SortedSet -> NavigableSortedSet

fun <T> java.util.SortedSet<T>.toConnection(): SortedNavigableSet<T> = this.asConnection()

fun <T> java.util.SortedSet<T>.toViewConnection(): SortedNavigableSetView<T> = this.asViewConnection()

fun <T> java.util.SortedSet<T>.toRemoveOnlyConnection(): RemoveOnlySortedNavigableSet<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> java.util.SortedSet<T>.toMutableConnection(): MutableSortedNavigableSet<T> = this.asViewConnection().snapshot().toMutable()

// java.util.NavigableSet -> NavigableSet

fun <T> java.util.NavigableSet<T>.toConnection(): NavigableSet<T> = this.asConnection()

fun <T> java.util.NavigableSet<T>.toViewConnection(): NavigableSetView<T> = this.asViewConnection()

fun <T> java.util.NavigableSet<T>.toRemoveOnlyConnection(): RemoveOnlyNavigableSet<T> = this.asViewConnection().snapshot().toMutable().asRemoveOnly()

fun <T> java.util.NavigableSet<T>.toMutableConnection(): MutableNavigableSet<T> = this.asViewConnection().snapshot().toMutable()

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.toConnection(): Map<K, V> = this.asConnection()

fun <K, V> KotlinMap<K, V>.toViewConnection(): MapView<K, V> = this.asViewConnection()

fun <K, V> KotlinMutableMap<K, V>.toConnection(): MutableMap<K, V> = this.asViewConnection().snapshot().toMutable()

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.toConnection(): SequencedMap<K, V> = this.asConnection()

fun <K, V> java.util.SequencedMap<K, V>.toViewConnection(): SequencedMapView<K, V> = this.asViewConnection()

fun <K, V> java.util.SequencedMap<K, V>.toMutableConnection(): MutableSequencedMap<K, V> = this.asViewConnection().snapshot().toMutable()

// java.util.SortedMap -> SortedNavigableMap

fun <K, V> java.util.SortedMap<K, V>.toConnection(): SortedNavigableMap<K, V> = this.asConnection()

fun <K, V> java.util.SortedMap<K, V>.toViewConnection(): SortedNavigableMapView<K, V> = this.asViewConnection()

fun <K, V> java.util.SortedMap<K, V>.toMutableConnection(): MutableSortedNavigableMap<K, V> = this.asViewConnection().snapshot().toMutable()

// java.util.NavigableMap -> NavigableMap

fun <K, V> java.util.NavigableMap<K, V>.toConnection(): NavigableMap<K, V> = this.asConnection()

fun <K, V> java.util.NavigableMap<K, V>.toViewConnection(): NavigableMapView<K, V> = this.asViewConnection()

fun <K, V> java.util.NavigableMap<K, V>.toMutableConnection(): MutableNavigableMap<K, V> = this.asViewConnection().snapshot().toMutable()
