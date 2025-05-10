/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen.dsl

/**
 * Contains *adapters*, which convert collections between Connection and Kotlin.
 */
@ConnectionDSL
class Adapters: Configurable {
	/**
	 * A collection for an adapter type.
	 */
	@ConnectionDSL
	inner class AdapterCollection: Configurable {
		/**
		 * The default adapter generated for all kinds.
		 * Its [target type][Adapter.kotlin] will be the type of the `kotlin` property,
		 * and the [name][Adapter.name] will be determined based on the kind of the Connection.
		 *
		 * Assign `null` to not generate the default adapter.
		 * The behavior of the generator when assigning another adapter is not defined.
		 */
		var default: Adapter? = Adapter()

		/**
		 * Contains any other custom adapters created by the DSL.
		 */
		@DslInternal
		val extra: MutableList<Adapter> = mutableListOf()

		/**
		 * Creates a new adapter, different from the [default] adapter.
		 */
		fun create(name: String, to: KotlinType, configuration: Adapter.() -> Unit = {}): Adapter =
			Adapter(to, name).apply {
				configuration()
				this@AdapterCollection.extra += this
			}
	}

	/**
	 * Adapters that convert Connections to Kotlin collections.
	 */
	val asKotlin: AdapterCollection = AdapterCollection()
	/**
	 * Adapters that convert Kotlin collections to Connection.
	 */
	val asConnection: AdapterCollection = AdapterCollection()
}

/**
 * Represents an adapter.
 * `null` properties will be replaced with default values, which might cause compilation errors.
 */
@ConnectionDSL
data class Adapter @DslInternal constructor(var kotlin: KotlinType? = null, var name: String? = null, var docs: String? = null, var unchecked: Boolean = false): Configurable
