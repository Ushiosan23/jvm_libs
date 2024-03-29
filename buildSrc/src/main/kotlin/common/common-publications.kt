package common

import org.gradle.api.Project
import types.*
import utilities.env

val jvmUtilitiesReleasePublication
	get() = PublicationInfo(
		name = "jvm-utilities",
		artifactId = "jvm-utilities",
		pom = MavenPomInfo(
			artifactUrl = "https://github.com/Ushiosan23/jvm_libs.git",
			description = "Utilities for the java virtual machine.",
			licenses = commonLicenses,
			artifactId = "jvm-utilities",
			developers = commonDevelopers.toList(),
			scm = ScmInfo(branch = "development")))

val jvmTestUtilitiesReleasePublication
	get() = PublicationInfo(
		name = "jvm-test-utilities",
		artifactId = "jvm-test-utilities",
		pom = MavenPomInfo(
			artifactUrl = "https://github.com/Ushiosan23/jvm_libs.git",
			description = "Utilities for the java virtual machine.",
			licenses = commonLicenses,
			artifactId = "jvm-test-utilities",
			developers = commonDevelopers.toList(),
			scm = ScmInfo(branch = "development")))

fun projectSigningInfo(project: Project) =
	SigningInfo(
		env(project, "SIGNING_KEY_ID", ""),
		env(project, "SIGNING_PASSWORD", ""),
		env(project, "SIGNING_PGP_B64", ""))