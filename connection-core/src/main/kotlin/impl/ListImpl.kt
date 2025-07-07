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
): ListView<T>, SequencedCollectionViewImpl<T>(kotlin as java.util.SequencedCollection<T>) {
	override fun iterator(): ListIterator<T> = this.kotlin.listIterator()

	override fun iterator(index: Int): ListIterator<T> = this.kotlin.listIterator(index)

	override fun reversed(): ListView<T> = ListViewImpl((this.kotlin as java.util.List<T>).reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T> = ListViewImpl(this.kotlin.subList(startInclusive, endExclusive))

	override fun get(index: Int): T = this.kotlin[index]

	override fun indexOf(element: T): Int = this.kotlin.indexOf(element)

	override fun lastIndexOf(element: T): Int = this.kotlin.lastIndexOf(element)

	/**
	 * Returns whether the given object is equal to this list.
	 *
	 * The given object is equal to this list if the object is a [ListView],
	 * and the elements of the given list equals to the elements in this list, regarding the order.
	 *
	 * This implementation uses the Java way, which is documented and implemented in [java.util.AbstractList.equals].
	 */
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is ListView<*>) return false
		val thisIterator = iterator()
		val otherIterator = other.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() || otherIterator.hasNext())
	}

	/**
	 * Returns a hash code for this list.
	 *
	 * The hash is computed based on the contained objects' hash codes, regarding the order.
	 *
	 * This implementation uses the Java way, which is documented and implemented in [java.util.AbstractList.hashCode]
	 */
	override fun hashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
}

open class ListImpl<T>(override val kotlin: kotlin.collections.List<T>): List<T>, ListViewImpl<T>(kotlin) {
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

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))

	override fun addFirst(element: T) = this.kotlin.addFirst(element)

	override fun addLast(element: T) = this.kotlin.addLast(element)

	override fun set(index: Int, element: T): T = this.kotlin.set(index, element)

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeAt(index: Int): T = this.kotlin.removeAt(index)

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	@Deprecated("This method is replaced with an inline operation.", replaceWith = ReplaceWith("removeAll", "io.github.spacedvoid.connection.removeAll"))
	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(collection.toSet())

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(collection.toSet())

	override fun clear() = this.kotlin.clear()
}
