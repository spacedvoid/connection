@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents *conversions*, which convert Connections between their types and kinds.
 */
@ConnectionDSL
class Conversions: Configurable {
	/**
	 * Conversions that mask remove-only Connections to prevent downcasting.
	 */
	var removeOnly: Conversion? = Conversion()
	/**
	 * Conversions that mask view Connections to prevent downcasting.
	 */
	var view: Conversion? = Conversion()
}

/**
 * Represents a conversion.
 * `null` properties will be replaced with default values, which might cause compilation errors.
 */
@ConnectionDSL
@ConsistentCopyVisibility
data class Conversion @DslInternal internal constructor(var to: ConnectionGeneration.ConnectionType? = null, var name: String? = null, var docs: String? = null): Configurable
