/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*

open class KotlinNavigableSetImpl<T>(private val connection: NavigableSetView<T>): KotlinSequencedSetImpl<T>(connection), java.util.NavigableSet<T> {
	override fun reversed(): java.util.NavigableSet<T> = KotlinNavigableSetImpl(this.connection.reversed())

	override fun lower(e: T): T? = this.connection.lower(e, false)

	override fun floor(e: T): T? = this.connection.lower(e, true)

	override fun ceiling(e: T): T? = this.connection.higher(e, true)

	override fun higher(e: T): T? = this.connection.higher(e, false)

	override fun pollFirst(): T? =
		if(this.connection is RemoveOnlyNavigableSet<T>) this.connection.removeFirst() else throw UnsupportedOperationException("pollFirst")

	override fun pollLast(): T? =
		if(this.connection is RemoveOnlyNavigableSet<T>) this.connection.removeLast() else throw UnsupportedOperationException("pollLast")

	override fun descendingSet(): java.util.NavigableSet<T> = reversed()

	override fun descendingIterator(): MutableIterator<T> = descendingSet().iterator()

	override fun subSet(fromElement: T, fromInclusive: Boolean, toElement: T, toInclusive: Boolean): java.util.NavigableSet<T> =
		KotlinNavigableSetImpl(this.connection.subSet(fromElement, toElement, fromInclusive, toInclusive))

	override fun headSet(toElement: T, inclusive: Boolean): java.util.NavigableSet<T> =
		KotlinNavigableSetImpl(this.connection.headSet(toElement, inclusive))

	override fun tailSet(fromElement: T, inclusive: Boolean): java.util.NavigableSet<T> =
		KotlinNavigableSetImpl(this.connection.tailSet(fromElement, inclusive))

	override fun subSet(fromElement: T, toElement: T): java.util.NavigableSet<T> = subSet(fromElement, true, toElement, false)

	override fun headSet(toElement: T): java.util.NavigableSet<T> = headSet(toElement, false)

	override fun tailSet(fromElement: T): java.util.NavigableSet<T> = tailSet(fromElement, true)

	override fun comparator(): Comparator<in T> = this.connection.comparator

	override fun first(): T? = this.connection.first()

	override fun last(): T? = this.connection.last()
}
