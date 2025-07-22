/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinMapImpl

open class SequencedMapViewImpl<K, out V>(override val kotlin: java.util.SequencedMap<K, @UnsafeVariance V>): SequencedMapView<K, V>, MapViewImpl<K, V>(kotlin) {
	override fun reversed(): SequencedMapView<K, V> = SequencedMapViewImpl(this.kotlin.reversed())

	override fun first(): Pair<K, V>? = this.kotlin.firstEntry()?.let { it.key to it.value }

	override fun last(): Pair<K, V>? = this.kotlin.lastEntry()?.let { it.key to it.value }

	override fun firstKey(): K = this.kotlin.firstEntry()?.key ?: throw NoSuchElementException("Map is empty")

	override fun lastKey(): K = this.kotlin.lastEntry()?.key ?: throw NoSuchElementException("Map is empty")

	override val keys: SequencedSetView<K> = SequencedSetViewImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollectionView<V> = SequencedCollectionViewImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSetView<Map.Entry<K, V>> = SequencedSetViewImpl(this.kotlin.sequencedEntrySet())
}

open class SequencedMapImpl<K, out V>(override val kotlin: java.util.SequencedMap<K, @UnsafeVariance V>): SequencedMap<K, V>, SequencedMapViewImpl<K, V>(kotlin) {
	override fun reversed(): SequencedMap<K, V> = SequencedMapImpl(this.kotlin.reversed())

	override val keys: SequencedSet<K> = SequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollection<V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableSequencedMapImpl<K, V>(override val kotlin: java.util.SequencedMap<K, V>): MutableSequencedMap<K, V>, SequencedMapViewImpl<K, V>(kotlin) {
	override fun reversed(): MutableSequencedMap<K, V> = MutableSequencedMapImpl(this.kotlin.reversed())

	override fun removeFirst(): Pair<K, V>? = this.kotlin.pollFirstEntry()?.toPair()

	override fun removeLast(): Pair<K, V>? = this.kotlin.pollLastEntry()?.toPair()

	override fun put(key: K, value: V): V? = this.kotlin.put(key, value)

	override fun putAll(map: MapView<out K, V>) = this.kotlin.putAll(KotlinMapImpl(map))

	override fun remove(key: K): V? = this.kotlin.remove(key)

	override fun clear() = this.kotlin.clear()

	override val keys: MutableSequencedSet<K> = MutableSequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: MutableSequencedCollection<V> = MutableSequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: MutableSequencedSet<MutableMap.MutableEntry<K, V>> = MutableSequencedSetImpl(this.kotlin.sequencedEntrySet())
}
