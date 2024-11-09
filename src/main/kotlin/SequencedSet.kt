package io.github.spacedvoid.connection

interface SequencedSetView<T>: SetView<T>, SequencedCollectionView<T> {
	override fun reverse(): SequencedSetView<T>
}

interface SequencedSet<T>: Set<T>, SequencedCollection<T>, SequencedSetView<T> {
	override fun reverse(): SequencedSet<T>
}

interface RemoveOnlySequencedSet<T>: RemoveOnlySet<T>, RemoveOnlySequencedCollection<T>, SequencedSetView<T> {
	override fun reverse(): RemoveOnlySequencedSet<T>
}

interface MutableSequencedSet<T>: MutableSet<T>, MutableSequencedCollection<T>, RemoveOnlySequencedSet<T> {
	override fun reverse(): MutableSequencedSet<T>
}
