@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.spacedvoid.connection.gen.ConversionAdapterGenerator.generatePerType
import io.github.spacedvoid.connection.gen.dsl.Adapter
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration.ConnectionType.ConnectionTypeKind
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind
import io.github.spacedvoid.connection.gen.dsl.Conversion
import io.github.spacedvoid.connection.gen.dsl.DslInternal
import io.github.spacedvoid.connection.gen.dsl.KotlinType
import io.github.spacedvoid.connection.gen.dsl.qualifiedName

object ConversionAdapterGenerator {
	fun generate(resolver: Resolver, files: GeneratingFiles, collected: ConnectionGeneration) {
		ConversionsAndAdapters(files).use { files ->
			collected.connections.values.forEach { generatePerType(files, resolver, it) }
		}
	}

	/**
	 * Generates sources based on the [collected][collect] Connection [type].
	 */
	private fun generatePerType(files: ConversionsAndAdapters, resolver: Resolver, type: ConnectionGeneration.ConnectionType) {
		// Conversions
		// View
		type.conversions.view?.let {
			val view = type.kinds[ConnectionKind.VIEW] ?: return@let
			files.view.attachSources(resolver, view.qualifiedName)
			files.view.generateView(it, type.typeArgs, view.name)
		}
		// RemoveOnly
		type.conversions.removeOnly?.let {
			val removeOnly = type.kinds[ConnectionKind.REMOVE_ONLY] ?: return@let
			files.removeOnly.attachSources(resolver, removeOnly.qualifiedName)
			files.removeOnly.generateRemoveOnly(it, type.typeArgs, removeOnly.name)
		}
		type.kinds.values.forEach { kind: ConnectionTypeKind ->
			// Adapters
			// CollectionAsConnection
			(kind.adapters.asConnection.default + kind.adapters.asConnection.extra).forEach {
				if(it == null) return@forEach
				val kotlin = it.kotlin ?: kind.kotlin ?: if(it.isExtra) throw IllegalArgumentException("`kotlin` not set for ColAsCon adapter in $it") else return@forEach
				files.colAsCon.attachSources(resolver, kind.qualifiedName)
				files.colAsCon.generateColAsCon(it, kind, kotlin)
			}
			// ConnectionAsCollection
			(kind.adapters.asKotlin.default + kind.adapters.asKotlin.extra).forEach {
				if(it == null) return@forEach
				val kotlin = it.kotlin ?: kind.kotlin ?: if(it.isExtra) throw IllegalArgumentException("`kotlin` not set for ConAsCol adapter in $it") else return@forEach
				val apiClass = resolver.getClassDeclarationByName(kind.qualifiedName) ?: throw IllegalArgumentException("Class ${kind.qualifiedName} does not exist")
				if(alreadyGenerated(apiClass, kotlin) && it === kind.adapters.asKotlin.default) return@forEach
				apiClass.containingFile?.let { files.conAsCol.attach(it) }
				files.conAsCol.generateConAsCol(it, kind, kotlin)
			}
		}
	}

	/**
	 * Collection of [GeneratingFiles.GeneratingFile] to maintain between iterations of [generatePerType].
	 */
	private class ConversionsAndAdapters(generator: GeneratingFiles): AutoCloseable {
		val view: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "View")

		val removeOnly: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "RemoveOnly")

		val colAsCon: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "CollectionAsConnection").also {
			it += """
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
		}

		val conAsCol: GeneratingFiles.GeneratingFile = generator.GeneratingFile("io.github.spacedvoid.connection", "ConnectionAsCollection").also {
			it += """
				
				import io.github.spacedvoid.connection.impl.*
				import io.github.spacedvoid.connection.impl.kotlin.*
				
			""".trimIndent()
		}

		/**
		 * Closes all streams.
		 *
		 * Has no effect when the streams are already closed.
		 */
		override fun close() {
			this.view.close()
			this.removeOnly.close()
			this.colAsCon.close()
			this.conAsCol.close()
		}
	}

	private val defaultViewDoc = """
		/**
		 * Returns a collection view, converted from this collection.
		 */
	""".trimIndent()

	private val defaultRemoveOnlyDoc = """
		/**
		 * Returns a remove-only collection, converted from this collection.
		 */
	""".trimIndent()

	private val defaultConAsColDocs = """
		/**
		 * Returns a Kotlin collection that delegates to this collection.
		 */
	""".trimIndent()

	private fun defaultColAsConDocs(kind: ConnectionKind): String = when(kind) {
		ConnectionKind.VIEW -> """
			/**
			 * Returns a collection view that delegates to the Kotlin collection.
			 */
		""".trimIndent()
		ConnectionKind.IMMUTABLE -> """
			/**
			 * Returns an immutable collection that delegates to the Kotlin collection.
			 *
			 * @apiNote
			 * Do not use this extension unless it is absolutely sure that it is an immutable collection, or will never be modified elsewhere.
			 * The [Iterator] might throw [ConcurrentModificationException] if the collection is modified.
			 */
		""".trimIndent()
		ConnectionKind.REMOVE_ONLY -> """
			/**
			 * Returns a remove-only collection that delegates to the Kotlin collection.
			 *
			 * @apiNote	
			 * Do not use this extension unless it is absolutely sure that it is a remove-only collection.
			 * Some operations might throw [UnsupportedOperationException].
			 */
		""".trimIndent()
		ConnectionKind.MUTABLE -> """
			/**
			 * Returns a mutable collection that delegates to the Kotlin collection.
			 *
			 * @apiNote
			 * Do not use this extension unless it is absolutely sure that it is a mutable collection.
			 * Some operations might throw [UnsupportedOperationException].
			 */
		""".trimIndent()
	}

	private fun defaultColAsConName(kind: ConnectionKind) = when(kind) {
		ConnectionKind.VIEW -> "asViewConnection"
		ConnectionKind.IMMUTABLE -> "asConnection"
		ConnectionKind.REMOVE_ONLY -> "asRemoveOnlyConnection"
		ConnectionKind.MUTABLE -> "asMutableConnection"
	}

	private val generatedAsKotlin = mutableMapOf<String, MutableSet<KSClassDeclaration>>()

	/**
	 * Returns whether the `asKotlin` adapter is not required to be generated
	 * because another adapter that returns the same type but accepts a supertype has already been generated.
	 */
	private fun alreadyGenerated(apiClass: KSClassDeclaration, kotlin: KotlinType): Boolean {
		val sourceTypes = this.generatedAsKotlin.computeIfAbsent(kotlin.qualifiedName) { mutableSetOf() }
		return apiClass.getAllSuperTypes().any { it.declaration in sourceTypes }.also { if(!it) sourceTypes += apiClass }
	}

	private operator fun <T> T.plus(list: List<T>): List<T> = mutableListOf(this).apply { addAll(list) }

	private val ConnectionGeneration.ConnectionType.typeArgs: String
		get() = when(this.typeArgCount) {
			1 -> "<T>"
			2 -> "<K, V>"
			else -> throw IllegalStateException("Undefined type argument count ${this.typeArgCount}")
		}

	/**
	 * Shortcut for generating Kotlin collection to Connection adapters.
	 */
	private fun GeneratingFiles.GeneratingFile.generateColAsCon(adapter: Adapter, kind: ConnectionTypeKind, kotlin: KotlinType) {
		check(!this.closed)
		val typeParams = kind.type.typeArgs
		this += "\n"
		this += adapter.docs ?: defaultColAsConDocs(kind.kind)
		this += "\nfun $typeParams ${kotlin.qualifiedName}$typeParams.${adapter.name ?: defaultColAsConName(kind.kind)}(): ${kind.name}$typeParams = ${kind.impl.name}(this)\n"
	}

	/**
	 * Shortcut for generating Connection to Kotlin collection adapters.
	 */
	private fun GeneratingFiles.GeneratingFile.generateConAsCol(adapter: Adapter, kind: ConnectionTypeKind, kotlin: KotlinType) {
		check(!this.closed)
		val typeParams = kind.type.typeArgs
		this += "\n"
		this += adapter.docs ?: defaultConAsColDocs
		if(adapter.unchecked) this += "\n@Suppress(\"UNCHECKED_CAST\")"
		this += "\nfun $typeParams ${kind.name}$typeParams.${adapter.name ?: "asKotlin"}(): ${kotlin.qualifiedName}$typeParams = if(this is ${kind.impl.name}$typeParams) this.kotlin "
		if(adapter.unchecked) this += "as ${kotlin.qualifiedName}$typeParams "
		this += "else ${kotlin.impl.name}(this)\n"
	}

	/**
	 * Shortcut for generating view conversions.
	 */
	private fun GeneratingFiles.GeneratingFile.generateView(conversion: Conversion, typeParams: String, view: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultViewDoc
		this += "\nfun $typeParams $view$typeParams.${conversion.name ?: "asView"}(): $view$typeParams = object: $view$typeParams by this {}\n"
	}

	/**
	 * Shortcut for generating remove-only conversions.
	 */
	private fun GeneratingFiles.GeneratingFile.generateRemoveOnly(conversion: Conversion, typeParams: String, removeOnly: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultRemoveOnlyDoc
		this += "\nfun $typeParams $removeOnly$typeParams.${conversion.name ?: "asRemoveOnly"}(): $removeOnly$typeParams = object: $removeOnly$typeParams by this {}\n"
	}
}
