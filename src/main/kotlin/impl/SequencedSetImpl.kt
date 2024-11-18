package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.CollectionView
import io.github.spacedvoid.connection.MutableSequencedSet
import io.github.spacedvoid.connection.RemoveOnlySequencedSet
import io.github.spacedvoid.connection.SequencedSet
import io.github.spacedvoid.connection.SequencedSetView

open class SequencedSetViewImpl<T>(private val kotlin: java.util.SequencedSet<T>): SetViewImpl<T>(kotlin), SequencedSetView<T> {
	override fun reverse(): SequencedSetView<T> = SequencedSetViewImpl(this.kotlin.reversed())

	override val CollectionView<T>.kotlin: java.util.SequencedSet<T>
		get() = this@SequencedSetViewImpl.kotlin
}

open class SequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): SequencedSetViewImpl<T>(kotlin), SequencedSet<T> {
	override fun reverse(): SequencedSet<T> = SequencedSetImpl(this.kotlin.reversed())
}

open class RemoveOnlySequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): SequencedSetViewImpl<T>(kotlin), RemoveOnlySequencedSet<T> {
	override fun reverse(): RemoveOnlySequencedSet<T> = RemoveOnlySequencedSetImpl(this.kotlin.reversed())
}

open class MutableSequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): RemoveOnlySequencedSetImpl<T>(kotlin), MutableSequencedSet<T> {
	override fun reverse(): MutableSequencedSet<T> = MutableSequencedSetImpl(this.kotlin.reversed())
}
