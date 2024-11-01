package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl
import kotlin.collections.MutableCollection
import kotlin.collections.MutableSet

fun <T> RemoveOnlyCollection<T>.asView(): MutatingCollectionView<T> =
	object: MutatingCollectionView<T> by this, Wrapper<MutableCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedCollection<T>.asView(): MutatingSequencedCollectionView<T> =
	object: MutatingSequencedCollectionView<T> by this, Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableList<T>.asView(): MutatingListView<T> =
	object: MutatingListView<T> by this, Wrapper<kotlin.collections.MutableList<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySet<T>.asView(): MutatingSetView<T> =
	object: MutatingSetView<T> by this, Wrapper<MutableSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedSet<T>.asView(): MutatingSequencedSetView<T> =
	object: MutatingSequencedSetView<T> by this, Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySortedNavigableSet<T>.asView(): MutatingSortedNavigableSetView<T> =
	object: MutatingSortedNavigableSetView<T> by this, Wrapper<java.util.SortedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlyNavigableSet<T>.asView(): MutatingNavigableSetView<T> =
	object: MutatingNavigableSetView<T> by this, Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableMap<K, V>.asView(): MutatingMapView<K, V> =
	object: MutatingMapView<K, V> by this, Wrapper<kotlin.collections.MutableMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableSequencedMap<K, V>.asView(): MutatingSequencedMapView<K, V> =
	object: MutatingSequencedMapView<K, V> by this, Wrapper<java.util.SequencedMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableSortedNavigableMap<K, V>.asView(): MutatingSortedNavigableMapView<K, V> =
	object: MutatingSortedNavigableMapView<K, V> by this, Wrapper<java.util.SortedMap<K, V>> by WrapperImpl(this.origin()) {}

fun <K, V> MutableNavigableMap<K, V>.asView(): MutatingNavigableMapView<K, V> =
	object: MutatingNavigableMapView<K, V> by this, Wrapper<java.util.NavigableMap<K, V>> by WrapperImpl(this.origin()) {}
