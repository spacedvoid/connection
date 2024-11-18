package io.github.spacedvoid.connection

interface CollectionView<T> {
	fun size(): Int =
		this.kotlin.size

	fun isEmpty(): Boolean =
		this.kotlin.isEmpty()

	operator fun contains(element: T): Boolean =
		this.kotlin.contains(element)

	fun containsAll(from: Collection<T>): Boolean =
		this.kotlin.containsAll(from.kotlin)

	val CollectionView<T>.kotlin: kotlin.collections.Collection<T>
}

interface Collection<T>: CollectionView<T>, Iterable<T> {
	override operator fun iterator(): Iterator<T> =
		this.kotlin.iterator()
}

interface RemoveOnlyCollection<T>: CollectionView<T> {
	fun remove(element: T): Boolean =
		this.kotlin.remove(element)

	fun removeAll(from: Collection<T>): Boolean =
		this.kotlin.removeAll(from.kotlin.toSet())

	fun retainAll(from: Collection<T>): Boolean =
		this.kotlin.retainAll(from.kotlin.toSet())

	fun clear() =
		this.kotlin.clear()

	override val CollectionView<T>.kotlin: kotlin.collections.MutableCollection<T>
}

interface MutableCollection<T>: RemoveOnlyCollection<T> {
	fun add(element: T): Boolean =
		this.kotlin.add(element)

	fun addAll(from: Collection<T>): Boolean =
		this.kotlin.addAll(from.kotlin)
}
