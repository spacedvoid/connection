/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("UNCHECKED_CAST")

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.List
import io.github.spacedvoid.connection.MutableList
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl

open class ListViewImpl<T>(
	@property:Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE") override val kotlin: kotlin.collections.List<T>
): SequencedCollectionViewImpl<T>(kotlin as java.util.SequencedCollection<T>), ListView<T> {
	override fun iterator(): ListIterator<T> = this.kotlin.listIterator()

	override fun reversed(): ListView<T> = ListViewImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T> = ListViewImpl(this.kotlin.subList(startInclusive, endExclusive))

	override fun get(index: Int): T = this.kotlin[index]

	override fun indexOf(element: T): Int = this.kotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this.kotlin.lastIndexOf(element)

}

open class ListImpl<T>(override val kotlin: kotlin.collections.List<T>): ListViewImpl<T>(kotlin), List<T> {
	override fun reversed(): List<T> = ListImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): List<T> = ListImpl(this.kotlin.subList(startInclusive, endExclusive))
}

open class MutableListImpl<T>(override val kotlin: kotlin.collections.MutableList<T>): ListViewImpl<T>(kotlin), MutableList<T> {
	override fun iterator(): MutableListIterator<T> = this.kotlin.listIterator()

	override fun reversed(): MutableList<T> = MutableListImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T> = MutableListImpl(this.kotlin.subList(startInclusive, endExclusive))

	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun add(index: Int, element: T) = this.kotlin.add(index, element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))

	override fun addFirst(element: T) = this.kotlin.addFirst(element)

	override fun addLast(element: T) = this.kotlin.addLast(element)

	override fun set(index: Int, element: T): T = this.kotlin.set(index, element)

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeAt(index: Int): T = this.kotlin.removeAt(index)

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(collection.toSet())

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(collection.toSet())

	override fun clear() = this.kotlin.clear()

}
