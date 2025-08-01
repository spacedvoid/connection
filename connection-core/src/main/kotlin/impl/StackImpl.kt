/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class StackImpl<T>(override val kotlin: java.util.Deque<T>): Stack<T>, MutableCollectionImpl<T>(kotlin) {
	override fun top(): T = this.kotlin.first

	override fun peek(): T? = this.kotlin.peek()

	override fun pop(): T = this.kotlin.pop()

	override fun poll(): T? = this.kotlin.poll()

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is Stack<*>) return false
		val thisIterator = iterator()
		val otherIterator = other.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() && otherIterator.hasNext())
	}

	/**
	 * Returns a hash code for this stack.
	 *
	 * The hash is computed based on the contained objects' hash codes, by their iteration order.
	 *
	 * This implementation uses the same way from [java.util.AbstractList.hashCode]
	 */
	override fun hashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
}
