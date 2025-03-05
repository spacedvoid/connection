/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen

import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind

/**
 * The main DSL for the Connection Generator.
 */
fun ConnectionGeneration.collect() {
	collectionNamed("Collection") {
		allKinds()
	}

	collectionNamed("SequencedCollection") {
		allKinds()
	}

	collectionNamed("List") {
		standardKinds()
		kind(ConnectionKind.VIEW) {
			adapters {
				asKotlin {
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
	}

	collectionNamed("Set") {
		allKinds()
	}

	collectionNamed("SequencedSet") {
		allKinds()
	}

	collectionNamed("NavigableSet") {
		allKinds()
	}

	mapNamed("Map") {
		standardKinds()
	}

	mapNamed("SequencedMap") {
		standardKinds()
	}

	mapNamed("NavigableMap") {
		standardKinds()
	}
}

private fun ConnectionGeneration.ConnectionType.allKinds() = kinds(*allConnectionKinds)

private fun ConnectionGeneration.ConnectionType.standardKinds() = kinds(*standardKinds)

private val allConnectionKinds = ConnectionKind.entries.toTypedArray()

private val standardKinds = arrayOf(ConnectionKind.VIEW, ConnectionKind.IMMUTABLE, ConnectionKind.MUTABLE)
