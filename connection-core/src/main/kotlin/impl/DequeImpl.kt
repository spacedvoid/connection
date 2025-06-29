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

	override fun addAllFirst(collection: CollectionView<out T>): Boolean {
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
}
