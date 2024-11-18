package io.github.spacedvoid.connection

interface SetView<T>: CollectionView<T> {
	override val CollectionView<T>.kotlin: kotlin.collections.Set<T>
}

interface Set<T>: Collection<T>, SetView<T>

interface RemoveOnlySet<T>: RemoveOnlyCollection<T>, SetView<T> {
	override val CollectionView<T>.kotlin: kotlin.collections.MutableSet<T>
}

interface MutableSet<T>: MutableCollection<T>, RemoveOnlySet<T>
