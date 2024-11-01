package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.MutableSequenced
import io.github.spacedvoid.connection.characteristic.RemoveOnlySequenced
import io.github.spacedvoid.connection.characteristic.Sequenced

interface SequencedCollection<T>: Collection<T>, Sequenced<T> {
	fun reverse(): SequencedCollection<T>

	override fun first(): T

	override fun last(): T
}

interface MutatingSequencedCollectionView<T>: MutatingCollectionView<T>, Sequenced<T> {
	fun reverse(): MutatingSequencedCollectionView<T>

	override fun first(): T

	override fun last(): T
}

interface RemoveOnlySequencedCollection<T>: RemoveOnlyCollection<T>, MutatingSequencedCollectionView<T>, RemoveOnlySequenced<T> {
	override fun reverse(): RemoveOnlySequencedCollection<T>

	override fun removeFirst(): T

	override fun removeLast(): T
}

interface MutableSequencedCollection<T>: MutableCollection<T>, RemoveOnlySequencedCollection<T>, MutableSequenced<T> {
	override fun reverse(): MutableSequencedCollection<T>
}
