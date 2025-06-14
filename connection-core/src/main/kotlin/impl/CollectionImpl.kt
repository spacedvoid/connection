package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.MutableCollection
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl
import io.github.spacedvoid.connection.utils.Spliterators
import java.util.Spliterator

open class CollectionViewImpl<T>(open val kotlin: kotlin.collections.Collection<T>): CollectionView<T> {
	override fun iterator(): Iterator<T> = this.kotlin.iterator()

	@StreamSupport
	override fun spliterator(): Spliterator<T> = Spliterators.fromDefault(this)

	override fun size(): Int = this.kotlin.size

	override fun isEmpty(): Boolean = this.kotlin.isEmpty()

	override fun contains(element: T): Boolean = this.kotlin.contains(element)

	override fun containsAll(collection: CollectionView<out T>): Boolean = this.kotlin.containsAll(collection.toSet())

	override fun equals(other: Any?): Boolean = super.equals(other)

	override fun hashCode(): Int = super.hashCode()

	override fun toString(): String = "${this::class.qualifiedName}{elements=[${joinToString()}]}"
}

open class CollectionImpl<T>(kotlin: kotlin.collections.Collection<T>): CollectionViewImpl<T>(kotlin), Collection<T>

open class RemoveOnlyCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): CollectionViewImpl<T>(kotlin), RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(collection.toSet())

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(collection.toSet())

	override fun clear() = this.kotlin.clear()
}

open class MutableCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): RemoveOnlyCollectionImpl<T>(kotlin), MutableCollection<T> {
	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))
}
