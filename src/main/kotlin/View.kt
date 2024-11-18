package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.ListViewImpl
import io.github.spacedvoid.connection.impl.MapViewImpl
import io.github.spacedvoid.connection.impl.NavigableMapViewImpl
import io.github.spacedvoid.connection.impl.NavigableSetViewImpl
import io.github.spacedvoid.connection.impl.SequencedCollectionViewImpl
import io.github.spacedvoid.connection.impl.SequencedMapViewImpl
import io.github.spacedvoid.connection.impl.SequencedSetViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl
import io.github.spacedvoid.connection.impl.SortedNavigableMapViewImpl
import io.github.spacedvoid.connection.impl.SortedNavigableSetViewImpl

fun <T> CollectionView<T>.asView(): CollectionView<T> = CollectionViewImpl(this.kotlin)

fun <T> SequencedCollectionView<T>.asView(): SequencedCollectionView<T> = SequencedCollectionViewImpl(this.kotlin)

fun <T> ListView<T>.asView(): ListView<T> = ListViewImpl(this.kotlin)

fun <T> SetView<T>.asView(): SetView<T> = SetViewImpl(this.kotlin)

fun <T> SequencedSetView<T>.asView(): SequencedSetView<T> = SequencedSetViewImpl(this.kotlin)

fun <T> SortedNavigableSetView<T>.asView(): SortedNavigableSetView<T> = SortedNavigableSetViewImpl(this.kotlin)

fun <T> NavigableSetView<T>.asView(): NavigableSetView<T> = NavigableSetViewImpl(this.kotlin)

fun <K, V> MapView<K, V>.asView(): MapView<K, V> = MapViewImpl(this.kotlin)

fun <K, V> SequencedMapView<K, V>.asView(): SequencedMapView<K, V> = SequencedMapViewImpl(this.kotlin)

fun <K, V> SortedNavigableMapView<K, V>.asView(): SortedNavigableMapView<K, V> = SortedNavigableMapViewImpl(this.kotlin)

fun <K, V> NavigableMapView<K, V>.asView(): NavigableMapView<K, V> = NavigableMapViewImpl(this.kotlin)
