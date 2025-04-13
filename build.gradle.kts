import org.jetbrains.dokka.gradle.engine.parameters.KotlinPlatform
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import java.net.URI

plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
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

    val dokkaJar by registering(Jar::class) {
        dependsOn(dokkaGenerate)
        archiveClassifier = "javadoc"
        from(dokkaOutputDir)
    }

    artifacts {
        archives(sourcesJar)
        archives(dokkaJar)
    }
}

tasks.compileKotlin {
    compilerOptions.freeCompilerArgs += "-Xjvm-default=all-compatibility"
}

tasks.clean {
    delete(dokkaOutputDir)
}

dokka {
    dokkaSourceSets.configureEach {
        documentedVisibilities = setOf(VisibilityModifier.Public, VisibilityModifier.Protected)
        reportUndocumented = true
        skipDeprecated = true
        suppressGeneratedFiles = false
        jdkVersion = 21
        analysisPlatform = KotlinPlatform.JVM
    }

    dokkaSourceSets.main {
        includes += file("src/main/docs/module.md")

        perPackageOption {
            matchingRegex = "io\\.github\\.spacedvoid\\.connection\\.impl"
            reportUndocumented = false
        }

        sourceLink {
            localDirectory = file("src/main/kotlin")
            remoteUrl = URI("https://github.com/spacedvoid/connection/tree/main/src/main/kotlin")
        }
    }

    dokkaPublications.configureEach {
        failOnWarning = true
    }

    dokkaPublications.html {
        outputDirectory = dokkaOutputDir
    }
}

private operator fun ConfigurableFileCollection.plusAssign(file: File) {
    setFrom(*this.from.toTypedArray(), file)
}

private operator fun <T: Any> ListProperty<T>.plusAssign(element: T) {
    add(element)
}
