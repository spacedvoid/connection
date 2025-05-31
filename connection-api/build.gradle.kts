import java.net.URI

plugins {
	kotlin("jvm")
	id("com.google.devtools.ksp") version "2.1.20-2.0.0"
	id("dokka-convention")
}

repositories {
	mavenCentral()
}

dependencies {
	api(project(":connection-collections"))
	implementation(project(":connection-core"))
	compileOnly(project(":connection-gen"))
	ksp(project(":connection-gen"))
}

kotlin {
	jvmToolchain(21)
}

dokka {
	dokkaSourceSets.main {
		includes += file("src/main/markdown/module.md")

		sourceLink {
			localDirectory = file("src/main/kotlin")
			remoteUrl = URI("https://github.com/spacedvoid/connection/tree/manual-impl/connection-api/src/main/kotlin")
		}
	}
}

tasks.dokkaGenerate {
	enabled = false
}

tasks.dokkaGeneratePublicationHtml {
	enabled = false
}

tasks.logLinkDokkaGeneratePublicationHtml {
	enabled = false
}
