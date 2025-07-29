/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection

import java.util.Spliterator
import java.util.Spliterators

internal val emptyList = emptyList<Nothing>().asConnection()

internal object EmptyIterator: Iterator<Nothing> {
	override fun next(): Nothing = throw NoSuchElementException("Collection is empty")

	override fun hasNext(): Boolean = false
}

internal object EmptySequencedSet: SequencedSet<Nothing> {
	override fun size(): Int = 0

	override fun isEmpty(): Boolean = true

	override fun iterator(): Iterator<Nothing> = EmptyIterator

	@StreamSupport
	override fun spliterator(): Spliterator<Nothing> = Spliterators.emptySpliterator()

	override fun contains(element: Nothing): Boolean = false

	override fun containsAll(collection: CollectionView<Nothing>): Boolean = false

	override fun reversed(): SequencedSet<Nothing> = this

	override fun first(): Nothing = throw NoSuchElementException("Collection is empty")

	override fun last(): Nothing = throw NoSuchElementException("Collection is empty")

	override fun equals(other: Any?): Boolean = other is SetView<*> && other.isEmpty()

	override fun hashCode(): Int = 0

	override fun toString(): String = "${this::class.qualifiedName}{elements=[]}"
}

internal object EmptySequencedMap: SequencedMap<Any?, Nothing> {
	override fun size(): Int = 0

	override fun isEmpty(): Boolean = true

	override fun containsKey(key: Any?): Boolean = false

	override fun containsValue(value: Nothing): Boolean = false

	override fun get(key: Any?): Nothing? = null

	override fun reversed(): SequencedMap<Any?, Nothing> = this

	override fun first(): Pair<Any?, Nothing>? = null

	override fun last(): Pair<Any?, Nothing>? = null

	override fun firstKey(): Nothing = throw NoSuchElementException("Map is empty")

	override fun lastKey(): Nothing = throw NoSuchElementException("Map is empty")

	override val keys: SequencedSet<Any?> = EmptySequencedSet

	override val values: SequencedCollection<Nothing> = EmptySequencedSet

	override val entries: SequencedSet<Map.Entry<Any?, Nothing>> = EmptySequencedSet

	override fun equals(other: Any?): Boolean = other is MapView<*, *> && other.isEmpty()

	override fun hashCode(): Int = 0

	override fun toString(): String = "${this::class.qualifiedName}{entries=[]}"
}
