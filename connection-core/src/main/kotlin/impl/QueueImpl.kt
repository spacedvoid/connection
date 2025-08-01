/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class QueueImpl<T>(override val kotlin: java.util.Queue<T>): Queue<T>, MutableCollectionImpl<T>(kotlin) {
	override fun element(): T = this.kotlin.element()

	override fun peek(): T? = this.kotlin.peek()

	override fun remove(): T = this.kotlin.remove()

	override fun poll(): T? = this.kotlin.poll()

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is Queue<*>) return false
		val thisIterator = iterator()
		val otherIterator = other.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() && otherIterator.hasNext())
	}

	/**
	 * Returns a hash code for this queue.
	 *
	 * The hash is computed based on the contained objects' hash codes, by their iteration order.
	 *
	 * This implementation uses the same way from [java.util.AbstractList.hashCode]
	 */
	override fun hashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
}
