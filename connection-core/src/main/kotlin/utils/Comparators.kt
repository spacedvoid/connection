/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.utils

/**
 * Returns a [Comparator] that casts the objects to [Comparable] and invokes [Comparable.compareTo].
 */
@Suppress("UNCHECKED_CAST")
fun <T> naturalOrdering(): Comparator<T> = NaturalOrdering as Comparator<T>

private object NaturalOrdering : Comparator<Any?> {
	@Suppress("UNCHECKED_CAST")
	override fun compare(o1: Any?, o2: Any?): Int {
		if(o1 == null || o2 == null) throw NullPointerException()
		if(o1 !is Comparable<*> || o2 !is Comparable<*>) throw TypeCastException("Non-comparable objects: $o1 and $o2")
		return (o1 as Comparable<Any>).compareTo(o2)
	}
}
