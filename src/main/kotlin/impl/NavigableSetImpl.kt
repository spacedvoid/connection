/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.utils.naturalOrdering

open class NavigableSetViewImpl<T>(private val kotlin: java.util.NavigableSet<T>): SequencedSetViewImpl<T>(kotlin), NavigableSetView<T> {
	override val comparator: Comparator<in T> = this.kotlin.comparator() ?: naturalOrdering()

	override fun reversed(): NavigableSetView<T> = NavigableSetViewImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.tailSet(after, inclusive))

	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val CollectionView<T>.kotlin: java.util.NavigableSet<T>
		get() = this@NavigableSetViewImpl.kotlin
}

open class NavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): NavigableSetViewImpl<T>(kotlin), NavigableSet<T> {
	override fun reversed(): NavigableSet<T> = NavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}

open class RemoveOnlyNavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): NavigableSetViewImpl<T>(kotlin), RemoveOnlyNavigableSet<T> {
	override fun reversed(): RemoveOnlyNavigableSet<T> = RemoveOnlyNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}

open class MutableNavigableSetImpl<T>(private val kotlin: java.util.NavigableSet<T>): RemoveOnlyNavigableSetImpl<T>(kotlin), MutableNavigableSet<T> {
	override fun reversed(): MutableNavigableSet<T> = MutableNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}
