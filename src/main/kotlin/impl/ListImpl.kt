package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.CollectionView
import io.github.spacedvoid.connection.List
import io.github.spacedvoid.connection.ListView
import io.github.spacedvoid.connection.MutableList
import kotlin.collections.List as KotlinList
import kotlin.collections.MutableList as KotlinMutableList

@Suppress("UNCHECKED_CAST")
open class ListViewImpl<T>(private val kotlin: KotlinList<T>): SequencedCollectionViewImpl<T>(kotlin as java.util.SequencedCollection<T>), ListView<T> {
	override fun reverse(): ListView<T> = ListViewImpl(this.kotlin.reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T> = ListViewImpl(this.kotlin.subList(startInclusive, endExclusive))

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: KotlinList<T>
		get() = this@ListViewImpl.kotlin
}

open class ListImpl<T>(private val kotlin: KotlinList<T>): ListViewImpl<T>(kotlin), List<T> {
	override fun reverse(): List<T> = ListImpl(this.kotlin.reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): List<T> = ListImpl(this.kotlin.subList(startInclusive, endExclusive))
}

open class MutableListImpl<T>(private val kotlin: KotlinMutableList<T>): ListViewImpl<T>(kotlin), MutableList<T> {
	override fun reverse(): MutableList<T> = MutableListImpl(this.kotlin.asReversed())

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T> = MutableListImpl(this.kotlin.subList(startInclusive, endExclusive))

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: KotlinMutableList<T>
		get() = this@MutableListImpl.kotlin
}
