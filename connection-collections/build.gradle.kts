import java.net.URI

plugins {
    kotlin("jvm")
    id("dokka-convention")
}

repositories {
    mavenCentral()
}

tasks.compileKotlin {
    compilerOptions.freeCompilerArgs += "-Xjvm-default=all-compatibility"
}

kotlin {
    jvmToolchain(21)
}

dokka {
    dokkaSourceSets.main {
        includes += file("src/main/markdown/module.md")

        sourceLink {
            localDirectory = file("src/main/kotlin")
            remoteUrl = URI("https://github.com/spacedvoid/connection/tree/manual-impl/connection-collections/src/main/kotlin")
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

private operator fun <T: Any> ListProperty<T>.plusAssign(element: T) {
    add(element)
}
