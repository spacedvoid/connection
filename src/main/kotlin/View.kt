package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl
import kotlin.collections.MutableCollection
import kotlin.collections.MutableSet

fun <T> RemoveOnlyCollection<T>.asView(): CollectionView<T> =
	object: CollectionView<T> by this, Wrapper<MutableCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedCollection<T>.asView(): SequencedCollectionView<T> =
	object: SequencedCollectionView<T> by this, Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableList<T>.asView(): ListView<T> =
	object: ListView<T> by this, Wrapper<kotlin.collections.MutableList<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySet<T>.asView(): SetView<T> =
	object: SetView<T> by this, Wrapper<MutableSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedSet<T>.asView(): SequencedSetView<T> =
	object: SequencedSetView<T> by this, Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySortedNavigableSet<T>.asView(): SortedNavigableSetView<T> =
	object: SortedNavigableSetView<T> by this, Wrapper<java.util.SortedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlyNavigableSet<T>.asView(): NavigableSetView<T> =
	object: NavigableSetView<T> by this, Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableMap<K, V>.asView(): MapView<K, V> =
	object: MapView<K, V> by this, Wrapper<kotlin.collections.MutableMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableSequencedMap<K, V>.asView(): SequencedMapView<K, V> =
	object: SequencedMapView<K, V> by this, Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableSortedNavigableMap<K, V>.asView(): SortedNavigableMapView<K, V> =
	object: SortedNavigableMapView<K, V> by this, Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableNavigableMap<K, V>.asView(): NavigableMapView<K, V> =
	object: NavigableMapView<K, V> by this, Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this.origin()) {}
