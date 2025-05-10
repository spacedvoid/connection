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
		archiveAppendix = "api"
		from(project(":connection-api").sourceSets["main"].output)
		from(project(":connection-collections").sourceSets["main"].output)
	}

	val coreJar by registering(Jar::class) {
		archiveAppendix = "core"
		from(project(":connection-api").sourceSets["main"].output)
		from(project(":connection-collections").sourceSets["main"].output)
		from(project(":connection-core").sourceSets["main"].output)
	}

	val assembleBin = register("assembleBin") {
		group = "Build"
		description = "Assembles only the binary outputs of this project."
		dependsOn(apiJar, coreJar)
	}

	val apiSourcesJar by registering(Jar::class) {
		archiveAppendix = "api"
		archiveClassifier = "sources"
		from(project(":connection-api").sourceSets["main"].allSource)
		from(project(":connection-collections").sourceSets["main"].allSource)
	}

	val coreSourcesJar by registering(Jar::class) {
		archiveAppendix = "core"
		archiveClassifier = "sources"
		from(project(":connection-api").sourceSets["main"].allSource)
		from(project(":connection-collections").sourceSets["main"].allSource)
		from(project(":connection-core").sourceSets["main"].allSource)
	}

	val assembleSource = register("assembleSource") {
		group = "Build"
		description = "Assembles only the source outputs of this project."
		dependsOn(apiSourcesJar, coreSourcesJar)
	}

	val dokkaJar by registering(Jar::class) {
		dependsOn(dokkaGenerate)
		archiveClassifier = "javadoc"
		from(dokkaOutputDir)
	}

	val assembleDoc = register("assembleDoc") {
		group = "Build"
		description = "Assembles only the documentation outputs of this project."
		dependsOn(dokkaJar)
	}

	assemble {
		dependsOn(assembleBin, assembleSource, assembleDoc)
	}
}

tasks.clean {
	delete(dokkaOutputDir)
}
