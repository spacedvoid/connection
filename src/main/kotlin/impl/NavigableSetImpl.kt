package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.CollectionView
import io.github.spacedvoid.connection.MutableNavigableSet
import io.github.spacedvoid.connection.NavigableSet
import io.github.spacedvoid.connection.NavigableSetView
import io.github.spacedvoid.connection.RemoveOnlyNavigableSet

open class NavigableSetViewImpl<T>(private val kotlin: java.util.NavigableSet<T>): SortedNavigableSetViewImpl<T>(kotlin), NavigableSetView<T> {
	override fun reverse(): NavigableSetView<T> = NavigableSetViewImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.tailSet(after, inclusive))

	override val CollectionView<T>.kotlin: java.util.NavigableSet<T>
		get() = this@NavigableSetViewImpl.kotlin
}

open class NavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): NavigableSetViewImpl<T>(kotlin), NavigableSet<T> {
	override fun reverse(): NavigableSet<T> = NavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}

open class RemoveOnlyNavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): NavigableSetViewImpl<T>(kotlin), RemoveOnlyNavigableSet<T> {
	override fun reverse(): RemoveOnlyNavigableSet<T> = RemoveOnlyNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}

open class MutableNavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): RemoveOnlyNavigableSetImpl<T>(kotlin), MutableNavigableSet<T> {
	override fun reverse(): MutableNavigableSet<T> = MutableNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}
