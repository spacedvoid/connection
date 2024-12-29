package io.github.spacedvoid.connection.gen.dsl

@ConnectionDSL
class Adapters: Configurable {
	@ConnectionDSL
	inner class AdapterCollection: Configurable {
		var default: Adapter? = Adapter()

		internal val extra: MutableList<Adapter> = mutableListOf()

		fun create(name: String, to: String, configuration: Adapter.() -> Unit = {}): Adapter =
			Adapter(to, name).apply {
				configuration()
				this@AdapterCollection.extra += this
			}
	}

	val asKotlin: AdapterCollection = AdapterCollection()
	val asConnection: AdapterCollection = AdapterCollection()
}

@ConnectionDSL
class Adapter(var kotlin: String? = null, var name: String? = null, var docs: String? = null, var unchecked: Boolean = false): Configurable
