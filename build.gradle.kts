import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.Platform
import java.net.URI

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "io.github.spacedvoid"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.dokkaHtml {
    outputDirectory = projectDir.resolve("docs").apply(File::mkdirs)
    failOnWarning = true
    dokkaSourceSets {
        configureEach {
            documentedVisibilities = setOf(DokkaConfiguration.Visibility.PUBLIC, DokkaConfiguration.Visibility.PROTECTED)
            reportUndocumented = true
            skipDeprecated = true
            suppressGeneratedFiles = false
            jdkVersion = 21
            platform = Platform.jvm

            sourceLink {
                localDirectory = projectDir.resolve("src/main/kotlin")
                remoteUrl = URI("https://github.com/spacedvoid/connection/tree/main/src/main/kotlin").toURL()
            }
        }

        named("main") {
            includes += files("src/main/docs/module.md")
        }
    }
}

tasks.register("cleanDokka") {
    delete(projectDir.resolve("docs"))
}

private operator fun ConfigurableFileCollection.plusAssign(files: ConfigurableFileCollection) {
    setFrom(*from.toTypedArray(), *files.from.toTypedArray())
}
