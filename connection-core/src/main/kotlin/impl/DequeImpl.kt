/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class DequeImpl<T>(override val kotlin: java.util.Deque<T>): Deque<T>, MutableSequencedCollectionImpl<T>(kotlin) {
	override fun reversed(): DequeImpl<T> = DequeImpl(this.kotlin.reversed())

	override fun peekFirst(): T? = this.kotlin.peekFirst()

	override fun peekLast(): T? = this.kotlin.peekLast()

	override fun addFirst(element: T) = this.kotlin.addFirst(element)

	override fun addAllFirst(collection: CollectionView<T>): Boolean {
		var result = false
		for(e in collection) {
			addFirst(e)
			result = true
		}
		return result
	}

	override fun pollFirst(): T? = this.kotlin.pollFirst()

	override fun pollLast(): T? = this.kotlin.pollLast()

	override fun removeLast(element: T): Boolean = this.kotlin.removeLastOccurrence(element)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is Deque<*>) return false
		val thisIterator = iterator()
		val otherIterator = other.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() && otherIterator.hasNext())
	}

	/**
	 * Returns a hash code for this deque.
	 *
	 * The hash is computed based on the contained objects' hash codes, by their iteration order.
	 *
	 * This implementation uses the same way from [java.util.AbstractList.hashCode]
	 */
	override fun hashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
}
