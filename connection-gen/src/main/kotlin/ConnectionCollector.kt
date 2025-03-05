/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen

import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind.IMMUTABLE
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind.MUTABLE
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind.VIEW

/**
 * The main DSL for the Connection Generator.
 */
fun ConnectionGeneration.collect() {
	collectionNamed("Collection") {
		allKinds()
	}

	collectionNamed("SequencedCollection") {
		allKinds {
			kotlin = "java.util.SequencedCollection"
		}
	}

	collectionNamed("List") {
		standardKinds()
		kind(VIEW) {
			adapters.asKotlin {
				create(to = "java.util.SequencedCollection", name = "asSequencedKotlin") {
					unchecked = true
					docs = """
						/**
						 * Returns a Kotlin collection that delegates to this collection.
						 *
						 * @apiNote
						 * This extension exists since [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].
						 */
					""".trimIndent()
				}
			}
		}
	}

	collectionNamed("Set") {
		allKinds()
	}

	collectionNamed("SequencedSet") {
		allKinds {
			kotlin = "java.util.SequencedSet"
		}
	}

	collectionNamed("NavigableSet") {
		allKinds {
			kotlin = "java.util.NavigableSet"
		}
	}

	mapNamed("Map") {
		standardKinds()
	}

	mapNamed("SequencedMap") {
		standardKinds {
			kotlin = "java.util.SequencedMap"
		}
	}

	mapNamed("NavigableMap") {
		standardKinds {
			kotlin = "java.util.NavigableMap"
		}
	}
}

private fun ConnectionGeneration.ConnectionType.allKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	kinds(*allConnectionKinds, configuration = configuration)

private fun ConnectionGeneration.ConnectionType.standardKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	kinds(*standardKinds, configuration = configuration)

private val allConnectionKinds = ConnectionKind.entries.toTypedArray()

private val standardKinds = arrayOf(VIEW, IMMUTABLE, MUTABLE)
