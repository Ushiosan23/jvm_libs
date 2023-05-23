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
version = "1.0.0"

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
		generatePublication(this, jvmUtilitiesReleasePublication, project)
	}
	
	// Signing
	signing {
		signPublications(this, jvmUtilitiesSigningInfo(project),
			*publishing.publications.toTypedArray())
	}
}

/* -----------------------------------------------------
 * Project tasks
 * ----------------------------------------------------- */

tasks {
	generatePublicationTasks(this,
		jvmUtilitiesReleasePublication,
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
	// Test implementations
	testImplementation(platform("org.junit:junit-bom:$jupiterUnitVersion"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testCompileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
}