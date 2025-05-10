/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableMap
import io.github.spacedvoid.connection.impl.MapViewImpl

open class KotlinMapImpl<K, V>(private val connection: MapView<K, V>): Map<K, V> {
	override val size: Int
		get() = this.connection.size()

	override fun isEmpty(): Boolean = this.connection.isEmpty()

	override fun containsKey(key: K): Boolean = this.connection.containsKey(key)

	override fun containsValue(value: V): Boolean = this.connection.containsValue(value)

	override fun get(key: K): V? = this.connection[key]

	override val keys: Set<K> = KotlinSetImpl(this.connection.keys)

	override val values: Collection<V> = KotlinCollectionImpl(this.connection.values)

	override val entries: Set<Map.Entry<K, V>> = KotlinSetImpl(this.connection.entries)
}

open class KotlinMutableMapImpl<K, V>(private val connection: MutableMap<K, V>): KotlinMapImpl<K, V>(connection), kotlin.collections.MutableMap<K, V> {
	override fun put(key: K, value: V): V? = this.connection.put(key, value)

	override fun putAll(from: Map<out K, V>) = this.connection.putAll(MapViewImpl(from))

	override fun remove(key: K): V? = this.connection.remove(key)

	override fun clear() = this.connection.clear()

	override val keys: MutableSet<K> = KotlinMutableSetImpl(this.connection.keys)

	override val values: MutableCollection<V> = KotlinMutableCollectionImpl(this.connection.values)

	override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = KotlinMutableSetImpl(this.connection.entries)
}
