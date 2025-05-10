/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl

import io.github.spacedvoid.connection.*
import io.github.spacedvoid.connection.MutableSet
import io.github.spacedvoid.connection.Set

open class SetViewImpl<T>(override val kotlin: kotlin.collections.Set<T>): CollectionViewImpl<T>(kotlin), SetView<T> {
}

open class SetImpl<T>(kotlin: kotlin.collections.Set<T>): SetViewImpl<T>(kotlin), Set<T>

open class RemoveOnlySetImpl<T>(override val kotlin: kotlin.collections.MutableSet<T>): RemoveOnlyCollectionImpl<T>(kotlin), RemoveOnlySet<T> {
}

open class MutableSetImpl<T>(kotlin: kotlin.collections.MutableSet<T>): MutableCollectionImpl<T>(kotlin), MutableSet<T> {
}
