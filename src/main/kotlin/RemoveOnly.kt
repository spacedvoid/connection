package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.impl.RemoveOnlyCollectionImpl
import io.github.spacedvoid.connection.impl.RemoveOnlyNavigableSetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySequencedCollectionImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySequencedSetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySetImpl
import io.github.spacedvoid.connection.impl.RemoveOnlySortedNavigableSetImpl

fun <T> RemoveOnlyCollection<T>.asRemoveOnly(): RemoveOnlyCollection<T> = RemoveOnlyCollectionImpl(this.kotlin)

fun <T> RemoveOnlySequencedCollection<T>.asRemoveOnly(): RemoveOnlySequencedCollection<T> = RemoveOnlySequencedCollectionImpl(this.kotlin)

fun <T> RemoveOnlySet<T>.asRemoveOnly(): RemoveOnlySet<T> = RemoveOnlySetImpl(this.kotlin)

fun <T> RemoveOnlySequencedSet<T>.asRemoveOnly(): RemoveOnlySequencedSet<T> = RemoveOnlySequencedSetImpl(this.kotlin)

fun <T> RemoveOnlySortedNavigableSet<T>.asRemoveOnly(): RemoveOnlySortedNavigableSet<T> = RemoveOnlySortedNavigableSetImpl(this.kotlin)

fun <T> RemoveOnlyNavigableSet<T>.asRemoveOnly(): RemoveOnlyNavigableSet<T> = RemoveOnlyNavigableSetImpl(this.kotlin)
