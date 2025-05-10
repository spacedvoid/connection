plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "connection"

include("connection-gen")
include("connection-api")
include("connection-core")
include("connection-collections")
