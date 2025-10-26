/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents *conversions*, which convert Connections between their types and kinds.
 *
 * This class only allow conversions that maintain types.
 * Conversions that also change types should be manually created.
 */
@ConnectionDSL
class Conversions @DslInternal constructor(): Configurable {
	/**
	 * Conversions that mask remove-only Connections to prevent downcasting.
	 *
	 * The behavior of the generator when assigning another conversion(other than `null`) is not defined.
	 */
	var removeOnly: Conversion? = Conversion()
	/**
	 * Conversions that mask view Connections to prevent downcasting.
	 *
	 * The behavior of the generator when assigning another conversion(other than `null`) is not defined.
	 */
	var view: Conversion? = Conversion()
}

/**
 * Represents a conversion.
 * Unless noted otherwise, `null` properties will be replaced with default values.
 */
@ConnectionDSL
data class Conversion @DslInternal constructor(var name: String? = null, var docs: String? = null): Configurable
