package io.github.spacedvoid.connection.gen

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

object AdapterGenerator {
	fun generate(environment: SymbolProcessorEnvironment, resolver: Resolver) {
		environment.codeGenerator.generateColAsCon(resolver)
		environment.codeGenerator.generateConAsCol(resolver)
	}

	private fun CodeGenerator.generateColAsCon(resolver: Resolver) {
		val dependencies = ConnectionTypeKind.all.asSequence()
			.flatMap { listOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.toDependencies()
		createNewFile("CollectionAsConnection", dependencies).use { out ->
			out += """
				package io.github.spacedvoid.connection
				
				import io.github.spacedvoid.connection.impl.*
				
			""".trimIndent()
			ConnectionTypeKind.all.forEach { typeKind ->
				typeKind.asConnectionAdapters.forEach {
					out += "\n"
					out += it.docs
					val adapterName = it.customName ?: when(typeKind.kind) {
						ConnectionKind.IMMUTABLE -> "asConnection"
						ConnectionKind.VIEW -> "asViewConnection"
						ConnectionKind.REMOVE_ONLY -> "asRemoveOnlyConnection"
						ConnectionKind.MUTABLE -> "asMutableConnection"
					}
					out += "\nfun ${typeKind.typeParams} ${typeKind.kotlin}${typeKind.typeParams}.$adapterName(): ${typeKind.fullIdentifier} = ${typeKind.impl.name}(this)\n"
				}
			}
		}
	}

	private fun CodeGenerator.generateConAsCol(resolver: Resolver) {
		val dependencies = ConnectionTypeKind.all.asSequence()
			.flatMap { listOf(it, it.impl) }
			.map { resolver.getClassDeclarationByName(it.qualifiedName)!! }
			.toDependencies()
		createNewFile("ConnectionAsCollection", dependencies).use { out ->
			out.write("package io.github.spacedvoid.connection\n")
			ConnectionTypeKind.all.forEach { typeKind ->
				typeKind.asKotlinAdapters.forEach {
					out += "\n"
					out += it.docs
					val target = it.uncheckedCast?.target ?: typeKind.kotlin
					if(it.uncheckedCast != null) out += "\n@Suppress(\"UNCHECKED_CAST\")"
					val adapterName = it.customName ?: "asKotlin"
					out += "\nfun ${typeKind.typeParams} ${typeKind.fullIdentifier}.$adapterName(): $target${typeKind.typeParams} = object: $target${typeKind.typeParams} by this.kotlin "
					if(it.uncheckedCast != null) out += "as ${it.uncheckedCast.target}${typeKind.typeParams} "
					out += "{}\n"
				}
			}
		}
	}
}
