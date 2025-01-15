/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.List
import io.github.spacedvoid.connection.MutableList
import kotlin.collections.List as KotlinList
import kotlin.collections.MutableList as KotlinMutableList

@Suppress("UNCHECKED_CAST")
open class ListViewImpl<T>(private val kotlin: KotlinList<T>): SequencedCollectionViewImpl<T>(kotlin as java.util.SequencedCollection<T>), ListView<T> {
	override fun reversed(): ListView<T> = ListViewImpl(this.kotlin.reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): ListView<T> = ListViewImpl(this.kotlin.subList(startInclusive, endExclusive))

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: KotlinList<T>
		get() = this@ListViewImpl.kotlin
}

open class ListImpl<T>(private val kotlin: KotlinList<T>): ListViewImpl<T>(kotlin), List<T> {
	override fun reversed(): List<T> = ListImpl(this.kotlin.reversed())

	override fun subList(startInclusive: Int, endExclusive: Int): List<T> = ListImpl(this.kotlin.subList(startInclusive, endExclusive))
}

open class MutableListImpl<T>(private val kotlin: KotlinMutableList<T>): ListViewImpl<T>(kotlin), MutableList<T> {
	override fun reversed(): MutableList<T> = MutableListImpl(this.kotlin.asReversed())

	override fun subList(startInclusive: Int, endExclusive: Int): MutableList<T> = MutableListImpl(this.kotlin.subList(startInclusive, endExclusive))

	@Suppress("PROPERTY_TYPE_MISMATCH_ON_OVERRIDE")
	override val CollectionView<T>.kotlin: KotlinMutableList<T>
		get() = this@MutableListImpl.kotlin
}
