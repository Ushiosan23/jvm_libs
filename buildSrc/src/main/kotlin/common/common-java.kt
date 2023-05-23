package common

import org.gradle.api.JavaVersion
import types.JavadocInfo

/**
 * Common source java version
 */
val commonSourceCompatibility: JavaVersion
	get() = JavaVersion.VERSION_11

/**
 * Common target java version
 */
val commonTargetCompatibility: JavaVersion
	get() = commonSourceCompatibility

/**
 * Common Javadoc info for jvm-utilities
 */
val commonJvmUtilitiesJavadocInfo: JavadocInfo
	get() = JavadocInfo(
		title = jvmUtilitiesReleasePublication.name,
		windowTitle = jvmUtilitiesReleasePublication.name,
		urls = listOf(
			"https://docs.oracle.com/en/java/javase/11/docs/api/",
			"https://javadoc.io/doc/org.jetbrains/annotations/latest/"))