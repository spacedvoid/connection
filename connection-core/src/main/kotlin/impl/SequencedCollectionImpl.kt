package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl

open class SequencedCollectionViewImpl<T>(override val kotlin: java.util.SequencedCollection<T>): SequencedCollectionView<T>, CollectionViewImpl<T>(kotlin) {
	override fun reversed(): SequencedCollectionView<T> = SequencedCollectionViewImpl(this.kotlin.reversed())

	override fun first(): T = this.kotlin.first

	override fun last(): T = this.kotlin.last
}

open class SequencedCollectionImpl<T>(override val kotlin: java.util.SequencedCollection<T>): SequencedCollection<T>, SequencedCollectionViewImpl<T>(kotlin) {
	override fun reversed(): SequencedCollection<T> = SequencedCollectionImpl(this.kotlin.reversed())
}

open class RemoveOnlySequencedCollectionImpl<T>(override val kotlin: java.util.SequencedCollection<T>): RemoveOnlySequencedCollection<T>, SequencedCollectionViewImpl<T>(kotlin) {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun reversed(): RemoveOnlySequencedCollection<T> = RemoveOnlySequencedCollectionImpl(this.kotlin.reversed())

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(collection.toSet())

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(collection.toSet())

	override fun clear() = this.kotlin.clear()
}

open class MutableSequencedCollectionImpl<T>(override val kotlin: java.util.SequencedCollection<T>): MutableSequencedCollection<T>, RemoveOnlySequencedCollectionImpl<T>(kotlin) {
	override fun reversed(): MutableSequencedCollection<T> = MutableSequencedCollectionImpl(this.kotlin.reversed())

	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))
}
