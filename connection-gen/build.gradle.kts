plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":connection-collections"))
	implementation(project(":connection-core"))
	implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-2.0.0")
	implementation(kotlin("reflect"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}
