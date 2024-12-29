package io.github.spacedvoid.connection.gen.dsl

enum class ConnectionKind {
	VIEW, IMMUTABLE, REMOVE_ONLY, MUTABLE;

	companion object {
		val all: Array<ConnectionKind>
			get() = ConnectionKind.entries.toTypedArray()
	}
}
