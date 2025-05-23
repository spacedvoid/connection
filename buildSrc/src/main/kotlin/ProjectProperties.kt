import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import java.io.File

val Project.dokkaOutputDir
	get() = rootProject.file("docs")

operator fun ConfigurableFileCollection.plusAssign(file: File) {
	setFrom(*this.from.toTypedArray(), file)
}
