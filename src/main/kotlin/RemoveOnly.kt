package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl

fun <T> RemoveOnlyCollection<T>.asRemoveOnly(): RemoveOnlyCollection<T> =
	object: RemoveOnlyCollection<T> by this, Wrapper<kotlin.collections.MutableCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedCollection<T>.asRemoveOnly(): RemoveOnlySequencedCollection<T> =
	object: RemoveOnlySequencedCollection<T> by this, Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySet<T>.asRemoveOnly(): RemoveOnlySet<T> =
	object: RemoveOnlySet<T> by this, Wrapper<kotlin.collections.MutableSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySequencedSet<T>.asRemoveOnly(): RemoveOnlySequencedSet<T> =
	object: RemoveOnlySequencedSet<T> by this, Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlySortedNavigableSet<T>.asRemoveOnly(): RemoveOnlySortedNavigableSet<T> =
	object: RemoveOnlySortedNavigableSet<T> by this, Wrapper<java.util.SortedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> RemoveOnlyNavigableSet<T>.asRemoveOnly(): RemoveOnlyNavigableSet<T> =
	object: RemoveOnlyNavigableSet<T> by this, Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this.origin()) {}
