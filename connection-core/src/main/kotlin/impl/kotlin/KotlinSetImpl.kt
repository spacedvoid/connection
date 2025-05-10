/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.impl.kotlin

import io.github.spacedvoid.connection.*

open class KotlinSetImpl<T>(connection: SetView<T>): KotlinCollectionImpl<T>(connection), Set<T>

open class KotlinMutableSetImpl<T>(connection: RemoveOnlySet<T>): KotlinMutableCollectionImpl<T>(connection), MutableSet<T>
