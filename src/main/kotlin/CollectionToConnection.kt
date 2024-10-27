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

fun <T> KotlinCollection<T>.toConnection(): Collection<T> = this.toList().asConnection()

fun <T> KotlinCollection<T>.toViewConnection(): MutatingCollectionView<T> = this.asViewConnection()

fun <T> KotlinMutableCollection<T>.toRemoveOnlyConnection(): RemoveOnlyCollection<T> = this.asRemoveOnlyConnection()

fun <T> KotlinMutableCollection<T>.toConnection(): MutableCollection<T> = this.toMutableList().asConnection()

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.toConnection(): SequencedCollection<T> = this.toList().asSequencedConnection()

fun <T> java.util.SequencedCollection<T>.toViewConnection(): MutatingSequencedCollectionView<T> = this.asViewConnection()

fun <T> java.util.SequencedCollection<T>.toRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = this.asRemoveOnlyConnection()

fun <T> java.util.SequencedCollection<T>.toMutableConnection(): MutableSequencedCollection<T> = this.toMutableList().asSequencedConnection()

// kotlin.collections.List -> SequencedCollection

fun <T> KotlinList<T>.toSequencedConnection(): SequencedCollection<T> = this.toList().asSequencedConnection()

fun <T> KotlinList<T>.toSequencedViewConnection(): MutatingSequencedCollectionView<T> = this.asViewConnection()

fun <T> KotlinMutableList<T>.toSequencedRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = this.asSequencedRemoveOnlyConnection()

fun <T> KotlinMutableList<T>.toSequencedConnection(): MutableSequencedCollection<T> = this.toMutableList().asSequencedConnection()

// kotlin.collections.List -> List

fun <T> KotlinList<T>.toConnection(): List<T> = this.toList().asConnection()

fun <T> KotlinList<T>.toViewConnection(): MutatingListView<T> = this.asViewConnection()

fun <T> KotlinMutableList<T>.toConnection(): MutableList<T> = this.toMutableList().asConnection()

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.toConnection(): Set<T> = this.toSet().asConnection()

fun <T> KotlinSet<T>.toViewConnection(): MutatingSetView<T> = this.asViewConnection()

fun <T> KotlinMutableSet<T>.toRemoveOnlyConnection(): RemoveOnlySet<T> = this.toMutableSet().asRemoveOnlyConnection()

fun <T> KotlinMutableSet<T>.toConnection(): MutableSet<T> = this.toMutableSet().asConnection()

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.toConnection(): SequencedSet<T> = LinkedHashSet(this).asConnection()

fun <T> java.util.SequencedSet<T>.toViewConnection(): MutatingSequencedSetView<T> = this.asViewConnection()

fun <T> java.util.SequencedSet<T>.toRemoveOnlyConnection(): RemoveOnlySequencedSet<T> = LinkedHashSet(this).asRemoveOnlyConnection()

fun <T> java.util.SequencedSet<T>.toMutableConnection(): MutableSequencedSet<T> = LinkedHashSet(this).asMutableConnection()

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.toConnection(): Map<K, V> = this.toMap().asConnection()

fun <K, V> KotlinMap<K, V>.toViewConnection(): MutatingMapView<K, V> = this.asViewConnection()

fun <K, V> KotlinMutableMap<K, V>.toConnection(): MutableMap<K, V> = this.toMutableMap().asConnection()

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.toConnection(): SequencedMap<K, V> = LinkedHashMap(this).asConnection()

fun <K, V> java.util.SequencedMap<K, V>.toViewConnection(): MutatingSequencedMapView<K, V> = this.asViewConnection()

fun <K, V> java.util.SequencedMap<K, V>.toMutableConnection(): MutableSequencedMap<K, V> = LinkedHashMap(this).asMutableConnection()
