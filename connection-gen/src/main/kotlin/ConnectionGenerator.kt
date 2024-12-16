package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets

class ConnectionGeneratorProvider: SymbolProcessorProvider {
	override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = ConnectionGenerator(environment)
}

class ConnectionGenerator(private val environment: SymbolProcessorEnvironment): SymbolProcessor {
	override fun process(resolver: Resolver): List<KSAnnotated> {
		if(resolver.getAllFiles().find { it.fileName == "CollectionAsConnection.kt" } != null) return listOf()
		AdapterGenerator.generate(this.environment, resolver)
		ConversionGenerator.generate(this.environment, resolver)
		return listOf()
	}
}

internal fun CodeGenerator.createNewFile(name: String, dependencies: Dependencies = Dependencies(false)): BufferedWriter =
	BufferedWriter(OutputStreamWriter(createNewFile(dependencies, "io.github.spacedvoid.connection", name), StandardCharsets.UTF_8)).also {
		it += "// Auto-generated file. The declaration order might change without notice.\n\n"
	}

internal fun Sequence<KSClassDeclaration>.toDependencies(aggregating: Boolean = false): Dependencies =
	Dependencies(
		aggregating,
		*this.map { it.containingFile }
			.filterNotNull()
			.toSet()
			.toTypedArray()
	)

internal operator fun Writer.plusAssign(s: String) = write(s)
