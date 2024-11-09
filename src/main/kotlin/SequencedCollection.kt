package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.MutableSequenced
import io.github.spacedvoid.connection.characteristic.RemoveOnlySequenced
import io.github.spacedvoid.connection.characteristic.Sequenced

interface SequencedCollectionView<T>: CollectionView<T>, Sequenced<T> {
	override fun reverse(): SequencedCollectionView<T>

	override fun first(): T

	override fun last(): T
}

interface SequencedCollection<T>: Collection<T>, SequencedCollectionView<T>, Sequenced<T> {
	override fun reverse(): SequencedCollection<T>
}

interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, SequencedCollectionView<T>, RemoveOnlySequenced<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	override fun removeFirst(): T

	override fun removeLast(): T
}

interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T>, MutableSequenced<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
