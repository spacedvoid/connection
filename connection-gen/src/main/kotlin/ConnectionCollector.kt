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
import io.github.spacedvoid.connection.gen.dsl.invoke

/**
 * The main DSL for the Connection generator.
 */
fun ConnectionGeneration.collect() {
	collectionNamed("Collection") {
		allKinds()
	}

	val sequencedCol = collectionNamed("SequencedCollection") {
		allKinds {
			kotlin = KotlinType<java.util.SequencedCollection<*>>()
		}
	}

	collectionNamed("List") {
		standardKinds()
		kind(VIEW) {
			adapters.asKotlin {
				create {
					name = "asSequencedKotlin"
					kotlin = sequencedCol.kind(VIEW).kotlin!!
					unchecked = true
					docs = """
						/**
						 * Returns a Kotlin collection that delegates to this collection.
						 *
						 * This extension exists because [kotlin.collections.List] does not inherit from [java.util.SequencedCollection].
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
			kotlin = KotlinType<java.util.SequencedSet<*>>()
		}
	}

	collectionNamed("NavigableSet") {
		allKinds {
			kotlin = KotlinType<java.util.NavigableSet<*>>()
		}
	}

	mapNamed("Map") {
		standardKinds()
	}

	mapNamed("SequencedMap") {
		standardKinds {
			kotlin = KotlinType<java.util.SequencedMap<*, *>>()
		}
	}

	mapNamed("NavigableMap") {
		standardKinds {
			kotlin = KotlinType<java.util.NavigableMap<*, *>>()
		}
	}

	collectionNamed("Stack") {
		kind(MUTABLE) {
			name = "Stack"
			kotlin = null
		}
	}

	collectionNamed("Queue") {
		kind(MUTABLE) {
			name = "Queue"
			kotlin = null
		}
	}

	collectionNamed("Deque") {
		kind(MUTABLE) {
			name = "Deque"
			kotlin = KotlinType<java.util.Deque<*>>()
			adapters.asKotlin.default = null
		}
	}
}

private inline fun <reified T: Any> KotlinType(): KotlinType = KotlinType(T::class.simpleName!!, T::class.java.packageName)

private inline fun ConnectionGeneration.ConnectionType.allKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	allConnectionKinds.forEach { kind(it, configuration) }

private inline fun ConnectionGeneration.ConnectionType.standardKinds(configuration: ConnectionGeneration.ConnectionType.ConnectionTypeKind.() -> Unit = {}) =
	standardKinds.forEach { kind(it, configuration) }

private val allConnectionKinds = ConnectionKind.entries.toTypedArray()

private val standardKinds = arrayOf(VIEW, IMMUTABLE, MUTABLE)
