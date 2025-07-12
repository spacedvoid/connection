import java.net.URI

plugins {
	kotlin("jvm")
	id("dokka-convention")
}

repositories {
	mavenCentral()
}

dependencies {
	api(project(":connection-collections"))
}

kotlin {
	jvmToolchain(21)
}

dokka {
	dokkaSourceSets.main {
		includes += file("src/main/markdown/module.md")

		perPackageOption {
			matchingRegex = "io\\.github\\.spacedvoid\\.connection\\.impl.*"
			reportUndocumented = false
		}

		sourceLink {
			localDirectory = file("src/main/kotlin")
			remoteUrl = URI("https://github.com/spacedvoid/connection/tree/manual-impl/connection-core/src/main/kotlin")
		}
	}
}
