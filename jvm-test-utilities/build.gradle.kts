import common.*
import utilities.*

plugins {
	`java-library`
	`maven-publish`
	signing
}

/* -----------------------------------------------------
 * Base configuration
 * ----------------------------------------------------- */

group = commonGroup
version = jvmTestUtilitiesVersion

/* -----------------------------------------------------
 * Java configuration
 * ----------------------------------------------------- */

java {
	sourceCompatibility = commonSourceCompatibility
	targetCompatibility = commonTargetCompatibility
}

/* -----------------------------------------------------
 * Maven publications
 * ----------------------------------------------------- */

afterEvaluate {
	// Publications
	publishing {
		// Release publication
		generatePublication(this, jvmTestUtilitiesReleasePublication, project)
	}
	
	// Signing
	signing {
		signPublications(this, projectSigningInfo(project),
			*publishing.publications.toTypedArray())
	}
}

/* -----------------------------------------------------
 * Project tasks
 * ----------------------------------------------------- */

tasks {
	generatePublicationTasks(this,
		jvmTestUtilitiesReleasePublication,
		project, sourceSets.findByName("main"))
	
	findByName("javadoc")?.let {
		configureJavadoc(it as Javadoc, commonJvmUtilitiesJavadocInfo)
	}
	
	test {
		useJUnitPlatform()
	}
}

/* -----------------------------------------------------
 * Dependencies
 * ----------------------------------------------------- */

dependencies {
	compileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
	implementation(platform("org.junit:junit-bom:$jupiterUnitVersion"))
	implementation("org.junit.jupiter:junit-jupiter")
	implementation(project(":jvm-utilities"))
	// Test implementations
	testImplementation(platform("org.junit:junit-bom:$jupiterUnitVersion"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testCompileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
}