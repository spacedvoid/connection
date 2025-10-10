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

	override fun equals(other: Any?): Boolean = listLikeEquals<Stack<*>>(other)

	override fun hashCode(): Int = listLikeHashCode()
}
