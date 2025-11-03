/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class LinkedSetImpl<T>(override val kotlin: LinkedHashSet<T>): LinkedSet<T>, MutableSequencedSetImpl<T>(kotlin) {
	private val reversed by lazy(LazyThreadSafetyMode.PUBLICATION) {
		val reverse = this@LinkedSetImpl.kotlin.reversed()
		return@lazy object: LinkedSet<T>, MutableSequencedSetImpl<T>(reverse) {
			override fun reversed(): LinkedSet<T> = this@LinkedSetImpl

			override fun addFirst(element: T) = this@LinkedSetImpl.addLast(element)

			override fun addAllFirst(collection: CollectionView<T>) = this@LinkedSetImpl.addAllLast(collection)
		}
	}

	override fun reversed(): LinkedSet<T> = this.reversed

	override fun addFirst(element: T) = this.kotlin.addFirst(element)

	override fun addAllFirst(collection: CollectionView<T>) {
		for(e in collection) addFirst(e)
	}
}
