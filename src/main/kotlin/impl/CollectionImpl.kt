/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("DEPRECATION")

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.Collection
import io.github.spacedvoid.connection.MutableCollection

open class CollectionViewImpl<T>(private val kotlin: KotlinCollection<T>): CollectionView<T> {
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val CollectionView<T>.kotlin: KotlinCollection<T>
		get() = this@CollectionViewImpl.kotlin

	@Suppress("UNCHECKED_CAST")
	override fun equals(other: Any?): Boolean {
		if(other !is CollectionView<*>) return false
		other as CollectionView<T>
		return this.kotlin == with(other) { other.kotlin }
	}

	override fun hashCode(): Int = this.kotlin.hashCode()
}

open class CollectionImpl<T>(kotlin: KotlinCollection<T>): CollectionViewImpl<T>(kotlin), Collection<T>

open class RemoveOnlyCollectionImpl<T>(private val kotlin: KotlinMutableCollection<T>): CollectionViewImpl<T>(kotlin), RemoveOnlyCollection<T> {
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val CollectionView<T>.kotlin: KotlinMutableCollection<T>
		get() = this@RemoveOnlyCollectionImpl.kotlin
}

open class MutableCollectionImpl<T>(kotlin: KotlinMutableCollection<T>): RemoveOnlyCollectionImpl<T>(kotlin), MutableCollection<T>
