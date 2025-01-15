/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*

open class SequencedSetViewImpl<T>(private val kotlin: java.util.SequencedSet<T>): SetViewImpl<T>(kotlin), SequencedSetView<T> {
	override fun reversed(): SequencedSetView<T> = SequencedSetViewImpl(this.kotlin.reversed())

	override val CollectionView<T>.kotlin: java.util.SequencedSet<T>
		get() = this@SequencedSetViewImpl.kotlin
}

open class SequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): SequencedSetViewImpl<T>(kotlin), SequencedSet<T> {
	override fun reversed(): SequencedSet<T> = SequencedSetImpl(this.kotlin.reversed())
}

open class RemoveOnlySequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): SequencedSetViewImpl<T>(kotlin), RemoveOnlySequencedSet<T> {
	override fun reversed(): RemoveOnlySequencedSet<T> = RemoveOnlySequencedSetImpl(this.kotlin.reversed())
}

open class MutableSequencedSetImpl<T>(private val kotlin: java.util.SequencedSet<T>): RemoveOnlySequencedSetImpl<T>(kotlin), MutableSequencedSet<T> {
	override fun reversed(): MutableSequencedSet<T> = MutableSequencedSetImpl(this.kotlin.reversed())
}
