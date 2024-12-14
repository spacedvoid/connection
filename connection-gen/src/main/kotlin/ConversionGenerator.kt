package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

object ConversionGenerator {
	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		environment.codeGenerator.generateView(resolver)
		environment.codeGenerator.generateRemoveOnly(resolver)
	}

	private fun CodeGenerator.generateView(resolver: Resolver) {
		val viewCollections = (CollectionTypes.entries + MapTypes.entries).asSequence()
			.map { ConnectionTypeKind(it, ConnectionKind.VIEW) }
			.filter { it !in ConnectionTypeKind.nonExistent }
			.toList()
		val dependencies = viewCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("View", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			viewCollections.forEach {
				out += """
					
					/**
					 * Returns a collection view, converted from this collection.
					 */
					 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asView(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					
				""".trimIndent()
			}
		}
	}

	private fun CodeGenerator.generateRemoveOnly(resolver: Resolver) {
		val removeOnlyCollections = CollectionTypes.entries.asSequence()
			.map { ConnectionTypeKind(it, ConnectionKind.REMOVE_ONLY) }
			.filter { it !in ConnectionTypeKind.nonExistent }
			.toList()
		val dependencies = removeOnlyCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.files()
		createNewFile("RemoveOnly", Dependencies(aggregating = false, *dependencies)).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			removeOnlyCollections.forEach {
				out += """
					
					/**
					 * Returns a remove-only collection, converted from this collection.
					 */
					 fun ${it.typeParamDeclaration()} ${it.fullIdentifier}.asRemoveOnly(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					 
				""".trimIndent()
			}
		}
	}
}
