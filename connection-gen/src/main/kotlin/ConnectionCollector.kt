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
import io.github.spacedvoid.connection.gen.dsl.KotlinType

/**
 * The main DSL for the Connection Generator.
 */
fun ConnectionGeneration.collect() {
	collectionNamed("Collection") {
		allKinds()
	}

	collectionNamed("SequencedCollection") {
		allKinds {
			kotlinType<java.util.SequencedCollection<*>> {
				name = this@collectionNamed.name
			}
		}
	}

	collectionNamed("List") {
		standardKinds()
		kind(VIEW) {
			adapters.asKotlin {
				create(to = collectionNamed("SequencedCollection").kind(VIEW).kotlin!!, name = "asSequencedKotlin") {
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
			kotlinType<java.util.SequencedSet<*>> {
				name = this@collectionNamed.name
			}
		}
	}

	collectionNamed("NavigableSet") {
		allKinds {
			kotlinType<java.util.NavigableSet<*>> {
				name = this@collectionNamed.name
			}
		}
	}

	mapNamed("Map") {
		standardKinds()
	}

	mapNamed("SequencedMap") {
		standardKinds {
			kotlinType<java.util.SequencedMap<*, *>> {
				name = this@mapNamed.name
			}
		}
	}

	mapNamed("NavigableMap") {
		standardKinds {
			kotlinType<java.util.NavigableMap<*, *>> {
				name = this@mapNamed.name
			}
		}
	}
}

private inline fun <reified T: Any> ConnectionGeneration.ConnectionType.ConnectionTypeKind.kotlinType(configuration: KotlinType.() -> Unit = {}) {
	kotlin = KotlinType(T::class.simpleName!!, T::class.java.packageName).apply(configuration)
}

private inline fun ConnectionGeneration.ConnectionType.allKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	kinds(*allConnectionKinds, configuration = configuration)

private inline fun ConnectionGeneration.ConnectionType.standardKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	kinds(*standardKinds, configuration = configuration)

private val allConnectionKinds = ConnectionKind.entries.toTypedArray()

private val standardKinds = arrayOf(VIEW, IMMUTABLE, MUTABLE)
