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
}
