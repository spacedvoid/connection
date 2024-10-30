package io.github.spacedvoid.connection

import io.github.spacedvoid.connection.characteristic.Collectable
import io.github.spacedvoid.connection.characteristic.Mutable
import io.github.spacedvoid.connection.characteristic.RemoveOnly

interface Collection<T>: Iterable<T>, Collectable<T> {
	override operator fun iterator(): Iterator<T>

	override fun size(): Int

	override operator fun contains(element: T): Boolean

	override fun containsAll(from: Collection<T>): Boolean
}

interface MutatingCollectionView<T>: Collectable<T> {
	override fun size(): Int

	override operator fun contains(element: T): Boolean

	override fun containsAll(from: Collection<T>): Boolean
}

interface RemoveOnlyCollection<T>: MutatingCollectionView<T>, RemoveOnly<T> {
	override fun remove(element: T): Boolean

	override fun removeAll(from: Collection<T>): Boolean

	override fun clear()
}

interface MutableCollection<T>: RemoveOnlyCollection<T>, Mutable<T> {
	override fun add(element: T): Boolean

	override fun addAll(from: Collection<T>): Boolean
}
