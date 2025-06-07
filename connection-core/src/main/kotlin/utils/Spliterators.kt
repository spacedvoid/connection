/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.utils

import io.github.spacedvoid.connection.impl.CollectionImpl
import io.github.spacedvoid.connection.impl.CollectionViewImpl
import io.github.spacedvoid.connection.impl.NavigableSetViewImpl
import io.github.spacedvoid.connection.impl.SequencedCollectionViewImpl
import io.github.spacedvoid.connection.impl.SetViewImpl
import java.util.Spliterator

/**
 * Contains utility methods for creating spliterators.
 */
object Spliterators {
	/**
	 * Helps create a spliterator from default implementations.
	 */
	fun <T> fromDefault(target: CollectionViewImpl<T>): Spliterator<T> {
		var c = Spliterator.SIZED
		if(target is CollectionImpl<T>) c = c or Spliterator.IMMUTABLE
		if(target is SequencedCollectionViewImpl<T>) c = c or Spliterator.ORDERED
		if(target is SetViewImpl<T>) c = c or Spliterator.DISTINCT
		if(target is NavigableSetViewImpl<T>) c = c or Spliterator.SORTED
		val split = target.kotlin.spliterator()
		c = c or split.characteristics()

		/*
		 * The spliterator from Java is one of the following:
		 * - [Spliterator.IMMUTABLE]
		 * - [Spliterator.CONCURRENT]
		 * - late-binding
		 *
		 * All three cases does not affect a [Stream]'s lazy behavior even if we eagerly create the spliterator.
		 *
		 * Some characteristics are injected to the spliterator by [c], for example, [Spliterator.IMMUTABLE].
		 * This is because most implementations are mutable collections, and does not know they're treated immutable.
		 * We hope this does not cause any confusions...
		 */
		return object: Spliterator<T> by split {
			override fun estimateSize(): Long = target.size().toLong()

			override fun getExactSizeIfKnown(): Long = target.size().toLong()

			override fun characteristics(): Int = c

			override fun hasCharacteristics(characteristics: Int): Boolean = (c and characteristics) != 0

			override fun getComparator(): java.util.Comparator<in T> =
				if(target is NavigableSetViewImpl<T>) target.comparator else throw IllegalStateException("Collection is not sorted")
		}
	}
}
