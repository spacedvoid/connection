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

fun <T> KotlinCollection<T>.toConnection(): Collection<T> = this.asViewConnection().snapshot()

fun <T> KotlinCollection<T>.toViewConnection(): MutatingCollectionView<T> = this.asViewConnection().copy().asView()

fun <T> KotlinMutableCollection<T>.toRemoveOnlyConnection(): RemoveOnlyCollection<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> KotlinMutableCollection<T>.toConnection(): MutableCollection<T> = this.asConnection().copy()

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.toConnection(): SequencedCollection<T> = this.asViewConnection().snapshot()

fun <T> java.util.SequencedCollection<T>.toViewConnection(): MutatingSequencedCollectionView<T> = this.asViewConnection().copy().asView()

fun <T> java.util.SequencedCollection<T>.toRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> java.util.SequencedCollection<T>.toMutableConnection(): MutableSequencedCollection<T> = this.asMutableConnection().copy()

// kotlin.collections.List -> List

fun <T> KotlinList<T>.toConnection(): List<T> = this.asViewConnection().snapshot()

fun <T> KotlinList<T>.toViewConnection(): MutatingListView<T> = this.asViewConnection().copy().asView()

fun <T> KotlinMutableList<T>.toConnection(): MutableList<T> = this.asConnection().copy()

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.toConnection(): Set<T> = this.asViewConnection().snapshot()

fun <T> KotlinSet<T>.toViewConnection(): MutatingSetView<T> = this.asViewConnection().copy().asView()

fun <T> KotlinMutableSet<T>.toRemoveOnlyConnection(): RemoveOnlySet<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> KotlinMutableSet<T>.toConnection(): MutableSet<T> = this.asConnection().copy()

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.toConnection(): SequencedSet<T> = this.asViewConnection().snapshot()

fun <T> java.util.SequencedSet<T>.toViewConnection(): MutatingSequencedSetView<T> = this.asViewConnection().copy().asView()

fun <T> java.util.SequencedSet<T>.toRemoveOnlyConnection(): RemoveOnlySequencedSet<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> java.util.SequencedSet<T>.toMutableConnection(): MutableSequencedSet<T> = this.asMutableConnection().copy()

// java.util.SortedSet -> NavigableSortedSet

fun <T> java.util.SortedSet<T>.toConnection(): SortedNavigableSet<T> = this.asViewConnection().snapshot()

fun <T> java.util.SortedSet<T>.toViewConnection(): MutatingSortedNavigableSetView<T> = this.asViewConnection().copy().asView()

fun <T> java.util.SortedSet<T>.toRemoveOnlyConnection(): RemoveOnlySortedNavigableSet<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> java.util.SortedSet<T>.toMutableConnection(): MutableSortedNavigableSet<T> = this.asMutableConnection().copy()

// java.util.NavigableSet -> NavigableSet

fun <T> java.util.NavigableSet<T>.toConnection(): NavigableSet<T> = this.asViewConnection().snapshot()

fun <T> java.util.NavigableSet<T>.toViewConnection(): MutatingNavigableSetView<T> = this.asViewConnection().copy().asView()

fun <T> java.util.NavigableSet<T>.toRemoveOnlyConnection(): RemoveOnlyNavigableSet<T> = this.asRemoveOnlyConnection().copy().asRemoveOnly()

fun <T> java.util.NavigableSet<T>.toMutableConnection(): MutableNavigableSet<T> = this.asMutableConnection().copy()

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.toConnection(): Map<K, V> = this.asViewConnection().snapshot()

fun <K, V> KotlinMap<K, V>.toViewConnection(): MutatingMapView<K, V> = this.asViewConnection().copy().asView()

fun <K, V> KotlinMutableMap<K, V>.toConnection(): MutableMap<K, V> = this.asConnection().copy()

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.toConnection(): SequencedMap<K, V> = this.asViewConnection().snapshot()

fun <K, V> java.util.SequencedMap<K, V>.toViewConnection(): MutatingSequencedMapView<K, V> = this.asViewConnection().copy().asView()

fun <K, V> java.util.SequencedMap<K, V>.toMutableConnection(): MutableSequencedMap<K, V> = this.asMutableConnection().copy()

// java.util.SortedMap -> SortedNavigableMap

fun <K, V> java.util.SortedMap<K, V>.toConnection(): SortedNavigableMap<K, V> = this.asViewConnection().snapshot()

fun <K, V> java.util.SortedMap<K, V>.toViewConnection(): MutatingSortedNavigableMapView<K, V> = this.asViewConnection().copy().asView()

fun <K, V> java.util.SortedMap<K, V>.toMutableConnection(): MutableSortedNavigableMap<K, V> = this.asMutableConnection().copy()

// java.util.NavigableMap -> NavigableMap

fun <K, V> java.util.NavigableMap<K, V>.toConnection(): NavigableMap<K, V> = this.asViewConnection().snapshot()

fun <K, V> java.util.NavigableMap<K, V>.toViewConnection(): MutatingNavigableMapView<K, V> = this.asViewConnection().copy().asView()

fun <K, V> java.util.NavigableMap<K, V>.toMutableConnection(): MutableNavigableMap<K, V> = this.asMutableConnection().copy()
