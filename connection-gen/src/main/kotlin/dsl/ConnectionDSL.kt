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
 * Class used for Kotlin representations of Connections.
 */
class KotlinType(override var name: String, override var inPackage: String): Named {
	/**
	 * Alike [ConnectionGeneration.ConnectionType.ConnectionTypeKind.impl],
	 * unless the value is customized, it will always be `io.github.spacedvoid.connection.impl.kotlin.Kotlin`[name]`Impl`.
	 * Once set, it will always be the customized name.
	 */
	var impl: Named = ConfigurableNamed("io.github.spacedvoid.connection.impl.kotlin") { "Kotlin${this.name}Impl" }
}

/**
 * Class to allow customizable name.
 *
 * The [name] will be the provided [defaultName] until a custom name is set.
 */
private class ConfigurableNamed(override var inPackage: String, private val defaultName: () -> String): Named {
	override var name: String = this.defaultName()
		get() = if(this.customName) field else this.defaultName()
		set(value) {
			this.customName = true
			field = value
		}

	private var customName = false
}

/**
 * The context object for the Connection Generator DSL.
 */
class ConnectionGeneration @DslInternal constructor(): Configurable {
	/**
	 * Represents a Connection type family, such as `List` or `Map`.
	 * Kinds are defined by [kinds].
	 *
	 * @param name The representative name of this type family.
	 * @param typeArgCount The number of type parameters that the kinds of this family requires.
	 */
	@ConnectionDSL
	inner class ConnectionType @DslInternal constructor(val name: String, @property:DslInternal val typeArgCount: Int): Configurable {
		/**
		 * Represents a Connection with a type and [kind][ConnectionKind], such as `MutableList`.
		 */
		@ConnectionDSL
		inner class ConnectionTypeKind @DslInternal constructor(val kind: ConnectionKind): Configurable, Named {
			/**
			 * The simple name of the type.
			 *
			 * By default, it will be based on its [kind]:
			 * - [VIEW][ConnectionKind.VIEW]: [name][ConnectionType.name]`View`
			 * - [IMMUTABLE][ConnectionKind.IMMUTABLE]: [name][ConnectionType.name]
			 * - [REMOVE_ONLY][ConnectionKind.REMOVE_ONLY]: `RemoveOnly`[name][ConnectionType.name]
			 * - [MUTABLE][ConnectionKind.MUTABLE]: `Mutable`[name][ConnectionType.name]
			 */
			override var name: String = when(this.kind) {
				ConnectionKind.VIEW -> "${this@ConnectionType.name}View"
				ConnectionKind.IMMUTABLE -> this@ConnectionType.name
				ConnectionKind.REMOVE_ONLY -> "RemoveOnly${this@ConnectionType.name}"
				ConnectionKind.MUTABLE -> "Mutable${this@ConnectionType.name}"
			}

			/**
			 * The package containing the type, separated with dots(.).
			 * The last dot is omitted.
			 *
			 * By default, it will be `io.github.spacedvoid.connection`.
			 */
			override var inPackage: String = "io.github.spacedvoid.connection"

			/**
			 * The default implementation of this typekind.
			 *
			 * Unless the value is customized, it will always be `io.github.spacedvoid.connection.impl.`[name]`Impl`.
			 * Once set, it will always be the customized name.
			 */
			var impl: Named = ConfigurableNamed("io.github.spacedvoid.connection.impl") { this.name + "Impl" }

			/**
			 * The Kotlin equivalent of this collection.
			 * Used for the default value of [Adapter.kotlin].
			 *
			 * Setting this to `null` will not generate any [default adapters][Adapters.AdapterCollection.default],
			 * and any [extra][Adapters.AdapterCollection.extra] adapters without the [Adapter.kotlin] being specified will cause an [IllegalArgumentException].
			 *
			 * By default, it will be `kotlin.collections.`[name], additionally prepending `Mutable` to the [name][Named.name] if the [kind] is a mutable kind.
			 * The [KotlinType.impl] will be `io.github.spacedvoid.connection.impl.kotlin.`[name]`Impl`.
			 */
			var kotlin: KotlinType? = KotlinType("${
				when(this.kind) {
					ConnectionKind.REMOVE_ONLY, ConnectionKind.MUTABLE -> "Mutable"
					else -> ""
				}
			}${this@ConnectionType.name}", "kotlin.collections")

			/**
			 * The adapters for this typekind.
			 */
			val adapters = Adapters()

			/**
			 * Allows access to the [type][ConnectionType] of this typekind.
			 */
			@DslInternal
			val type: ConnectionType = this@ConnectionType
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
		 * Defines a [kind] that this family has, and configures it.
		 * Previous configurations with the [kind] will be preserved.
		 */
		inline fun kind(kind: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit = {}): ConnectionTypeKind =
			this.kinds.computeIfAbsent(kind) { ConnectionTypeKind(kind) }.apply(configuration)

		/**
		 * Defines a [kind] that this family has, and configures it.
		 * Previous configurations with the [kinds] will be preserved.
		 */
		inline fun kinds(vararg kinds: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit) {
			kinds.forEach { kind(it, configuration) }
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
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different type argument count already exists.
	 */
	inline fun collectionNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		named(name, 1, configuration)

	/**
	 * Creates or retrieves a Connection type which has two type arguments.
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different type argument count already exists.
	 */
	inline fun mapNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		named(name, 2, configuration)

	/**
	 * Creates and/or returns the Connection type with the name.
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different [typeArgCount] already exists.
	 */
	inline fun named(name: String, typeArgCount: Int, configuration: ConnectionType.() -> Unit = {}): ConnectionType {
		val type = this.connections.computeIfAbsent(name) { ConnectionType(name, typeArgCount) }
		check(type.typeArgCount == typeArgCount) { "Requested type argument count $typeArgCount differs from found ${type.typeArgCount}" }
		return type.apply(configuration)
	}
}
