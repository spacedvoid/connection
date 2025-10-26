/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(DslInternal::class)

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import io.github.spacedvoid.connection.gen.dsl.ConnectionGeneration
import io.github.spacedvoid.connection.gen.dsl.ConnectionKind
import io.github.spacedvoid.connection.gen.dsl.Conversion
import io.github.spacedvoid.connection.gen.dsl.DslInternal
import io.github.spacedvoid.connection.gen.dsl.qualifiedName
import io.github.spacedvoid.connection.gen.dsl.typeArgs

object ConversionGenerator {
	fun generate(resolver: Resolver, generator: CodeGenerator, collected: ConnectionGeneration) {
		ConversionFiles(generator).use { files ->
			collected.connections.values.forEach { generatePerType(files, resolver, it) }
		}
	}

	private fun generatePerType(files: ConversionFiles, resolver: Resolver, type: ConnectionGeneration.ConnectionType) {
		type.conversions.view?.let {
			val view = type.kinds[ConnectionKind.VIEW] ?: return@let
			files.view.attachSources(resolver, view.qualifiedName)
			files.view.generateView(it, type.typeArgs, view.name)
		}
		type.conversions.removeOnly?.let {
			val removeOnly = type.kinds[ConnectionKind.REMOVE_ONLY] ?: return@let
			files.removeOnly.attachSources(resolver, removeOnly.qualifiedName)
			files.removeOnly.generateRemoveOnly(it, type.typeArgs, removeOnly.name)
		}
	}

	private class ConversionFiles(generator: CodeGenerator): AutoCloseable {
		val view: GeneratingFile = GeneratingFile(generator, "io.github.spacedvoid.connection", "View")

		val removeOnly: GeneratingFile = GeneratingFile(generator, "io.github.spacedvoid.connection", "RemoveOnly")

		override fun close() {
			this.view.close()
			this.removeOnly.close()
		}
	}

	private val defaultViewDoc = """
		/**
		 * Returns a view collection that delegates to this collection, prohibiting all mutation operations.
		 */
	""".trimIndent()

	private val defaultRemoveOnlyDoc = """
		/**
		 * Returns a remove-only collection that delegates to this collection, prohibiting element addition operations.
		 */
	""".trimIndent()

	/**
	 * Shortcut for generating view conversions.
	 */
	private fun GeneratingFile.generateView(conversion: Conversion, typeParams: String, view: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultViewDoc
		this += "\nfun $typeParams $view$typeParams.${conversion.name ?: "asView"}(): $view$typeParams = object: $view$typeParams by this {}\n"
	}

	/**
	 * Shortcut for generating remove-only conversions.
	 */
	private fun GeneratingFile.generateRemoveOnly(conversion: Conversion, typeParams: String, removeOnly: String) {
		check(!this.closed)
		this += "\n"
		this += conversion.docs ?: defaultRemoveOnlyDoc
		this += "\nfun $typeParams $removeOnly$typeParams.${conversion.name ?: "asRemoveOnly"}(): $removeOnly$typeParams = object: $removeOnly$typeParams by this {}\n"
	}
}
