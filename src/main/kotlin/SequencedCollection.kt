package io.github.spacedvoid.connection

interface SequencedCollectionView<T>: CollectionView<T> {
	fun reverse(): SequencedCollectionView<T>

	fun first(): T =
		this.kotlin.first

	fun last(): T =
		this.kotlin.last

	override val CollectionView<T>.kotlin: java.util.SequencedCollection<T>
}

interface SequencedCollection<T>: Collection<T>, SequencedCollectionView<T>{
	override fun reverse(): SequencedCollection<T>
}

interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, SequencedCollectionView<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	fun removeFirst(): T =
		this.kotlin.removeFirst()

	fun removeLast(): T =
		this.kotlin.removeLast()
}

interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
