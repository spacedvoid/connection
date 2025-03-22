/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class SequencedMapViewImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): MapViewImpl<K, V>(kotlin), SequencedMapView<K, V> {
	override fun reversed(): SequencedMapView<K, V> = SequencedMapViewImpl(this.kotlin.reversed())

	override val keys: SequencedSetView<out K> = SequencedSetViewImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollectionView<out V> = SequencedCollectionViewImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSetView<out Map.Entry<K, V>> = SequencedSetViewImpl(this.kotlin.sequencedEntrySet())

	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val MapView<K, V>.kotlin: java.util.SequencedMap<K, V>
		get() = this@SequencedMapViewImpl.kotlin
}

open class SequencedMapImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), SequencedMap<K, V> {
	override fun reversed(): SequencedMap<K, V> = SequencedMapImpl(this.kotlin.reversed())

	override val keys: SequencedSet<out K> = SequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: SequencedCollection<out V> = SequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: SequencedSet<out Map.Entry<K, V>> = SequencedSetImpl(this.kotlin.sequencedEntrySet())
}

open class MutableSequencedMapImpl<K, V>(private val kotlin: java.util.SequencedMap<K, V>): SequencedMapViewImpl<K, V>(kotlin), MutableSequencedMap<K, V> {
	override fun reversed(): MutableSequencedMap<K, V> = MutableSequencedMapImpl(this.kotlin.reversed())

	override val keys: RemoveOnlySequencedSet<K> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedKeySet())

	override val values: RemoveOnlySequencedCollection<V> = RemoveOnlySequencedCollectionImpl(this.kotlin.sequencedValues())

	override val entries: RemoveOnlySequencedSet<MutableMap.MutableEntry<K, V>> = RemoveOnlySequencedSetImpl(this.kotlin.sequencedEntrySet())
}
