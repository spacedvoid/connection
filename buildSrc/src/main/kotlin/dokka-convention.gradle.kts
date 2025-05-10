import org.jetbrains.dokka.gradle.engine.parameters.KotlinPlatform
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

plugins {
	id("org.jetbrains.dokka")
}

dokka {
	dokkaSourceSets.configureEach {
		documentedVisibilities.set(setOf(VisibilityModifier.Public, VisibilityModifier.Protected))
		reportUndocumented.set(true)
		skipDeprecated.set(true)
		suppressGeneratedFiles.set(true)
		jdkVersion.set(21)
		analysisPlatform.set(KotlinPlatform.JVM)
	}

	dokkaPublications.configureEach {
		failOnWarning.set(true)
	}

	dokkaPublications.html {
		outputDirectory.set(dokkaOutputDir)
	}
}
