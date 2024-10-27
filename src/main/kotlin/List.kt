@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

@Deprecated("This type is hidden for public use.", ReplaceWith("List", "io.github.spacedvoid.connection.List"), DeprecationLevel.HIDDEN)
interface ListBase<T>: SequencedCollectionBase<T> {
	override fun reverse(): ListBase<T>

	fun slice(startInclusive: Int, endExclusive: Int): ListBase<T>

	operator fun get(index: Int): T

	fun indexOf(element: T): Int

	fun lastIndexOf(element: T): Int
}

interface List<T>: SequencedCollection<T>, ListBase<T> {
	override fun iterator(): ListIterator<T>

	override fun reverse(): List<T>

	override fun slice(startInclusive: Int, endExclusive: Int): List<T>
}

interface MutatingListView<T>: MutatingSequencedCollectionView<T>, ListBase<T> {
	override fun reverse(): MutatingListView<T>

	override fun slice(startInclusive: Int, endExclusive: Int): MutatingListView<T>
}

interface MutableList<T>: MutableSequencedCollection<T>, MutatingListView<T> {
	override fun reverse(): MutableList<T>

	override fun slice(startInclusive: Int, endExclusive: Int): MutableList<T>

	fun add(index: Int, element: T)

	operator fun set(index: Int, element: T): T

	fun removeAt(index: Int): T
}
