/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableSet
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.MapViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl
import java.util.SequencedMap

@Suppress("UNCHECKED_CAST")
open class KotlinSequencedMapImpl<K, V>(private val connection: SequencedMapView<K, V>): KotlinMapImpl<K, V>(connection), SequencedMap<K, V> {
	override fun reversed(): SequencedMap<K, V> = KotlinSequencedMapImpl(this.connection.reversed())

	override fun put(key: K, value: V): V? =
		if(this.connection is MutableSequencedMap<K, V>) this.connection.put(key, value) else throw UnsupportedOperationException("put(K, V)")

	override fun putAll(from: Map<out K, V>) =
		if(this.connection is MutableSequencedMap<K, V>) this.connection.putAll(MapViewImpl(from)) else throw UnsupportedOperationException("putAll(kotlin.collections.Map)")

	override fun remove(key: K): V? =
		if(this.connection is MutableSequencedMap<K, V>) this.connection.remove(key) else throw UnsupportedOperationException("remove(K)")

	override fun clear() =
		if(this.connection is MutableSequencedMap<K, V>) this.connection.clear() else throw UnsupportedOperationException("clear")

	override val keys: kotlin.collections.MutableSet<K> = ConditionallyMutableSet(this.connection.keys as SetView<K>)

	override val values: MutableCollection<V> = ConditionallyMutableCollection(this.connection.values as CollectionView<V>)

	override val entries: kotlin.collections.MutableSet<MutableMap.MutableEntry<K, V>> = ConditionallyMutableSet(this.connection.entries as SetView<MutableMap.MutableEntry<K, V>>)
}

private class ConditionallyMutableSet<T>(connection: SetView<T>): ConditionallyMutableCollection<T>(connection), kotlin.collections.MutableSet<T>

private open class ConditionallyMutableCollection<T>(private val connection: CollectionView<T>): KotlinCollectionImpl<T>(connection), MutableCollection<T> {
	override fun iterator(): MutableIterator<T> = this.connection.conditionalIterator()

	override fun add(element: T): Boolean =
		if(this.connection is MutableSet<T>) this.connection.add(element) else throw UnsupportedOperationException("add(T)")

	override fun addAll(elements: Collection<T>): Boolean =
		if(this.connection is MutableSet<T>) this.connection.addAll(CollectionViewImpl(elements)) else throw UnsupportedOperationException("addAll(kotlin.collections.Collection)")

	override fun remove(element: T): Boolean =
		if(this.connection is RemoveOnlySet<T>) this.connection.remove(element) else throw UnsupportedOperationException("remove(T)")

	override fun removeAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySet<T>) this.connection.removeAll(SetViewImpl(elements.toSet())) else throw UnsupportedOperationException("removeAll(kotlin.collections.Collection)")

	override fun retainAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySet<T>) this.connection.retainAll(SetViewImpl(elements.toSet())) else throw UnsupportedOperationException("retainAll(kotlin.collections.Collection)")

	override fun clear() =
		if(this.connection is RemoveOnlySet<T>) this.connection.clear() else throw UnsupportedOperationException("clear")
}
