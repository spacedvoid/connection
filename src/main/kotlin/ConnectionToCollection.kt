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

fun <T> Collection<T>.toKotlin(): KotlinCollection<T> = this.asKotlin().toList()

fun <T> MutatingCollectionView<T>.toKotlin(): KotlinCollection<T> = this.asKotlin()

fun <T> RemoveOnlyCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.asKotlin()

fun <T> MutableCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.asKotlin().toMutableList()

// SequencedCollection -> kotlin.collections.Collection

fun <T> SequencedCollection<T>.toKotlin(): KotlinCollection<T> = this.asKotlin().toList()

fun <T> MutatingSequencedCollectionView<T>.toKotlin(): KotlinCollection<T> = this.asKotlin()

fun <T> RemoveOnlySequencedCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.asKotlin()

fun <T> MutableSequencedCollection<T>.toKotlin(): KotlinMutableCollection<T> = this.asKotlin().toMutableList()

// List -> kotlin.collections.List

fun <T> List<T>.toKotlin(): KotlinList<T> = this.asKotlin().toList()

fun <T> MutatingListView<T>.toKotlin(): KotlinList<T> = this.asKotlin()

fun <T> MutableList<T>.toKotlin(): KotlinMutableList<T> = this.asKotlin().toMutableList()

// Set -> kotlin.collections.Set

fun <T> Set<T>.toKotlin(): KotlinSet<T> = this.asKotlin().toSet()

fun <T> MutatingSetView<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> RemoveOnlySet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin()

fun <T> MutableSet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin().toMutableSet()

// SequencedSet -> kotlin.collections.Set

fun <T> SequencedSet<T>.toKotlin(): KotlinSet<T> = this.asKotlin().toSet()

fun <T> MutatingSequencedSetView<T>.toKotlin(): KotlinSet<T> = this.asKotlin()

fun <T> RemoveOnlySequencedSet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin()

fun <T> MutableSequencedSet<T>.toKotlin(): KotlinMutableSet<T> = this.asKotlin().toMutableSet()

// Map -> kotlin.collections.Map

fun <K, V> Map<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin().toMap()

fun <K, V> MutatingMapView<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin()

fun <K, V> MutableMap<K, V>.toKotlin(): KotlinMutableMap<K, V> = this.asKotlin().toMutableMap()

// SequencedMap -> kotlin.collections.Map

fun <K, V> SequencedMap<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin().toMap()

fun <K, V> MutatingSequencedMapView<K, V>.toKotlin(): KotlinMap<K, V> = this.asKotlin()

fun <K, V> MutableSequencedMap<K, V>.toKotlin(): KotlinMutableMap<K, V> = this.asKotlin().toMutableMap()
