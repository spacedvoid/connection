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
 *
 * @see invoke
 */
interface Configurable

/**
 * Configures the object.
 * The receiver of the [configuration] is same as the object used to configure.
 */
operator fun <T: Configurable> T.invoke(configuration: T.() -> Unit) = this.configuration()

/**
 * Class used for Kotlin representations of Connections.
 */
class KotlinType(override var name: String, override var inPackage: String): TypeName {
	/**
	 * The implementation of this Kotlin collection for adapters.
	 *
	 * By default, this will be `io.github.spacedvoid.connection.impl.kotlin.`[name]`Impl`.
	 */
	var impl: ComputedTypeName = ComputedTypeName("io.github.spacedvoid.connection.impl.kotlin") { "Kotlin${this.name}Impl" }
}

/**
 * Allows customizable names.
 *
 * The [name] will return the invocation of [defaultName] until a custom name is set.
 */
class ComputedTypeName(override var inPackage: String, private val defaultName: () -> String): TypeName {
	override var name: String = ""
		get() = if(this.customName) field else this.defaultName()
		set(value) {
			this.customName = true
			field = value
		}

	/**
	 * Returns whether the [name] is customized and will not change unless it is manually set to another value.
	 */
	var customName = false
		private set
}

/**
 * The context object for the Connection Generator DSL.
 */
class ConnectionGeneration @DslInternal constructor(): Configurable {
	/**
	 * Contains all connections specified by [collectionNamed] and [mapNamed].
	 * The keys are their [names][ConnectionType.name], since no two types should have the same name.
	 */
	@DslInternal
	val connections: MutableMap<String, ConnectionType> = mutableMapOf()

	/**
	 * Creates and/or retrieves a Connection type which has one type argument.
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different type argument count already exists.
	 */
	inline fun collectionNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		named(name, 1, configuration)

	/**
	 * Creates and/or retrieves a Connection type which has two type arguments.
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different type argument count already exists.
	 */
	inline fun mapNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		named(name, 2, configuration)

	/**
	 * Creates and/or returns the Connection type with the given [name] and [typeArgCount].
	 * Throws [IllegalArgumentException] if a type with the same name, but with a different [typeArgCount] already exists.
	 */
	inline fun named(name: String, typeArgCount: Int, configuration: ConnectionType.() -> Unit = {}): ConnectionType {
		val type = this.connections.computeIfAbsent(name) { ConnectionType(name, typeArgCount) }
		check(type.typeArgCount == typeArgCount) { "Requested type argument count $typeArgCount differs from found ${type.typeArgCount}" }
		return type.apply(configuration)
	}

	/**
	 * Represents a Connection type family, such as `List` or `Map`.
	 * Kinds are defined by [kinds].
	 *
	 * @property name The representative name of this type family.
	 * @property typeArgCount The number of type parameters that the kinds of this family requires.
	 */
	@ConnectionDSL
	inner class ConnectionType @DslInternal constructor(val name: String, @property:DslInternal val typeArgCount: Int): Configurable {
		/**
		 * Contains all kinds that this type family has.
		 */
		@DslInternal
		val kinds: EnumMap<ConnectionKind, ConnectionTypeKind> = EnumMap(ConnectionKind::class.java)

		/**
		 * The conversions for this type family.
		 */
		val conversions: Conversions = Conversions()

		/**
		 * Defines a [kind] that this family has, and configures it.
		 * Previous configurations with the [kind] will be preserved.
		 */
		inline fun kind(kind: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit = {}): ConnectionTypeKind =
			this.kinds.computeIfAbsent(kind) { ConnectionTypeKind(kind) }.apply(configuration)

		/**
		 * Defines multiple [kinds] that this family has, and configures each.
		 * Previous configurations with each of the kind will be preserved.
		 */
		inline fun kinds(vararg kinds: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit) {
			kinds.forEach { kind(it, configuration) }
		}

		/**
		 * Represents a Connection [kind] in a type family, such as `MutableList`, which is also a type(class or interface).
		 */
		@ConnectionDSL
		inner class ConnectionTypeKind @DslInternal constructor(val kind: ConnectionKind): Configurable, TypeName {
			/**
			 * The simple name of this type.
			 *
			 * By default, it will be based on its [kind]:
			 * - [VIEW][ConnectionKind.VIEW]: [familyName][ConnectionType.name]`View`
			 * - [IMMUTABLE][ConnectionKind.IMMUTABLE]: [familyName][ConnectionType.name]
			 * - [REMOVE_ONLY][ConnectionKind.REMOVE_ONLY]: `RemoveOnly`[familyName][ConnectionType.name]
			 * - [MUTABLE][ConnectionKind.MUTABLE]: `Mutable`[familyName][ConnectionType.name]
			 */
			override var name: String = when(this.kind) {
				ConnectionKind.VIEW -> "${this@ConnectionType.name}View"
				ConnectionKind.IMMUTABLE -> this@ConnectionType.name
				ConnectionKind.REMOVE_ONLY -> "RemoveOnly${this@ConnectionType.name}"
				ConnectionKind.MUTABLE -> "Mutable${this@ConnectionType.name}"
			}

			/**
			 * The package containing this type, separated with dots(`.`).
			 * The last dot is omitted.
			 *
			 * By default, it will be `io.github.spacedvoid.connection`.
			 */
			override var inPackage: String = "io.github.spacedvoid.connection"

			/**
			 * The implementation of this type.
			 *
			 * By default, this will be `io.github.spacedvoid.connection.impl.`[name]`Impl`.
			 */
			var impl: ComputedTypeName = ComputedTypeName("io.github.spacedvoid.connection.impl") { this.name + "Impl" }

			/**
			 * The Kotlin equivalent of this collection.
			 * Used for the default value of [Adapter.kotlin].
			 *
			 * Setting this to `null` will not generate any [default adapters][Adapters.AdapterCollection.default],
			 * and any [extra][Adapters.AdapterCollection.extra] adapters without the [Adapter.kotlin] being specified will cause an [IllegalArgumentException].
			 *
			 * By default, its [TypeName.inPackage] will be `kotlin.collections`,
			 * and its [TypeName.name] will be equivalent with the [family name][ConnectionType.name],
			 * additionally prepending `Mutable` if this [kind] is a mutable kind.
			 * The [TypeName.inPackage] of the [KotlinType.impl] will be `io.github.spacedvoid.connection.impl.kotlin`,
			 * and its [TypeName.name] will be [familyName][ConnectionType.name]`Impl`.
			 */
			var kotlin: KotlinType? = KotlinType(
				when(this.kind) {
					ConnectionKind.REMOVE_ONLY, ConnectionKind.MUTABLE -> "Mutable${this@ConnectionType.name}"
					else -> this@ConnectionType.name
				},
				"kotlin.collections"
			)

			/**
			 * The adapters for this typekind.
			 */
			val adapters: Adapters = Adapters()

			/**
			 * Allows access to the [type][ConnectionType] of this typekind.
			 */
			@DslInternal
			val type: ConnectionType = this@ConnectionType
		}
	}
}

/**
 * Returns a string for a type argument definition.
 * Currently expects up to 2 type arguments; throws [IllegalStateException] if the [typeArgCount][ConnectionGeneration.ConnectionType.typeArgCount] is more than 2.
 */
@DslInternal
val ConnectionGeneration.ConnectionType.typeArgs: String
	get() = when(this.typeArgCount) {
		1 -> "<T>"
		2 -> "<K, V>"
		else -> throw IllegalStateException("Undefined type argument count ${this.typeArgCount}")
	}
