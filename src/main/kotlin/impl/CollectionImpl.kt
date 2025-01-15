/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.MutableCollection
import kotlin.collections.Collection as KotlinCollection
import kotlin.collections.MutableCollection as KotlinMutableCollection

open class CollectionViewImpl<T>(private val kotlin: KotlinCollection<T>): CollectionView<T> {
	override val CollectionView<T>.kotlin: KotlinCollection<T>
		get() = this@CollectionViewImpl.kotlin
}

open class CollectionImpl<T>(kotlin: KotlinCollection<T>): CollectionViewImpl<T>(kotlin), Collection<T>

open class RemoveOnlyCollectionImpl<T>(private val kotlin: KotlinMutableCollection<T>): CollectionViewImpl<T>(kotlin), RemoveOnlyCollection<T> {
	override val CollectionView<T>.kotlin: KotlinMutableCollection<T>
		get() = this@RemoveOnlyCollectionImpl.kotlin
}

open class MutableCollectionImpl<T>(kotlin: KotlinMutableCollection<T>): RemoveOnlyCollectionImpl<T>(kotlin), MutableCollection<T>
