package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableCollection
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl

open class KotlinCollectionImpl<T>(private val connection: CollectionView<T>): Collection<T> {
	override fun iterator(): Iterator<T> = this.connection.iterator()

	override val size: Int
		get() = this.connection.size()

	override fun isEmpty(): Boolean = this.connection.isEmpty()

	override fun contains(element: T): Boolean = this.connection.contains(element)

	override fun containsAll(elements: Collection<T>): Boolean = this.connection.containsAll(SetViewImpl(elements.toSet()))

	override fun toString(): String = "${this::class.qualifiedName}{elements=[${joinToString()}]}"
}

open class KotlinMutableCollectionImpl<T>(private val connection: RemoveOnlyCollection<T>): KotlinCollectionImpl<T>(connection), kotlin.collections.MutableCollection<T> {
	override fun iterator(): MutableIterator<T> = this.connection.iterator()

	override fun add(element: T): Boolean =
		if(this.connection is MutableCollection<T>) this.connection.add(element) else throw UnsupportedOperationException("add(T)")

	override fun addAll(elements: Collection<T>): Boolean =
		if(this.connection is MutableCollection<T>) this.connection.addAll(CollectionViewImpl(elements)) else throw UnsupportedOperationException("addAll(kotlin.collections.Collection)")

	override fun remove(element: T): Boolean = this.connection.remove(element)

	override fun removeAll(elements: Collection<T>): Boolean = this.connection.removeAll(SetViewImpl(elements.toSet()))

	override fun retainAll(elements: Collection<T>): Boolean = this.connection.retainAll(SetViewImpl(elements.toSet()))

	override fun clear() = this.connection.clear()
}
