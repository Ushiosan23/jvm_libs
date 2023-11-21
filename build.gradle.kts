import utilities.env

plugins {
	id("io.github.gradle-nexus.publish-plugin")
	id("org.javamodularity.moduleplugin") version "1.8.12" apply false
}

allprojects {
//	if (this != rootProject) apply(plugin = "org.javamodularity.moduleplugin")
	
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