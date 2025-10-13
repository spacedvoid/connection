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
import io.github.spacedvoid.connection.utils.safeContains
import io.github.spacedvoid.connection.utils.safeContainsAll
import java.util.Spliterator

open class CollectionViewImpl<out T>(open val kotlin: kotlin.collections.Collection<T>): CollectionView<T> {
	override fun iterator(): Iterator<T> = this.kotlin.iterator()

	@StreamSupport
	override fun spliterator(): Spliterator<@UnsafeVariance T> = createSpliterator()

	override fun size(): Int = this.kotlin.size

	override fun isEmpty(): Boolean = this.kotlin.isEmpty()

	override fun contains(element: @UnsafeVariance T): Boolean = this.kotlin.safeContains(element)

	override fun containsAll(collection: CollectionView<@UnsafeVariance T>): Boolean = this.kotlin.safeContainsAll(collection.asKotlin())

	override fun equals(other: Any?): Boolean = super.equals(other)

	override fun hashCode(): Int = super.hashCode()

	override fun toString(): String = "${this::class.qualifiedName}{elements=[${joinToString()}]}"
}

open class CollectionImpl<out T>(kotlin: kotlin.collections.Collection<T>): CollectionViewImpl<T>(kotlin), Collection<T>

open class RemoveOnlyCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): CollectionViewImpl<T>(kotlin), RemoveOnlyCollection<T> {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeAll(collection: CollectionView<T>): Boolean = this.kotlin.removeAll(collection.asKotlin())

	override fun retainAll(collection: CollectionView<T>): Boolean = this.kotlin.retainAll(collection.asKotlin())

	override fun clear() = this.kotlin.clear()
}

open class MutableCollectionImpl<T>(override val kotlin: kotlin.collections.MutableCollection<T>): RemoveOnlyCollectionImpl<T>(kotlin), MutableCollection<T> {
	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<T>): Boolean = this.kotlin.addAll(collection.asKotlin())
}

internal fun <T> CollectionView<T>.asKotlin(): kotlin.collections.Collection<T> =
	if(this is CollectionViewImpl<T>) this.kotlin else KotlinCollectionImpl(this)
