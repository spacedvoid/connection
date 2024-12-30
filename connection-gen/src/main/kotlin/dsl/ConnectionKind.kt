package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents the kind of Connection types.
 */
enum class ConnectionKind {
	VIEW, IMMUTABLE, REMOVE_ONLY, MUTABLE;

	companion object {
		/**
		 * Shortcut for [ConnectionGeneration.ConnectionType.kinds] which need all kinds.
		 */
		val all: Array<ConnectionKind>
			get() = ConnectionKind.entries.toTypedArray()
	}
}
