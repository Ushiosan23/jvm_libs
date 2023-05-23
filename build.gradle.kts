import utilities.env

plugins {
	id("io.github.gradle-nexus.publish-plugin")
}

allprojects {
	repositories {
		mavenCentral()
		mavenLocal()
		maven {
			setUrl("https://plugins.gradle.org/m2/")
		}
	}
}

nexusPublishing {
	repositories {
		sonatype {
			// Configure sonatype account
			stagingProfileId.set(env(project, "OSSRH_PROFILE_ID"))
			username.set(env(project, "OSSRH_USERNAME"))
			password.set(env(project, "OSSRH_PASSWORD"))
		}
	}
}