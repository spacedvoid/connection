/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl

open class KotlinSequencedCollectionImpl<T>(private val connection: SequencedCollectionView<T>): KotlinCollectionImpl<T>(connection), java.util.SequencedCollection<T> {
	/**
	 * Returns an iterator over the elements of this object.
	 *
	 * **Warning:** Do not assume mutability of iterators from non-Kotlin collections.
	 * Treat them as if they were [java.util.Iterator]; they are mutable if the source collection is mutable.
	 * Mutable iterators do **not** imply the source collection is mutable.
	 */
	override fun iterator(): MutableIterator<T> = this.connection.conditionalIterator()

	override fun reversed(): java.util.SequencedCollection<T> = KotlinSequencedCollectionImpl(this.connection.reversed())

	override fun add(element: T): Boolean =
		if(this.connection is MutableSequencedCollection<T>) this.connection.add(element) else throw UnsupportedOperationException("add(T)")

	override fun addAll(elements: Collection<T>): Boolean =
		if(this.connection is MutableSequencedCollection<T>) this.connection.addAll(CollectionViewImpl(elements)) else throw UnsupportedOperationException("addAll(kotlin.collections.Collection)")

	override fun remove(element: T): Boolean =
		if(this.connection is RemoveOnlySequencedCollection<T>) this.connection.remove(element) else throw UnsupportedOperationException("remove(T)")

	override fun removeAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySequencedCollection<T>) this.connection.removeAll(SetViewImpl(elements.toSet())) else throw UnsupportedOperationException("removeAll(kotlin.collections.Collection)")

	override fun retainAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySequencedCollection<T>) this.connection.retainAll(SetViewImpl(elements.toSet())) else throw UnsupportedOperationException("retainAll(kotlin.collections.Collection)")

	override fun clear() =
		if(this.connection is RemoveOnlySequencedCollection<T>) this.connection.clear() else throw UnsupportedOperationException("clear")
}

internal fun <T> CollectionView<T>.conditionalIterator(): MutableIterator<T> {
	val iterator = iterator()
	return object: MutableIterator<T> by iterator {
		@Suppress("UNCHECKED_CAST")
		override fun remove() = (iterator as java.util.Iterator<T>).remove()
	}
}
