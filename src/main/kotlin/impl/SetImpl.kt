/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("DEPRECATION")

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableSet
import io.github.spacedvoid.connection.Set

open class SetViewImpl<T>(private val kotlin: KotlinSet<T>): CollectionViewImpl<T>(kotlin), SetView<T> {
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val CollectionView<T>.kotlin: KotlinSet<T>
		get() = this@SetViewImpl.kotlin

	@Suppress("UNCHECKED_CAST")
	override fun equals(other: Any?): Boolean {
		if(other !is SetView<*>) return false
		other as SetView<T>
		return this.kotlin == with(other) { other.kotlin }
	}

	override fun hashCode(): Int = this.kotlin.hashCode()
}

open class SetImpl<T>(kotlin: KotlinSet<T>): SetViewImpl<T>(kotlin), Set<T>

open class RemoveOnlySetImpl<T>(private val kotlin: KotlinMutableSet<T>): SetViewImpl<T>(kotlin), RemoveOnlySet<T> {
	@Deprecated("This property is error-prone, and not safe to use. Replace it with API calls such as `asKotlin` instead.")
	override val CollectionView<T>.kotlin: KotlinMutableSet<T>
		get() = this@RemoveOnlySetImpl.kotlin
}

open class MutableSetImpl<T>(kotlin: KotlinMutableSet<T>): RemoveOnlySetImpl<T>(kotlin), MutableSet<T>
