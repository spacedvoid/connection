@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

interface SequencedSetBase<T>: SetBase<T>, SequencedCollectionBase<T>

interface SequencedSet<T>: Set<T>, SequencedCollection<T>, SequencedSetBase<T> {
	override fun reverse(): SequencedSet<T>
}

interface MutatingSequencedSetView<T>: MutatingSequencedCollectionView<T>, MutatingSetView<T>, SequencedSetBase<T> {
	override fun reverse(): MutatingSequencedSetView<T>
}

interface RemoveOnlySequencedSet<T>: RemoveOnlySet<T>, RemoveOnlySequencedCollection<T>, MutatingSequencedSetView<T> {
	override fun reverse(): RemoveOnlySequencedSet<T>
}

interface MutableSequencedSet<T>: MutableSet<T>, MutableSequencedCollection<T>, RemoveOnlySequencedSet<T> {
	override fun reverse(): MutableSequencedSet<T>
}