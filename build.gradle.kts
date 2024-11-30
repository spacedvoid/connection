import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.Platform
import java.net.URI

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
}

group = "io.github.spacedvoid"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":connection-gen"))
    ksp(project(":connection-gen"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.dokkaHtml {
    outputDirectory = projectDir.resolve("docs")
    failOnWarning = true

    dokkaSourceSets {
        configureEach {
            documentedVisibilities = setOf(DokkaConfiguration.Visibility.PUBLIC, DokkaConfiguration.Visibility.PROTECTED)
            reportUndocumented = false
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
            includes += file("src/main/docs/module.md")
        }
    }
}

private operator fun ConfigurableFileCollection.plusAssign(file: File) {
    setFrom(*this.from.toTypedArray(), file)
}
