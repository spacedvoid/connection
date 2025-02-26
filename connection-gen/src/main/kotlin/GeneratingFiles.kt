/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets

/**
 * Contains output streams for source files that are being generated.
 */
class GeneratingFiles(private val generator: CodeGenerator): AutoCloseable {
	/**
	 * A single file that is being generated.
	 */
	inner class GeneratingFile(val packageName: String, val name: String, val extension: String = "kt") {
		init {
			this@GeneratingFiles.files += this
		}

		/**
		 * The output stream for the file.
		 * Whether it is closed can be determined with [closed].
		 */
		internal val out = BufferedWriter(OutputStreamWriter(
			this@GeneratingFiles.generator.createNewFile(
				Dependencies(false),
				packageName.replaceFirst(Regex("io\\.github\\.spacedvoid\\.connection\\.?"), ""),
				name,
				extension
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
				
				
			""".trimIndent())
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
		fun attach(files: List<KSFile>) = this@GeneratingFiles.generator.associate(files, this.packageName, this.name, this.extension)
	}

	/**
	 * Contains all files created with [GeneratingFile].
	 */
	private val files: MutableList<GeneratingFile> = mutableListOf()

	/**
	 * File that contains Kotlin collection to Connection adapters.
	 */
	val colAsCon: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "CollectionAsConnection").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}

	/**
	 * File that contains Connection to Kotlin collection adapters.
	 */
	val conAsCol: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "ConnectionAsCollection").also {
		it += "package io.github.spacedvoid.connection\n"
	}

	/**
	 * File that contains remove-only conversions.
	 */
	val removeOnly: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "RemoveOnly").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}

	/**
	 * File that contains view conversions.
	 */
	val view: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "View").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}

	/**
	 * Represents whether the output streams are closed.
	 */
	var closed: Boolean = false
		private set

	/**
	 * Closes all output streams.
	 * Closing after the streams are already closed will throw an [IllegalStateException].
	 */
	override fun close() {
		check(!this.closed) { "Closed" }
		this.closed = true
		this.files.forEach { it.out.close() }
	}
}

/**
 * Shortcut for generating Kotlin collection to Connection adapters.
 */
fun GeneratingFiles.generateColAsCon(name: String, docs: String, typeParams: String, kotlin: String, connection: String, impl: String) {
	check(!this.closed)
	this.colAsCon.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $kotlin$typeParams.$name(): $connection$typeParams = $impl(this)\n"
	}
}

/**
 * Shortcut for generating Connection to Kotlin collection adapters.
 *
 * [unchecked] controls whether the collection object needs unchecked casting.
 * Setting it to `true` additionally inserts `@Suppress("UNCHECKED_CAST")` and a cast to the [kotlin] type.
 */
fun GeneratingFiles.generateConAsCol(name: String, docs: String, typeParams: String, kotlin: String, connection: String, unchecked: Boolean) {
	check(!this.closed)
	this.conAsCol.let { out ->
		out += "\n"
		out += docs
		if(unchecked) out += "\n@Suppress(\"UNCHECKED_CAST\")"
		out += "\nfun $typeParams $connection$typeParams.$name(): $kotlin$typeParams = object: $kotlin$typeParams by this.kotlin "
		if(unchecked) out += "as $kotlin$typeParams "
		out += "{}\n"
	}
}

/**
 * Shortcut for generating view conversions.
 */
fun GeneratingFiles.generateView(name: String, docs: String, typeParams: String, from: String, to: String, impl: String) {
	check(!this.closed)
	this.view.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $from$typeParams.$name(): $to$typeParams = $impl(this.kotlin)\n"
	}
}

/**
 * Shortcut for generating remove-only conversions.
 */
fun GeneratingFiles.generateRemoveOnly(name: String, docs: String, typeParams: String, from: String, to: String, impl: String) {
	check(!this.closed)
	this.removeOnly.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $from$typeParams.$name(): $to$typeParams = $impl(this.kotlin)\n"
	}
}
