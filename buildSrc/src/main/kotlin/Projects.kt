import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.ConfigurableFileCollection
import java.io.File

val Project.dokkaOutputDir
	get() = rootProject.file("docs")

fun Task.shouldNotBeCalled(instead: String? = null) {
	doFirst {
		project.logger.warn("The task $path should not be called, and was skipped.${instead?.let { " Run the task $it instead." } ?: ""}")
	}
}

operator fun ConfigurableFileCollection.plusAssign(file: File) {
	setFrom(*this.from.toTypedArray(), file)
}
