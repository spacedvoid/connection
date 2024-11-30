package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import java.io.BufferedWriter
import java.io.OutputStreamWriter
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

//TODO: Rewrite method to allow configuration of [Dependencies]
fun CodeGenerator.createNewFile(name: String): BufferedWriter =
	BufferedWriter(OutputStreamWriter(createNewFile(Dependencies.ALL_FILES, "io.github.spacedvoid.connection", name), StandardCharsets.UTF_8))
