package io.github.spacedvoid.connection.gen.dsl

import java.util.EnumMap

@DslMarker
annotation class ConnectionDSL

interface Configurable {
	operator fun <T: Configurable> T.invoke(configuration: T.() -> Unit) = this.configuration()
}

operator fun <T: Configurable> T?.invoke(configuration: T.() -> Unit) = this?.configuration()

class ConnectionGeneration: Configurable {
	@ConnectionDSL
	inner class ConnectionType(val name: String, val typeArgCount: Int): Configurable {
		@ConnectionDSL
		inner class ConnectionTypeKind internal constructor(val kind: ConnectionKind): Configurable {
			var name: String = when(this.kind) {
				ConnectionKind.VIEW -> "${this@ConnectionType.name}View"
				ConnectionKind.IMMUTABLE -> this@ConnectionType.name
				ConnectionKind.REMOVE_ONLY -> "RemoveOnly${this@ConnectionType.name}"
				ConnectionKind.MUTABLE -> "Mutable${this@ConnectionType.name}"
			}
			var inPackage: String = "io.github.spacedvoid.connection"
			var impl: String = this.name + "Impl"
				get() = when(this.customImpl) {
					true -> field
					false -> this.name + "Impl"
				}
				set(value) {
					field = value
					this.customImpl = true
				}
			private var customImpl: Boolean = false

			val adapters = Adapters()
		}

		val kinds: EnumMap<ConnectionKind, ConnectionTypeKind> = EnumMap(ConnectionKind::class.java)
		val conversions: Conversions = Conversions()

		fun kinds(vararg kinds: ConnectionKind) {
			kinds.forEach {
				ConnectionTypeKind(it).apply {
					this@ConnectionType.kinds[it] = this
				}
			}
		}

		fun kind(kind: ConnectionKind, configuration: ConnectionTypeKind.() -> Unit = {}) {
			ConnectionTypeKind(kind).apply {
				configuration()
				this@ConnectionType.kinds[kind] = this
			}
		}
	}

	val connections: MutableMap<String, ConnectionType> = mutableMapOf()

	fun collectionNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		findOrAdd(name, 1).apply(configuration)

	fun mapNamed(name: String, configuration: ConnectionType.() -> Unit = {}): ConnectionType =
		findOrAdd(name, 2).apply(configuration)

	private fun findOrAdd(name: String, typeArgCount: Int): ConnectionType {
		val type = this.connections.computeIfAbsent(name) { ConnectionType(name, typeArgCount) }
		check(type.typeArgCount == typeArgCount) { "Requested type argument count $typeArgCount differs from found ${type.typeArgCount}" }
		return type
	}
}

val ConnectionGeneration.ConnectionType.ConnectionTypeKind.qualifiedName: String
	get() = "${this.inPackage}.${this.name}"
