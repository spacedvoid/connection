import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import java.io.File

val Project.dokkaOutputDir: File
	get() = rootProject.file("docs")

operator fun ConfigurableFileCollection.plusAssign(file: File) {
	from(file)
}

fun ConfigurableFileCollection.assign(file: File) {
	setFrom(file)
}
