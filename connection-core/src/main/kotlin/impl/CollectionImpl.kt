/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.MutableCollection
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl
import io.github.spacedvoid.connection.utils.createSpliterator
import java.util.Spliterator

open class CollectionViewImpl<T>(open val kotlin: kotlin.collections.Collection<T>): CollectionView<T> {
	override fun iterator(): Iterator<T> = this.kotlin.iterator()

	@StreamSupport
	override fun spliterator(): Spliterator<T> = createSpliterator()

	override fun size(): Int = this.kotlin.size

	override fun isEmpty(): Boolean = this.kotlin.isEmpty()

	override fun contains(element: T): Boolean = this.kotlin.contains(element)

	override fun containsAll(collection: CollectionView<out T>): Boolean = this.kotlin.containsAll(KotlinCollectionImpl(collection))

	override fun equals(other: Any?): Boolean = super.equals(other)

	override fun hashCode(): Int = super.hashCode()

	override fun toString(): String = "${this::class.qualifiedName}{elements=[${joinToString()}]}"
}

open class CollectionImpl<T>(kotlin: kotlin.collections.Collection<T>): CollectionViewImpl<T>(kotlin), Collection<T>

open class RemoveOnlyCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): CollectionViewImpl<T>(kotlin), RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	@Deprecated("This method is replaced with an inline operation.", replaceWith = ReplaceWith("removeAll(predicate)", "io.github.spacedvoid.connection.removeAll"))
	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(KotlinCollectionImpl(collection))

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(KotlinCollectionImpl(collection))

	override fun clear() = this.kotlin.clear()
}

open class MutableCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): RemoveOnlyCollectionImpl<T>(kotlin), MutableCollection<T> {
	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))
}
