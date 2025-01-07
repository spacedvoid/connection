package io.github.spacedvoid.connection.gen

import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind

/**
 * The main DSL for the Connection Generator.
 */
fun ConnectionGeneration.collect() {
	collectionNamed("Collection") {
		kinds(*ConnectionKind.all)
	}

	collectionNamed("SequencedCollection") {
		kinds(*ConnectionKind.all)
	}

	collectionNamed("List") {
		kinds(ConnectionKind.VIEW, ConnectionKind.IMMUTABLE, ConnectionKind.MUTABLE)
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
		kinds(*ConnectionKind.all)
	}

	collectionNamed("SequencedSet") {
		kinds(*ConnectionKind.all)
	}

	collectionNamed("NavigableSet") {
		kinds(*ConnectionKind.all)
	}

	mapNamed("Map") {
		kinds(ConnectionKind.VIEW, ConnectionKind.IMMUTABLE, ConnectionKind.MUTABLE)
	}

	mapNamed("SequencedMap") {
		kinds(ConnectionKind.VIEW, ConnectionKind.IMMUTABLE, ConnectionKind.MUTABLE)
	}

	mapNamed("NavigableMap") {
		kinds(ConnectionKind.VIEW, ConnectionKind.IMMUTABLE, ConnectionKind.MUTABLE)
	}
}
