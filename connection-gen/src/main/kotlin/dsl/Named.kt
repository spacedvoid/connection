package io.github.spacedvoid.connection.gen.dsl

/**
 * Represents that this object represents a type(class or interface).
 */
@ConnectionDSL
interface Named: Configurable {
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
 * The qualified name of the type, based on the [name][Named.name] and [package][Named.inPackage].
 */
val Named.qualifiedName: String
	get() = "${this.inPackage}.${this.name}"
