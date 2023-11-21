package common

import org.gradle.api.JavaVersion
import types.JavadocInfo
import types.PublicationInfo

/**
 * Common java version code
 */
val commonJavaVersion
	get() = "11"

/**
 * Common source java version
 */
val commonSourceCompatibility
	get() = JavaVersion.toVersion(commonJavaVersion)

/**
 * Common target java version
 */
val commonTargetCompatibility
	get() = commonSourceCompatibility

/* -----------------------------------------------------
 * Functions
 * ----------------------------------------------------- */

fun generateProjectJavadocInfo(info: PublicationInfo, vararg urls: String): JavadocInfo =
	JavadocInfo(
		title = info.name,
		windowTitle = info.name,
		urls = mutableListOf(
			"https://docs.oracle.com/en/java/javase/$commonJavaVersion/docs/api/",
			"https://javadoc.io/doc/org.jetbrains/annotations/$jetbrainsAnnotationsVersion/"
		) + urls
	)