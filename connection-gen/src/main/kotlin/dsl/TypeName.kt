/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents that this object represents a type(class or interface).
 */
@ConnectionDSL
interface TypeName: Configurable {
	/**
	 * The simple name of the type.
	 */
	var name: String
	/**
	 * The package containing the type, separated with dots(`.`).
	 * The last dot is omitted.
	 */
	var inPackage: String
}

/**
 * The qualified name of the type, based on the [name][TypeName.name] and [package][TypeName.inPackage].
 */
val TypeName.qualifiedName: String
	get() = "${this.inPackage}.${this.name}"
