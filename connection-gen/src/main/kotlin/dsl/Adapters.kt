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
class Adapters @DslInternal constructor(): Configurable {
	/**
	 * A collection for an adapter type.
	 */
	@ConnectionDSL
	inner class AdapterCollection @DslInternal constructor(): Configurable {
		/**
		 * The default adapter generated for all kinds.
		 *
		 * Assign `null` to not generate the default adapter.
		 * The behavior of the generator when assigning another adapter is not defined.
		 */
		var default: Adapter? = Adapter(isExtra = false)

		/**
		 * Contains any other custom adapters created by the DSL.
		 */
		@DslInternal
		val extra: MutableList<Adapter> = mutableListOf()

		/**
		 * Creates a new adapter, different from the [default] adapter.
		 */
		inline fun create(name: String, to: KotlinType, configuration: Adapter.() -> Unit = {}): Adapter =
			Adapter(to, name, isExtra = true).apply {
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
 * `null` properties will be replaced with default values from [ConnectionTypeKind][ConnectionGeneration.ConnectionType.ConnectionTypeKind], which might cause compilation errors.
 *
 * @param [unchecked] Controls whether the collection object needs unchecked casting.
 *                    Setting it to `true` additionally inserts `@Suppress("UNCHECKED_CAST")` and a cast to the [kotlin] type.
 */
@ConnectionDSL
data class Adapter @DslInternal constructor(var kotlin: KotlinType? = null, var name: String? = null, var docs: String? = null, var unchecked: Boolean = false, @property:DslInternal val isExtra: Boolean): Configurable
