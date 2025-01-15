/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableSet
import io.github.spacedvoid.connection.Set
import kotlin.collections.MutableSet as KotlinMutableSet
import kotlin.collections.Set as KotlinSet

open class SetViewImpl<T>(private val kotlin: KotlinSet<T>): CollectionViewImpl<T>(kotlin), SetView<T> {
	override val CollectionView<T>.kotlin: KotlinSet<T>
		get() = this@SetViewImpl.kotlin
}

open class SetImpl<T>(kotlin: KotlinSet<T>): SetViewImpl<T>(kotlin), Set<T>

open class RemoveOnlySetImpl<T>(private val kotlin: KotlinMutableSet<T>): SetViewImpl<T>(kotlin), RemoveOnlySet<T> {
	override val CollectionView<T>.kotlin: KotlinMutableSet<T>
		get() = this@RemoveOnlySetImpl.kotlin
}

open class MutableSetImpl<T>(kotlin: KotlinMutableSet<T>): RemoveOnlySetImpl<T>(kotlin), MutableSet<T>
