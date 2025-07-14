/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableList
import io.github.spacedvoid.connection.impl.CollectionViewImpl

open class KotlinListImpl<T>(private val connection: ListView<T>): KotlinCollectionImpl<T>(connection), List<T> {
	override fun listIterator(): ListIterator<T> = this.connection.iterator()

	override fun listIterator(index: Int): ListIterator<T> =
		if(index in 0..this.size) this.connection.iterator().apply { repeat(index) { next() } } else throw IndexOutOfBoundsException("$index out of size ${this.size}")

	override fun subList(fromIndex: Int, toIndex: Int): List<T> = KotlinListImpl(this.connection.subList(fromIndex, toIndex))

	override fun get(index: Int): T = this.connection[index]

	override fun indexOf(element: T): Int = this.connection.indexOf(element)

	override fun lastIndexOf(element: T): Int = this.connection.lastIndexOf(element)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other !is List<*>) return false
		val thisIterator = iterator()
		val otherIterator = other.iterator()
		while(thisIterator.hasNext() && otherIterator.hasNext()) {
			if(thisIterator.next() != otherIterator.next()) return false
		}
		return !(thisIterator.hasNext() || otherIterator.hasNext())
	}

	override fun hashCode(): Int = fold(1) { r, e -> r * 31 + e.hashCode() }
}

open class KotlinMutableListImpl<T>(private val connection: MutableList<T>): KotlinListImpl<T>(connection), kotlin.collections.MutableList<T> {
	override fun iterator(): MutableIterator<T> = this.connection.iterator()

	override fun listIterator(): MutableListIterator<T> = this.connection.iterator()

	override fun listIterator(index: Int): MutableListIterator<T> =
		if(index in 0..this.size) this.connection.iterator().apply { repeat(index) { next() } } else throw IndexOutOfBoundsException("$index out of size ${this.size}")

	override fun subList(fromIndex: Int, toIndex: Int): kotlin.collections.MutableList<T> = KotlinMutableListImpl(this.connection.subList(fromIndex, toIndex))

	override fun add(element: T): Boolean = this.connection.add(element)

	override fun remove(element: T): Boolean = this.connection.remove(element)

	override fun addAll(elements: Collection<T>): Boolean = this.connection.addAll(CollectionViewImpl(elements))

	override fun addAll(index: Int, elements: Collection<T>): Boolean {
		val iterator = elements.iterator()
		for(i in index..<(index + elements.size)) add(i, iterator.next())
		return elements.isNotEmpty()
	}

	override fun removeAll(elements: Collection<T>): Boolean = this.connection.removeAll(CollectionViewImpl(elements))

	override fun retainAll(elements: Collection<T>): Boolean = this.connection.retainAll(CollectionViewImpl(elements))

	override fun clear() = this.connection.clear()

	override fun set(index: Int, element: T): T = this.connection.set(index, element)

	override fun add(index: Int, element: T) = this.connection.add(index, element)

	override fun removeAt(index: Int): T = this.connection.removeAt(index)
}
