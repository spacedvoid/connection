package io.github.spacedvoid.connection

/**
 * A [SetView] that additionally defines the iteration order.
 */
interface SequencedSetView<T>: SequencedCollectionView<T>, SetView<T> {
	override fun reverse(): SequencedSetView<T>

	override val CollectionView<T>.kotlin: java.util.SequencedSet<T>
}

/**
 * An immutable [SequencedSetView].
 */
interface SequencedSet<T>: SequencedCollection<T>, Set<T>, SequencedSetView<T> {
	override fun reverse(): SequencedSet<T>
}

/**
 * A [SequencedSetView] that additionally supports element removal operations.
 */
interface RemoveOnlySequencedSet<T>: RemoveOnlySequencedCollection<T>, RemoveOnlySet<T>, SequencedSetView<T> {
	override fun reverse(): RemoveOnlySequencedSet<T>
}

/**
 * A [RemoveOnlySequencedSet] that additionally supports element addition operations.
 */
interface MutableSequencedSet<T>: MutableSequencedCollection<T>, MutableSet<T>, RemoveOnlySequencedSet<T> {
	override fun reverse(): MutableSequencedSet<T>
}
