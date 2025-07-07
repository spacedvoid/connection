/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:JvmMultifileClass
@file:JvmName("Connection")

package io.github.spacedvoid.connection

import java.util.stream.Stream

/**
 * Returns a new sequential stream for this collection, using [CollectionView.spliterator].
 */
@StreamSupport
fun <T> CollectionView<T>.stream(): Stream<T> = java.util.stream.StreamSupport.stream(spliterator(), false)
