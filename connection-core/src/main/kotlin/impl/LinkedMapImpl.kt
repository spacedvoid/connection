/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

class LinkedMapImpl<K, V>(override val kotlin: LinkedHashMap<K, V>): LinkedMap<K, V>, MutableSequencedMapImpl<K, V>(kotlin) {
	private val reversed by lazy {
		val reverse = this@LinkedMapImpl.kotlin.reversed()
		return@lazy object: LinkedMap<K, V>, MutableSequencedMapImpl<K, V>(reverse) {
			override fun reversed(): LinkedMap<K, V> = this@LinkedMapImpl

			override fun putFirst(key: K, value: V): V? = this@LinkedMapImpl.putLast(key, value)

			override fun putAllFirst(map: MapView<out K, V>) = this@LinkedMapImpl.putAllLast(map)
		}
	}

	override fun reversed(): LinkedMap<K, V> = this.reversed

	override fun putFirst(key: K, value: V): V? = this.kotlin.putFirst(key, value)

	override fun putAllFirst(map: MapView<out K, V>) {
		for(e in map.entries) this.kotlin.putFirst(e.key, e.value)
	}
}
