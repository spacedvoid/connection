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

val dokkaOutputDir = file("docs")

tasks {
    val sourcesJar by registering(Jar::class) {
        archiveClassifier = "sources"
        from(sourceSets["main"].allSource)
    }

    val dokkaHtmlJar by registering(Jar::class) {
        dependsOn(dokkaHtml)
        archiveClassifier = "javadoc"
        from(dokkaOutputDir)
    }

    artifacts {
        archives(sourcesJar)
        archives(dokkaHtmlJar)
    }
}

tasks.compileKotlin {
    compilerOptions.freeCompilerArgs += "-Xjvm-default=all-compatibility"
}

tasks.dokkaHtml {
    outputDirectory = dokkaOutputDir
    failOnWarning = true

    dokkaSourceSets {
        configureEach {
            documentedVisibilities = setOf(DokkaConfiguration.Visibility.PUBLIC, DokkaConfiguration.Visibility.PROTECTED)
            reportUndocumented = true
            skipDeprecated = true
            suppressGeneratedFiles = false
            jdkVersion = 21
            platform = Platform.jvm

            perPackageOption {
                matchingRegex = "io\\.github\\.spacedvoid\\.connection\\.impl"
                reportUndocumented = false
            }

            sourceLink {
                localDirectory = file("src/main/kotlin")
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

private operator fun <T: Any> ListProperty<T>.plusAssign(element: T) {
    add(element)
}
