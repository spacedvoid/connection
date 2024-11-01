package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Wrapper
import io.github.spacedvoid.connection.characteristic.WrapperImpl

fun <T> MutableCollection<T>.asRemoveOnly(): RemoveOnlyCollection<T> =
	object: RemoveOnlyCollection<T> by this, Wrapper<kotlin.collections.MutableCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableSequencedCollection<T>.asRemoveOnly(): RemoveOnlySequencedCollection<T> =
	object: RemoveOnlySequencedCollection<T> by this, Wrapper<java.util.SequencedCollection<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableSet<T>.asRemoveOnly(): RemoveOnlySet<T> =
	object: RemoveOnlySet<T> by this, Wrapper<kotlin.collections.MutableSet<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableSequencedSet<T>.asRemoveOnly(): RemoveOnlySequencedSet<T> =
	object: RemoveOnlySequencedSet<T> by this, Wrapper<java.util.SequencedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableSortedNavigableSet<T>.asRemoveOnly(): RemoveOnlySortedNavigableSet<T> =
	object: RemoveOnlySortedNavigableSet<T> by this, Wrapper<java.util.SortedSet<T>> by WrapperImpl(this.origin()) {}

fun <T> MutableNavigableSet<T>.asRemoveOnly(): RemoveOnlyNavigableSet<T> =
	object: RemoveOnlyNavigableSet<T> by this, Wrapper<java.util.NavigableSet<T>> by WrapperImpl(this.origin()) {}
