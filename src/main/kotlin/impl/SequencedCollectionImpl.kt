package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class SequencedCollectionViewImpl<T>(private val kotlin: java.util.SequencedCollection<T>): CollectionViewImpl<T>(kotlin), SequencedCollectionView<T> {
	override fun reversed(): SequencedCollectionView<T> = SequencedCollectionViewImpl(this.kotlin.reversed())

	override val CollectionView<T>.kotlin: java.util.SequencedCollection<T>
		get() = this@SequencedCollectionViewImpl.kotlin
}

open class SequencedCollectionImpl<T>(private val kotlin: java.util.SequencedCollection<T>): SequencedCollectionViewImpl<T>(kotlin), SequencedCollection<T> {
	override fun reversed(): SequencedCollection<T> = SequencedCollectionImpl(this.kotlin.reversed())
}

open class RemoveOnlySequencedCollectionImpl<T>(private val kotlin: java.util.SequencedCollection<T>): SequencedCollectionViewImpl<T>(kotlin), RemoveOnlySequencedCollection<T> {
	override fun reversed(): RemoveOnlySequencedCollection<T> = RemoveOnlySequencedCollectionImpl(this.kotlin.reversed())
}

open class MutableSequencedCollectionImpl<T>(private val kotlin: java.util.SequencedCollection<T>): RemoveOnlySequencedCollectionImpl<T>(kotlin), MutableSequencedCollection<T> {
	override fun reversed(): MutableSequencedCollection<T> = MutableSequencedCollectionImpl(this.kotlin.reversed())
}
