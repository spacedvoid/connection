package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class GeneratingFiles(private val generator: CodeGenerator) {
	inner class GeneratingFile(val packageName: String, val name: String, val extension: String = "kt") {
		init {
			this@GeneratingFiles.files += this
		}

		internal val out = BufferedWriter(OutputStreamWriter(
			this@GeneratingFiles.generator.createNewFile(Dependencies(false), packageName, name, extension),
			StandardCharsets.UTF_8
		)).also { it.write("// Auto-generated file. The declaration order might change without notice.\n\n") }

		operator fun plusAssign(content: String) {
			this.out.write(content)
		}

		fun attach(file: KSFile) = attach(listOf(file))

		fun attach(files: List<KSFile>) {
			this@GeneratingFiles.generator.associate(files, this.packageName, this.name, this.extension)
		}
	}

	private val files: MutableList<GeneratingFile> = mutableListOf()

	val colAsCon: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "CollectionAsConnection").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}
	val conAsCol: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "ConnectionAsCollection").also {
		it += "package io.github.spacedvoid.connection\n"
	}
	val removeOnly: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "RemoveOnly").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}
	val view: GeneratingFile = GeneratingFile("io.github.spacedvoid.connection", "View").also {
		it += """
			package io.github.spacedvoid.connection
			
			import io.github.spacedvoid.connection.impl.*
			
		""".trimIndent()
	}

	var closed: Boolean = false
		private set

	fun close() {
		check(!this.closed) { "Closed" }
		this.closed = true
		this.files.forEach { it.out.close() }
	}
}

fun GeneratingFiles.generateColAsCon(name: String, docs: String, typeParams: String, kotlin: String, connection: String, impl: String) {
	check(!this.closed)
	this.colAsCon.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $kotlin$typeParams.$name(): $connection$typeParams = $impl(this)\n"
	}
}

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

fun GeneratingFiles.generateView(name: String, docs: String, typeParams: String, from: String, to: String, impl: String) {
	check(!this.closed)
	this.view.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $from$typeParams.$name(): $to$typeParams = $impl(this.kotlin)\n"
	}
}

fun GeneratingFiles.generateRemoveOnly(name: String, docs: String, typeParams: String, from: String, to: String, impl: String) {
	check(!this.closed)
	this.removeOnly.let { out ->
		out += "\n"
		out += docs
		out += "\nfun $typeParams $from$typeParams.$name(): $to$typeParams = $impl(this.kotlin)\n"
	}
}
