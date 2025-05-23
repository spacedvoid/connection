package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl
import java.util.SequencedSet

open class KotlinSequencedSetImpl<T>(private val connection: SequencedSetView<T>): KotlinCollectionImpl<T>(connection), SequencedSet<T> {
	override fun iterator(): MutableIterator<T> = this.connection.conditionalIterator()

	override fun reversed(): SequencedSet<T> = KotlinSequencedSetImpl(this.connection.reversed())

	override fun add(element: T): Boolean =
		if(this.connection is MutableSequencedSet<T>) this.connection.add(element) else throw UnsupportedOperationException("add(T)")

	override fun remove(element: T): Boolean =
		if(this.connection is RemoveOnlySequencedSet<T>) this.connection.remove(element) else throw UnsupportedOperationException("remove(T)")

	override fun addAll(elements: Collection<T>): Boolean =
		if(this.connection is MutableSequencedSet<T>) this.connection.addAll(CollectionViewImpl(elements)) else throw UnsupportedOperationException("addAll(kotlin.collections.Collection)")

	override fun removeAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySequencedSet<T>) this.connection.removeAll(SetViewImpl(elements.toSet())) else throw UnsupportedOperationException("removeAll(kotlin.collections.Collection)")

	override fun retainAll(elements: Collection<T>): Boolean =
		if(this.connection is RemoveOnlySequencedSet<T>) this.connection.retainAll(CollectionViewImpl(elements)) else throw UnsupportedOperationException("retainAll(kotlin.collections.Collection)")

	override fun clear() =
		if(this.connection is RemoveOnlySequencedSet<T>) this.connection.clear() else throw UnsupportedOperationException("clear")
}
