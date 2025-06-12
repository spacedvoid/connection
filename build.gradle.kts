plugins {
	kotlin("jvm")
	id("com.google.devtools.ksp") version "2.1.20-2.0.0"
	id("dokka-convention")
}

group = "io.github.spacedvoid"
version = "0.1.0"

repositories {
	mavenCentral()
}

dependencies {
	ksp(project(":connection-gen"))
	dokka(project(":connection-collections"))
	dokka(project(":connection-api"))
	dokka(project(":connection-core"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}

tasks {
	val apiJar by registering(Jar::class) {
		group = "Build"
		description = "Assembles only the API binary jar."

		archiveAppendix = "api"
		from(project(":connection-api").sourceSets["main"].output)
		from(project(":connection-collections").sourceSets["main"].output)
	}

	val coreJar by registering(Jar::class) {
		group = "Build"
		description = "Assembles the core binary jar."

		archiveAppendix = "core"
		from(project(":connection-api").sourceSets["main"].output)
		from(project(":connection-collections").sourceSets["main"].output)
		from(project(":connection-core").sourceSets["main"].output)
	}

	val apiSourcesJar by registering(Jar::class) {
		group = "Build"
		description = "Assembles only the API source jar."

		archiveAppendix = "api"
		archiveClassifier = "sources"
		from(project(":connection-api").sourceSets["main"].allSource)
		from(project(":connection-collections").sourceSets["main"].allSource)
	}

	val coreSourcesJar by registering(Jar::class) {
		group = "Build"
		description = "Assembles only the core source jar."

		archiveAppendix = "core"
		archiveClassifier = "sources"
		from(project(":connection-api").sourceSets["main"].allSource)
		from(project(":connection-collections").sourceSets["main"].allSource)
		from(project(":connection-core").sourceSets["main"].allSource)
	}

	val dokkaJar by registering(Jar::class) {
		group = "Build"
		description = "Assembles only the Dokka documentation jar."

		dependsOn(dokkaGenerate)
		mustRunAfter(dokkaGenerate)
		archiveClassifier = "javadoc"
		from(dokkaOutputDir)
	}

	artifacts {
		archives(apiJar)
		archives(coreJar)
		archives(apiSourcesJar)
		archives(coreSourcesJar)
		archives(dokkaJar)
	}
}

dokka {
	dokkaPublications.html {
		outputDirectory = dokkaOutputDir
	}
}

tasks.clean {
	delete(dokkaOutputDir)
}

tasks.jar {
	enabled = false
}

tasks.javadoc {
	enabled = false
}
