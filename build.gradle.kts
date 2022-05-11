plugins {
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

/* ------------------------------------------------------------------
 * Global configuration
 * ------------------------------------------------------------------ */

buildscript {
	// Global configuration
	extra.set("global.jvm.compatibility", JavaVersion.VERSION_11)
	extra.set("global.maven.groupId", "com.github.ushiosan23")
	extra.set("global.maven.url", "https://github.com/Ushiosan23/jvm_libs")
	extra.set("global.scm.connection", "scm:git:github.com/Ushiosan23/jvm_libs")
	extra.set("global.scm.sshConnection", "scm:git:ssh:github.com/Ushiosan23/jvm_libs")
	extra.set(
		"global.maven.developers",
		listOf(
			mapOf(
				"id" to "Ushiosan23",
				"name" to "Brian Alvarez",
				"email" to "haloleyendee@outlook.com"
			)
		)
	)

	extra.set(
		"global.javadoc.links", listOf(
			"https://docs.oracle.com/en/java/javase/11/docs/api/",
			"https://javadoc.io/doc/org.jetbrains/annotations/latest/"
		)
	)

	// Utilities configuration
	extra.set("utilities.name", "JVM Utilities")
	extra.set("utilities.version", "0.1.20")
	extra.set("utilities.maven.artifact", "jvm_utilities")
	extra.set("utilities.maven.description", "Utilities for the java virtual machine.")
	extra.set("utilities.maven.url", "${extra["global.maven.url"]}/tree/main/jvm_utilities")
	extra.set("utilities.maven.license", "MIT")
	extra.set("utilities.maven.licenseUrl", "${extra["global.maven.url"]}/blob/main/LICENSE.md")
	extra.set("utilities.maven.developers", extra["global.maven.developers"])

	extra.set("utilities.scm.connection", extra["global.scm.connection"])
	extra.set("utilities.scm.sshConnection", extra["global.scm.sshConnection"])
	extra.set("utilities.scm.url", "${extra["global.maven.url"]}")

	extra.set("utilities.javadoc.links", extra["global.javadoc.links"])

	// SwingUtils configuration
	extra.set("swing.name", "JVM Utilities")
	extra.set("swing.version", "0.1")
	extra.set("swing.maven.artifact", "swing_utilities")
	extra.set("swing.maven.description", "")
	extra.set("swing.maven.url", "${extra["global.maven.url"]}/tree/main/swing_utilities")
	extra.set("swing.maven.license", "MIT")
	extra.set("swing.maven.licenseUrl", "${extra["global.maven.url"]}/blob/main/LICENSE.md")
	extra.set("swing.maven.developers", extra["global.maven.developers"])

	extra.set("swing.scm.connection", extra["global.scm.connection"])
	extra.set("swing.scm.sshConnection", extra["global.scm.sshConnection"])
	extra.set("swing.scm.url", "${extra["global.maven.url"]}")

	extra.set("swing.javadoc.links", extra["global.javadoc.links"])


	// Global repositories
	repositories {
		mavenCentral()
	}
}

/* ------------------------------------------------------------------
 * All projects configuration
 * ------------------------------------------------------------------ */

allprojects {
	repositories {
		mavenCentral()
	}
}

/* ------------------------------------------------------------------
 * Nexus configuration
 * ------------------------------------------------------------------ */

nexusPublishing {
	repositories {
		sonatype {
			apply("extra.gradle.kts")
			val rpExtra = rootProject.extra
			// Configure account
			stagingProfileId.set(rpExtra["OSSRH_PROFILE_ID"] as String)
			username.set(rpExtra["OSSRH_USERNAME"] as String)
			password.set(rpExtra["OSSRH_PASSWORD"] as String)
		}
	}
}
