@file:Suppress("DEPRECATION_ERROR")

package io.github.spacedvoid.connection

@Deprecated("This type is hidden for public use.", ReplaceWith("Collection", "io.github.spacedvoid.connection.Collection"), DeprecationLevel.HIDDEN)
interface CollectionBase<T> {
	fun size(): Int

	operator fun contains(element: T): Boolean

	fun containsAll(from: Collection<T>): Boolean
}

interface Collection<T>: CollectionBase<T>, Iterable<T> {
	override fun size(): Int

	override operator fun contains(element: T): Boolean

	override fun containsAll(from: Collection<T>): Boolean
}

interface MutatingCollectionView<T>: CollectionBase<T> {
	override fun size(): Int

	override operator fun contains(element: T): Boolean

	override fun containsAll(from: Collection<T>): Boolean
}

interface RemoveOnlyCollection<T>: MutatingCollectionView<T> {
	fun remove(element: T): Boolean

	fun removeAll(from: Collection<T>): Boolean

	fun clear()
}

interface MutableCollection<T>: RemoveOnlyCollection<T> {
	fun add(element: T): Boolean

	fun addAll(from: Collection<T>): Boolean
}
