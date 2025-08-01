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
            remoteUrl = URI("https://github.com/spacedvoid/connection/tree/main/connection-collections/src/main/kotlin")
        }
    }
}

private operator fun <T: Any> ListProperty<T>.plusAssign(element: T) {
    add(element)
}
