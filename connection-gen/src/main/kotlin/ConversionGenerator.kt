package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

object ConversionGenerator {
	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		environment.codeGenerator.generateView(resolver)
		environment.codeGenerator.generateRemoveOnly(resolver)
	}

	private fun CodeGenerator.generateView(resolver: Resolver) {
		val viewCollections = ConnectionTypeKind.all.asSequence()
			.filter { typeKind -> typeKind.kind == ConnectionKind.VIEW && ConnectionTypeKind.all.any { it.type == typeKind.type && it.kind >= ConnectionKind.REMOVE_ONLY } }
			.toList()
		val dependencies = viewCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.toDependencies()
		createNewFile("View", dependencies).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			viewCollections.forEach {
				out += """
					
					/**
					 * Returns a collection view, converted from this collection.
					 */
					fun ${it.typeParams} ${it.fullIdentifier}.asView(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					
				""".trimIndent()
			}
		}
	}

	private fun CodeGenerator.generateRemoveOnly(resolver: Resolver) {
		val removeOnlyCollections = ConnectionTypeKind.all.asSequence()
			.filter { typeKind -> typeKind.kind == ConnectionKind.REMOVE_ONLY && ConnectionTypeKind.all.any { it.type == typeKind.type && it.kind >= ConnectionKind.MUTABLE } }
			.toList()
		val dependencies = removeOnlyCollections.asSequence()
			.flatMap { sequenceOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.toDependencies()
		createNewFile("RemoveOnly", dependencies).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			removeOnlyCollections.forEach {
				out += """
					
					/**
					 * Returns a remove-only collection, converted from this collection.
					 */
					fun ${it.typeParams} ${it.fullIdentifier}.asRemoveOnly(): ${it.fullIdentifier} = ${it.impl.name}(this.kotlin)
					 
				""".trimIndent()
			}
		}
	}
}
