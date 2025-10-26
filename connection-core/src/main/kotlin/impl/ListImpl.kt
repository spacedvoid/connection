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

open class ListViewImpl<out T>(
	@property:Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE") override val kotlin: kotlin.collections.List<T>
): ListView<T>, SequencedCollectionViewImpl<T>(kotlin as java.util.SequencedCollection<T>) {
	override fun iterator(): ListIterator<T> = this.kotlin.listIterator()

	override fun iterator(index: Int): ListIterator<T> = this.kotlin.listIterator(index)

	override fun reversed(): ListView<T> = ListViewImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T> = ListViewImpl(this.kotlin.subList(startInclusive, endExclusive))

	override fun get(index: Int): T = this.kotlin[index]

	override fun indexOf(element: @UnsafeVariance T): Int = this.kotlin.indexOf(element)

	override fun lastIndexOf(element: @UnsafeVariance T): Int = this.kotlin.lastIndexOf(element)

	override fun equals(other: Any?): Boolean = listLikeEquals<ListView<*>>(other)

	override fun hashCode(): Int = listLikeHashCode()
}

open class ListImpl<out T>(override val kotlin: kotlin.collections.List<T>): List<T>, ListViewImpl<T>(kotlin) {
	override fun reversed(): List<T> = ListImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): List<T> = ListImpl(this.kotlin.subList(startInclusive, endExclusive))
}

open class MutableListImpl<T>(override val kotlin: kotlin.collections.MutableList<T>): MutableList<T>, ListViewImpl<T>(kotlin) {
	override fun iterator(): MutableListIterator<T> = this.kotlin.listIterator()

	override fun iterator(index: Int): MutableListIterator<T> = this.kotlin.listIterator(index)

	override fun reversed(): MutableList<T> = MutableListImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T> = MutableListImpl(this.kotlin.subList(startInclusive, endExclusive))

	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun add(index: Int, element: T) = this.kotlin.add(index, element)

	override fun addAll(collection: CollectionView<T>): Boolean = this.kotlin.addAll(collection.asKotlin())

	override fun set(index: Int, element: T): T = this.kotlin.set(index, element)

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeAt(index: Int): T = this.kotlin.removeAt(index)

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	override fun removeAll(collection: CollectionView<T>): Boolean = this.kotlin.removeAll(collection.asKotlin())

	override fun retainAll(collection: CollectionView<T>): Boolean = this.kotlin.retainAll(collection.asKotlin())

	override fun clear() = this.kotlin.clear()
}

internal inline fun <reified T: CollectionView<*>> CollectionView<*>.listLikeEquals(other: Any?): Boolean {
	if(this === other) return true
	if(other !is T) return false
	val thisIterator = iterator()
	val otherIterator = other.iterator()
	while(thisIterator.hasNext() && otherIterator.hasNext()) {
		if(thisIterator.next() != otherIterator.next()) return false
	}
	return !(thisIterator.hasNext() || otherIterator.hasNext())
}

internal fun CollectionView<*>.listLikeHashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
