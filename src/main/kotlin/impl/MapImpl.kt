/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.Map
import io.github.spacedvoid.connection.MutableMap
import io.github.spacedvoid.connection.Set

open class MapViewImpl<K, V>(private val kotlin: KotlinMap<K, V>): MapView<K, V> {
	override val keys: SetView<out K> = SetViewImpl(this.kotlin.keys)

	override val values: CollectionView<out V> = CollectionViewImpl(this.kotlin.values)

	override val entries: SetView<out KotlinMapEntry<K, V>> = SetViewImpl(this.kotlin.entries)

	override val MapView<K, V>.kotlin: KotlinMap<K, V>
		get() = this@MapViewImpl.kotlin

}

open class MapImpl<K, V>(private val kotlin: KotlinMap<K, V>): MapViewImpl<K, V>(kotlin), Map<K, V> {
	override val keys: Set<out K> = SetImpl(this.kotlin.keys)

	override val values: Collection<out V> = CollectionImpl(this.kotlin.values)

	override val entries: Set<out KotlinMapEntry<K, V>> = SetImpl(this.kotlin.entries)

	override val MapView<K, V>.kotlin: KotlinMap<K, V>
		get() = this@MapImpl.kotlin

}

open class MutableMapImpl<K, V>(private val kotlin: KotlinMutableMap<K, V>): MapViewImpl<K, V>(kotlin), MutableMap<K, V> {
	override val keys: RemoveOnlySet<K> = RemoveOnlySetImpl(this.kotlin.keys)

	override val values: RemoveOnlyCollection<V> = RemoveOnlyCollectionImpl(this.kotlin.values)

	override val entries: RemoveOnlySet<KotlinMutableMapEntry<K, V>> = RemoveOnlySetImpl(this.kotlin.entries)

	override val MapView<K, V>.kotlin: KotlinMutableMap<K, V>
		get() = this@MutableMapImpl.kotlin
}
