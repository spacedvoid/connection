package io.github.spacedvoid.connection.gen.dsl

@ConnectionDSL
class Conversions: Configurable {
	var removeOnly: Conversion? = Conversion()
	var view: Conversion? = Conversion()
}

@ConnectionDSL
data class Conversion(var to: ConnectionGeneration.ConnectionType? = null, var name: String? = null, var docs: String? = null): Configurable
