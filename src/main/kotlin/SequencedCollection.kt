package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.MutableSequenced
import io.github.spacedvoid.connection.characteristic.RemoveOnlySequenced
import io.github.spacedvoid.connection.characteristic.Sequenced

interface SequencedCollection<T>: Collection<T>, Sequenced<T> {
	override fun reverse(): SequencedCollection<T>
}

interface MutatingSequencedCollectionView<T>: MutatingCollectionView<T>, Sequenced<T> {
	override fun reverse(): MutatingSequencedCollectionView<T>
}

interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, MutatingSequencedCollectionView<T>, RemoveOnlySequenced<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	override fun removeFirst(): T

	override fun removeLast(): T
}

interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T>, MutableSequenced<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
