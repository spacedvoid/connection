package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Listed
import io.github.spacedvoid.connection.characteristic.MutableListed

interface List<T>: SequencedCollection<T>, Listed<T> {
	override fun iterator(): ListIterator<T>

	override fun reverse(): List<T>

	override fun slice(startInclusive: Int, endExclusive: Int): List<T>
}

interface MutatingListView<T>: MutatingSequencedCollectionView<T>, Listed<T> {
	override fun reverse(): MutatingListView<T>

	override fun slice(startInclusive: Int, endExclusive: Int): MutatingListView<T>
}

interface MutableList<T>: MutableSequencedCollection<T>, MutatingListView<T>, MutableListed<T> {
	override fun reverse(): MutableList<T>

	override fun slice(startInclusive: Int, endExclusive: Int): MutableList<T>

	override fun add(index: Int, element: T)

	override operator fun set(index: Int, element: T): T

	override fun removeAt(index: Int): T
}
