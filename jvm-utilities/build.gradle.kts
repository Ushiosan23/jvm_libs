import common.*
import utilities.*
import java.nio.file.*
import java.nio.file.Path

plugins {
	`java-library`
	`maven-publish`
	signing
}

/* -----------------------------------------------------
 * Base configuration
 * ----------------------------------------------------- */

group = commonGroup
version = jvmUtilitiesVersion

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
		signPublications(this, projectSigningInfo(project),
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

val versionResource by tasks.registering {
	val resourceDir = sourceSets["main"].resources
		.srcDirs
		.firstOrNull()?.toPath() ?: return@registering
	val targetResource = Path.of("$resourceDir", "ushiosan/jvm/jvm-utilities.properties")
	
	// Generate file
	outputs.file(targetResource)
	if (!Files.exists(targetResource)) {
		Files.createDirectories(targetResource.parent)
		Files.createFile(targetResource)
	}
	
	// Generate file content
	doLast {
		val content = """
			jvm.utilities.version=$version
		""".trimIndent()
		Files.writeString(targetResource, content,
			StandardOpenOption.WRITE,
			StandardOpenOption.TRUNCATE_EXISTING)
	}
}

tasks.processResources {
	dependsOn(versionResource)
}

/* -----------------------------------------------------
 * Dependencies
 * ----------------------------------------------------- */

dependencies {
	compileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
	// Test implementations
	testImplementation(platform("org.junit:junit-bom:$jupiterUnitVersion"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation(project(":jvm-test-utilities"))
	testCompileOnly("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
}