/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets

/**
 * Wraps output streams for source files that are being generated.
 *
 * @see CodeGenerator.createNewFile
 */
class GeneratingFile(val generator: CodeGenerator, val packageName: String, val name: String, val extension: String = "kt"): AutoCloseable {
	companion object {
		private val emptyDependencies = Dependencies(false)
	}

	var closed = false
		private set

	/**
	 * The output stream for the file.
	 * Whether it is closed can be determined with [closed].
	 */
	private val out = BufferedWriter(OutputStreamWriter(
		this.generator.createNewFile(
			emptyDependencies,
			this.packageName.replace('/', '.').replaceFirst(Regex("io\\.github\\.spacedvoid\\.connection\\.?"), ""),
			this.name,
			this.extension
		),
		StandardCharsets.UTF_8
	)).also {
		it.write("""
			/*
			 * This Source Code Form is subject to the terms of the Mozilla Public
			 * License, v. 2.0. If a copy of the MPL was not distributed with this
			 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
			 */
			
			// Auto-generated file. The declaration order might change without notice.
			
			package $packageName
			
		""".trimIndent())
	}

	/**
	 * Closes the output stream.
	 *
	 * Has no effect when the stream is already closed.
	 */
	override fun close() {
		if(this.closed) return
		this.out.close()
		this.closed = true
	}

	/**
	 * Alternative for [Writer.write].
	 */
	operator fun plusAssign(content: String) {
		this.out.write(content)
	}

	/**
	 * Associates a source file to this output file.
	 *
	 * [CodeGenerator.associate] allows to add dependencies after the file is [created][CodeGenerator.createNewFile].
	 * This method utilizes this behavior.
	 */
	fun attach(file: KSFile) = attach(listOf(file))

	/**
	 * Associates source files to this output file.
	 *
	 * [CodeGenerator.associate] allows to add dependencies after the file is [created][CodeGenerator.createNewFile].
	 * This method utilizes this behavior.
	 */
	fun attach(files: List<KSFile>) = this.generator.associate(files, this.packageName, this.name, this.extension)
}

/**
 * [Attaches][GeneratingFile.attach] the classes with the given [qualified names][qualifiedNames] to the [file][GeneratingFile].
 */
fun GeneratingFile.attachSources(resolver: Resolver, vararg qualifiedNames: String) =
	qualifiedNames.asSequence()
		.map { resolver.getClassDeclarationByName(it) ?: throw IllegalArgumentException("Class $it does not exist") }
		.mapNotNull { it.containingFile }
		.toList()
		.let { attach(it) }
