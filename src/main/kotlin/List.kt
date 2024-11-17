package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Listed
import io.github.spacedvoid.connection.characteristic.MutableListed

interface ListView<T>: SequencedCollectionView<T>, Listed<T> {
	override fun reverse(): ListView<T>

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T>

	override operator fun get(index: Int): T

	override fun indexOf(element: T): Int

	override fun lastIndexOf(element: T): Int
}

interface List<T>: SequencedCollection<T>, ListView<T>, Listed<T> {
	override operator fun iterator(): ListIterator<T>

	override fun reverse(): List<T>

	override fun subList(startInclusive: Int, endExclusive: Int): List<T>
}

interface MutableList<T>: MutableSequencedCollection<T>, ListView<T>, MutableListed<T> {
	override fun reverse(): MutableList<T>

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T>

	override fun add(index: Int, element: T)

	override fun addFirst(element: T)

	override fun addLast(element: T)

	override operator fun set(index: Int, element: T): T

	override fun removeAt(index: Int): T
}
