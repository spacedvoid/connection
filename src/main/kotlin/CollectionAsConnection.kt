package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.CollectionImpl
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.ListImpl
import io.github.spacedvoid.connection.impl.ListViewImpl
import io.github.spacedvoid.connection.impl.MapImpl
import io.github.spacedvoid.connection.impl.MapViewImpl
import io.github.spacedvoid.connection.impl.MutableCollectionImpl
import io.github.spacedvoid.connection.impl.MutableListImpl
import io.github.spacedvoid.connection.impl.MutableMapImpl
import io.github.spacedvoid.connection.impl.MutableNavigableMapImpl
import io.github.spacedvoid.connection.impl.MutableNavigableSetImpl
import io.github.spacedvoid.connection.impl.MutableSequencedCollectionImpl
import io.github.spacedvoid.connection.impl.MutableSequencedMapImpl
import io.github.spacedvoid.connection.impl.MutableSequencedSetImpl
import io.github.spacedvoid.connection.impl.MutableSetImpl
import io.github.spacedvoid.connection.impl.MutableSortedNavigableMapImpl
import io.github.spacedvoid.connection.impl.MutableSortedNavigableSetImpl
import io.github.spacedvoid.connection.impl.NavigableMapImpl
import io.github.spacedvoid.connection.impl.NavigableMapViewImpl
import io.github.spacedvoid.connection.impl.NavigableSetImpl
import io.github.spacedvoid.connection.impl.NavigableSetViewImpl
import io.github.spacedvoid.connection.impl.RemoveOnlyCollectionImpl
import io.github.spacedvoid.connection.impl.RemoveOnlyNavigableSetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySequencedCollectionImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySequencedSetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySortedNavigableSetImpl
import io.github.spacedvoid.connection.impl.SequencedCollectionImpl
import io.github.spacedvoid.connection.impl.SequencedCollectionViewImpl
import io.github.spacedvoid.connection.impl.SequencedMapImpl
import io.github.spacedvoid.connection.impl.SequencedMapViewImpl
import io.github.spacedvoid.connection.impl.SequencedSetImpl
import io.github.spacedvoid.connection.impl.SequencedSetViewImpl
import io.github.spacedvoid.connection.impl.SetImpl
import io.github.spacedvoid.connection.impl.SetViewImpl
import io.github.spacedvoid.connection.impl.SortedNavigableMapImpl
import io.github.spacedvoid.connection.impl.SortedNavigableMapViewImpl
import io.github.spacedvoid.connection.impl.SortedNavigableSetImpl
import io.github.spacedvoid.connection.impl.SortedNavigableSetViewImpl
import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.List as KotlinList
import kotlin.collections.Map as KotlinMap
import kotlin.collections.MutableCollection as KotlinMutableCollection
import kotlin.collections.MutableList as KotlinMutableList
import kotlin.collections.MutableMap as KotlinMutableMap
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

// kotlin.collections.Collection -> Collection

fun <T> KotlinCollection<T>.asConnection(): Collection<T> = CollectionImpl(this)

fun <T> KotlinCollection<T>.asViewConnection(): CollectionView<T> = CollectionViewImpl(this)

fun <T> KotlinMutableCollection<T>.asRemoveOnlyConnection(): RemoveOnlyCollection<T> = RemoveOnlyCollectionImpl(this)

fun <T> KotlinMutableCollection<T>.asConnection(): MutableCollection<T> = MutableCollectionImpl(this)

// java.util.SequencedCollection -> SequencedCollection

fun <T> java.util.SequencedCollection<T>.asConnection(): SequencedCollection<T> = SequencedCollectionImpl(this)

fun <T> java.util.SequencedCollection<T>.asViewConnection(): SequencedCollectionView<T> = SequencedCollectionViewImpl(this)

fun <T> java.util.SequencedCollection<T>.asRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = RemoveOnlySequencedCollectionImpl(this)

fun <T> java.util.SequencedCollection<T>.asMutableConnection(): MutableSequencedCollection<T> = MutableSequencedCollectionImpl(this)

// kotlin.collections.List -> SequencedCollection

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asSequencedConnection(): SequencedCollection<T> = SequencedCollectionImpl(this as java.util.SequencedCollection<T>)

@Suppress("UNCHECKED_CAST")
fun <T> KotlinList<T>.asSequencedViewConnection(): SequencedCollectionView<T> = SequencedCollectionViewImpl(this as java.util.SequencedCollection<T>)

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asSequencedRemoveOnlyConnection(): RemoveOnlySequencedCollection<T> = RemoveOnlySequencedCollectionImpl(this as java.util.SequencedCollection<T>)

@Suppress("UNCHECKED_CAST")
fun <T> KotlinMutableList<T>.asSequencedConnection(): MutableSequencedCollection<T> = MutableSequencedCollectionImpl(this as java.util.SequencedCollection<T>)

// kotlin.collections.List -> List

fun <T> KotlinList<T>.asConnection(): List<T> = ListImpl(this)

fun <T> KotlinList<T>.asViewConnection(): ListView<T> = ListViewImpl(this)

fun <T> KotlinMutableList<T>.asConnection(): MutableList<T> = MutableListImpl(this)

// kotlin.collections.Set -> Set

fun <T> KotlinSet<T>.asConnection(): Set<T> = SetImpl(this)

fun <T> KotlinSet<T>.asViewConnection(): SetView<T> = SetViewImpl(this)

fun <T> KotlinMutableSet<T>.asRemoveOnlyConnection(): RemoveOnlySet<T> = RemoveOnlySetImpl(this)

fun <T> KotlinMutableSet<T>.asConnection(): MutableSet<T> = MutableSetImpl(this)

// java.util.SequencedSet -> SequencedSet

fun <T> java.util.SequencedSet<T>.asConnection(): SequencedSet<T> = SequencedSetImpl(this)

fun <T> java.util.SequencedSet<T>.asViewConnection(): SequencedSetView<T> = SequencedSetViewImpl(this)

fun <T> java.util.SequencedSet<T>.asRemoveOnlyConnection(): RemoveOnlySequencedSet<T> = RemoveOnlySequencedSetImpl(this)

fun <T> java.util.SequencedSet<T>.asMutableConnection(): MutableSequencedSet<T> = MutableSequencedSetImpl(this)

// java.util.SortedSet -> SortedNavigableSet

fun <T> java.util.SortedSet<T>.asConnection(): SortedNavigableSet<T> = SortedNavigableSetImpl(this)

fun <T> java.util.SortedSet<T>.asViewConnection(): SortedNavigableSetView<T> = SortedNavigableSetViewImpl(this)

fun <T> java.util.SortedSet<T>.asRemoveOnlyConnection(): RemoveOnlySortedNavigableSet<T> = RemoveOnlySortedNavigableSetImpl(this)

fun <T> java.util.SortedSet<T>.asMutableConnection(): MutableSortedNavigableSet<T> = MutableSortedNavigableSetImpl(this)

// java.util.NavigableSet -> NavigableSet

fun <T> java.util.NavigableSet<T>.asConnection(): NavigableSet<T> = NavigableSetImpl(this)

fun <T> java.util.NavigableSet<T>.asViewConnection(): NavigableSetView<T> = NavigableSetViewImpl(this)

fun <T> java.util.NavigableSet<T>.asRemoveOnlyConnection(): RemoveOnlyNavigableSet<T> = RemoveOnlyNavigableSetImpl(this)

fun <T> java.util.NavigableSet<T>.asMutableConnection(): MutableNavigableSet<T> = MutableNavigableSetImpl(this)

// kotlin.collections.Map -> Map

fun <K, V> KotlinMap<K, V>.asConnection(): Map<K, V> = MapImpl(this)

fun <K, V> KotlinMap<K, V>.asViewConnection(): MapView<K, V> = MapViewImpl(this)

fun <K, V> KotlinMutableMap<K, V>.asConnection(): MutableMap<K, V> = MutableMapImpl(this)

// java.util.SequencedMap -> SequencedMap

fun <K, V> java.util.SequencedMap<K, V>.asConnection(): SequencedMap<K, V> = SequencedMapImpl(this)

fun <K, V> java.util.SequencedMap<K, V>.asViewConnection(): SequencedMapView<K, V> = SequencedMapViewImpl(this)

fun <K, V> java.util.SequencedMap<K, V>.asMutableConnection(): MutableSequencedMap<K, V> = MutableSequencedMapImpl(this)

// java.util.SortedMap -> SortedNavigableMap

fun <K, V> java.util.SortedMap<K, V>.asConnection(): SortedNavigableMap<K, V> = SortedNavigableMapImpl(this)

fun <K, V> java.util.SortedMap<K, V>.asViewConnection(): SortedNavigableMapView<K, V> = SortedNavigableMapViewImpl(this)

fun <K, V> java.util.SortedMap<K, V>.asMutableConnection(): MutableSortedNavigableMap<K, V> = MutableSortedNavigableMapImpl(this)

// java.util.NavigableMap -> NavigableMap

fun <K, V> java.util.NavigableMap<K, V>.asConnection(): NavigableMap<K, V> = NavigableMapImpl(this)

fun <K, V> java.util.NavigableMap<K, V>.asViewConnection(): NavigableMapView<K, V> = NavigableMapViewImpl(this)

fun <K, V> java.util.NavigableMap<K, V>.asMutableConnection(): MutableNavigableMap<K, V> = MutableNavigableMapImpl(this)
