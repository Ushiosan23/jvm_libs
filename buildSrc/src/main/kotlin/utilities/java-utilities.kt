package utilities

import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import types.JavadocInfo

fun configureJavadoc(task: Javadoc, config: JavadocInfo) =
	with(task) {
		isFailOnError = false
		// Configure javadoc information
		with(task.options as StandardJavadocDocletOptions) {
			config.title?.let(this::setDocTitle)
			config.windowTitle?.let(this::setWindowTitle)
			config.urls?.let(this::setLinks)
			
			outputLevel = config.outputLevel
		}
	}