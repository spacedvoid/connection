/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl

open class SequencedSetViewImpl<T>(override val kotlin: java.util.SequencedSet<T>): SequencedSetView<T>, SequencedCollectionViewImpl<T>(kotlin) {
	override fun reversed(): SequencedSetView<T> = SequencedSetViewImpl(this.kotlin.reversed())
}

open class SequencedSetImpl<T>(override val kotlin: java.util.SequencedSet<T>): SequencedSet<T>, SequencedSetViewImpl<T>(kotlin) {
	override fun reversed(): SequencedSet<T> = SequencedSetImpl<T>(this.kotlin.reversed())
}

open class RemoveOnlySequencedSetImpl<T>(override val kotlin: java.util.SequencedSet<T>): RemoveOnlySequencedSet<T>, SequencedSetViewImpl<T>(kotlin) {
	override fun iterator(): MutableIterator<T> = this.kotlin.iterator()

	override fun reversed(): RemoveOnlySequencedSet<T> = RemoveOnlySequencedSetImpl(this.kotlin.reversed())

	override fun removeFirst(): T = this.kotlin.removeFirst()

	override fun removeLast(): T = this.kotlin.removeLast()

	override fun remove(element: T): Boolean = this.kotlin.remove(element)

	override fun removeIf(predicate: (T) -> Boolean): Boolean = this.kotlin.removeIf(predicate)

	override fun removeAll(collection: CollectionView<out T>): Boolean = this.kotlin.removeAll(collection.toSet())

	override fun retainAll(collection: CollectionView<out T>): Boolean = this.kotlin.retainAll(collection.toSet())

	override fun clear() = this.kotlin.clear()
}

open class MutableSequencedSetImpl<T>(override val kotlin: java.util.SequencedSet<T>): MutableSequencedSet<T>, RemoveOnlySequencedSetImpl<T>(kotlin) {
	override fun reversed(): MutableSequencedSet<T> = MutableSequencedSetImpl(this.kotlin.reversed())

	override fun add(element: T): Boolean = this.kotlin.add(element)

	override fun addAll(collection: CollectionView<out T>): Boolean = this.kotlin.addAll(KotlinCollectionImpl(collection))
}
