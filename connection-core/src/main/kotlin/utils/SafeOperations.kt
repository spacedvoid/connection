/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.utils

/**
 * Equivalent with [contains][Collection.contains], but returns `false` when catching a [ClassCastException] or a [NullPointerException].
 */
fun <T> Collection<T>.safeContains(element: T): Boolean =
	try {
		contains(element)
	}
	catch(_: ClassCastException) {
		/*
		 * The given element is prohibited to be in this collection because of its type.
		 * While this may become problematic when using with collections-inside-collections
		 * (or anything that does not consider types in [Any.equals]),
		 * collections were never intended to be compared with equality anyway.
		 * And these safe operations should return `false` if the result cannot be determined, simply because "we don't know".
		 */
		false
	}
	catch(_: NullPointerException) {
		// The given is a `null` that this collection prohibits(therefore does not contain).
		false
	}

/**
 * Equivalent with [containsAll][Collection.containsAll], but returns `false` when catching a [ClassCastException] or a [NullPointerException].
 */
fun Collection<*>.safeContainsAll(collection: Collection<*>): Boolean =
	try {
		this.containsAll(collection)
	}
	catch(_: ClassCastException) {
		false
	}
	catch(_: NullPointerException) {
		false
	}

/*
 * TODO: What about other operations?
 * The result becomes undefined when an exception is thrown.
 * We can just use loops for bulk operations, but then there's no meaning for such *bulk* operations.
 */
