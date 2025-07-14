/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl
import io.github.spacedvoid.connection.utils.naturalOrdering

open class NavigableSetViewImpl<T>(override val kotlin: java.util.NavigableSet<T>): NavigableSetView<T>, SequencedSetViewImpl<T>(kotlin) {
	override fun reversed(): NavigableSetView<T> = NavigableSetViewImpl(this.kotlin.reversed())

	override val comparator: Comparator<in T> = this.kotlin.comparator() ?: naturalOrdering()

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSetView<T> =
		NavigableSetViewImpl(this.kotlin.tailSet(after, inclusive))

	override fun higher(than: T, inclusive: Boolean): T? = if(inclusive) this.kotlin.ceiling(than) else this.kotlin.higher(than)

	override fun lower(than: T, inclusive: Boolean): T? = if(inclusive) this.kotlin.floor(than) else this.kotlin.lower(than)
}

open class NavigableSetImpl<T>(override val kotlin: java.util.NavigableSet<T>): NavigableSet<T>, NavigableSetViewImpl<T>(kotlin) {
	override fun reversed(): NavigableSet<T> = NavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): NavigableSet<T> =
		NavigableSetImpl(this.kotlin.tailSet(after, inclusive))
}

open class RemoveOnlyNavigableSetImpl<T>(override val kotlin: java.util.NavigableSet<T>): RemoveOnlyNavigableSet<T>, NavigableSetViewImpl<T>(kotlin) {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun reversed(): RemoveOnlyNavigableSet<T> = RemoveOnlyNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): RemoveOnlyNavigableSet<T> =
		RemoveOnlyNavigableSetImpl(this.kotlin.tailSet(after, inclusive))

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	@Deprecated("This method is replaced with an inline operation.", replaceWith = ReplaceWith("removeAll(predicate)", "io.github.spacedvoid.connection.removeAll"))
	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(KotlinCollectionImpl(collection))

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(KotlinCollectionImpl(collection))

	override fun clear() = this.kotlin.clear()
}

open class MutableNavigableSetImpl<T>(override val kotlin: java.util.NavigableSet<T>): MutableNavigableSet<T>, RemoveOnlyNavigableSetImpl<T>(kotlin) {
	override fun reversed(): MutableNavigableSet<T> = MutableNavigableSetImpl(this.kotlin.reversed())

	override fun subSet(from: T, to: T, fromInclusive: Boolean, toInclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.subSet(from, fromInclusive, to, toInclusive))

	override fun headSet(before: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.headSet(before, inclusive))

	override fun tailSet(after: T, inclusive: Boolean): MutableNavigableSet<T> =
		MutableNavigableSetImpl(this.kotlin.tailSet(after, inclusive))

	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))
}
