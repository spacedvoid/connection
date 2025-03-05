/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen.dsl

import java.util.EnumMap

@DslMarker
annotation class ConnectionDSL

/**
 * Marker annotation to prevent usage of DSL-internal elements from the DSL.
 */
@RequiresOptIn("This element is internal to the DSL.")
annotation class DslInternal

/**
 * Allows objects to be configured with a DSL style.
 */
interface Configurable {
	/**
	 * Configures the object.
	 * The receiver of the [configuration] is same as the object used to configure.
	 */
	operator fun <T: Configurable> T.invoke(configuration: T.() -> Unit) = this.configuration()
}

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

/**
 * The context object for the Connection Generator DSL.
 */
class ConnectionGeneration @DslInternal internal constructor(): Configurable {
	/**
	 * Represents a Connection type family, such as `List` or `Map`.
	 * Kinds are defined by [kinds].
	 */
	@ConnectionDSL
	inner class ConnectionType @DslInternal internal constructor(val name: String, @property:DslInternal internal val typeArgCount: Int): Configurable {
		/**
		 * Represents a Connection with a type and [kind][ConnectionKind], such as `MutableList`.
		 */
		@ConnectionDSL
		inner class ConnectionTypeKind @DslInternal internal constructor(val kind: ConnectionKind): Configurable, Named {
			override var name: String = when(this.kind) {
				ConnectionKind.VIEW -> "${this@ConnectionType.name}View"
				ConnectionKind.IMMUTABLE -> this@ConnectionType.name
				ConnectionKind.REMOVE_ONLY -> "RemoveOnly${this@ConnectionType.name}"
				ConnectionKind.MUTABLE -> "Mutable${this@ConnectionType.name}"
			}
			override var inPackage: String = "io.github.spacedvoid.connection"

			/**
			 * The default implementation of this typekind.
			 *
			 * Unless the value is customized, it will always be [name]`Impl`.
			 * Once set, it will always be the customized name.
			 */
			val impl: Named = object: Named {
				override var name: String = this@ConnectionTypeKind.name + "Impl"
					get() = if(this.customName) field else this@ConnectionTypeKind.name + "Impl"
					set(value) {
						this.customName = true
						field = value
					}
				override var inPackage: String = "io.github.spacedvoid.connection.impl"

				private var customName = false
			}

			/**
			 * The adapters for this typekind.
			 */
			val adapters = Adapters()
		}

		/**
		 * Contains all kinds that this type family has.
		 */
		@DslInternal
		val kinds: EnumMap<ConnectionKind, ConnectionTypeKind> = EnumMap(ConnectionKind::class.java)
		/**
		 * The conversions for this type.
		 */
		val conversions: Conversions = Conversions()

		/**
		 * Defines the [kinds] that this family has, and configures each kind.
		 * Previous configurations with the [kind] will be preserved.
		 */
		fun kinds(vararg kinds: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit = {}) {
			kinds.forEach {
				this@ConnectionType.kinds.computeIfAbsent(it) { ConnectionTypeKind(it) }.apply(configuration)
			}
		}

		/**
		 * Defines a [kind] that this family has, and configures it.
		 * Previous configurations with the [kind] will be preserved.
		 */
		fun kind(kind: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit = {}) {
			this@ConnectionType.kinds.computeIfAbsent(kind) { ConnectionTypeKind(kind) }.apply(configuration)
		}
	}

	/**
	 * Contains all connections specified by [collectionNamed] and [mapNamed].
	 * Mapped by their [names][ConnectionType.name], since no two types should have the same name.
	 */
	@DslInternal
	val connections: MutableMap<String, ConnectionType> = mutableMapOf()

	/**
	 * Creates or retrieves a Connection type which has one type argument.
	 * The [name] must be unique from all other types.
	 *
	 * Additionally [Configurable] by the [configuration].
	 */
	fun collectionNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		findOrAdd(name, 1).apply(configuration)

	/**
	 * Creates or retrieves a Connection type which has two type arguments.
	 * The [name] must be unique from all other types.
	 *
	 * Additionally [Configurable] by the [configuration].
	 */
	fun mapNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		findOrAdd(name, 2).apply(configuration)

	/**
	 * Creates and/or returns the Connection type with the name.
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different [typeArgCount] already exists.
	 */
	private fun findOrAdd(name: String, typeArgCount: Int): ConnectionType {
		val type = this.connections.computeIfAbsent(name) { ConnectionType(name, typeArgCount) }
		check(type.typeArgCount == typeArgCount) { "Requested type argument count $typeArgCount differs from found ${type.typeArgCount}" }
		return type
	}
}
