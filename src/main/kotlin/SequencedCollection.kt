@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

@Deprecated("This type is hidden for public use.", ReplaceWith("SequencedCollection", "io.github.spacedvoid.connection.SequencedCollection"), DeprecationLevel.HIDDEN)
interface SequencedCollectionBase<T>: CollectionBase<T> {
	fun reverse(): SequencedCollectionBase<T>

	fun first(): T

	fun last(): T
}

interface SequencedCollection<T>: Collection<T>, SequencedCollectionBase<T> {
	override fun reverse(): SequencedCollection<T>
}

interface MutatingSequencedCollectionView<T>: MutatingCollectionView<T>, SequencedCollectionBase<T> {
	override fun reverse(): MutatingSequencedCollectionView<T>
}

interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, MutatingSequencedCollectionView<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	fun removeFirst(): T

	fun removeLast(): T
}

interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
