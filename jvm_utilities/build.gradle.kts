plugins {
	signing
	`java-library`
	`maven-publish`
}

/* ------------------------------------------------------------------
 * Extra config
 * ------------------------------------------------------------------ */

apply("../extra.gradle.kts")
val rpExtra
	get() = rootProject.extra
val pKey
	get() = "utilities"


/* ------------------------------------------------------------------
 * Base configuration
 * ------------------------------------------------------------------ */

group = rpExtra["global.maven.groupId"] as String
version = rpExtra["$pKey.version"] as String

/* ------------------------------------------------------------------
 * Java configuration
 * ------------------------------------------------------------------ */

java {
	sourceCompatibility = rpExtra["global.jvm.compatibility"] as JavaVersion
	targetCompatibility = rpExtra["global.jvm.compatibility"] as JavaVersion
}

/* ------------------------------------------------------------------
 * Javadoc configuration
 * ------------------------------------------------------------------ */

@Suppress("UNCHECKED_CAST")
tasks.named<Javadoc>("javadoc") {
	with(options as StandardJavadocDocletOptions) {
		docTitle = rpExtra["$pKey.name"] as String
		windowTitle = "${rpExtra["$pKey.name"]} - ${rpExtra["$pKey.version"]}"
		links = rpExtra.get("$pKey.javadoc.links") as List<String>?
	}
}

/* ------------------------------------------------------------------
 * Extra tasks
 * ------------------------------------------------------------------ */

tasks.create("sourcesJar", Jar::class) {
	archiveClassifier.set("sources")
	from(sourceSets["main"].java.srcDirs)
}

tasks.create("docJar", Jar::class) {
	archiveClassifier.set("javadoc")
	from(project.tasks["javadoc"])
	// Attach docs resources
	rootProject.file("docs/${rpExtra["$pKey.maven.artifact"]}").also {
		if (!it.exists()) return@also
		from(it)
	}
	dependsOn(project.tasks["javadoc"])
}

/* ------------------------------------------------------------------
 * Maven central configuration
 * ------------------------------------------------------------------ */

afterEvaluate {
	publishing {
		publications {
			// Generate default publication
			create<MavenPublication>("release") {
				// Base config
				groupId = rpExtra["global.maven.groupId"] as String
				artifactId = rpExtra["$pKey.maven.artifact"] as String
				version = rpExtra["$pKey.version"] as String
				// Artifact contents
				from(components["java"])
				artifact(project.tasks["sourcesJar"])
				artifact(project.tasks["docJar"])
				// POM configuration
				pom {
					// Base pom information
					url.set(rpExtra["$pKey.maven.url"] as String)
					name.set(rpExtra["$pKey.maven.artifact"] as String)
					description.set(rpExtra["$pKey.maven.description"] as String)
					// License
					licenses {
						license {
							url.set(rpExtra["$pKey.maven.licenseUrl"] as String)
							name.set(rpExtra["$pKey.maven.license"] as String)
						}
					}
					// Developers
					@Suppress("UNCHECKED_CAST")
					val devs = rpExtra["$pKey.maven.developers"] as List<Map<String, String>>
					developers {
						devs.forEach {
							developer {
								it["organizationUrl"]?.let(organizationUrl::set)
								it["organization"]?.let(organization::set)
								it["timezone"]?.let(timezone::set)
								it["email"]?.let(email::set)
								it["name"]?.let(name::set)
								it["url"]?.let(url::set)
								it["id"]?.let(id::set)
								it["roles"]?.let { roles.set(it.split(",")) }
							}
						}
					}
					// SCM Connections
					scm {
						developerConnection.set(rpExtra["$pKey.scm.sshConnection"] as String)
						connection.set(rpExtra["$pKey.scm.connection"] as String)
						url.set(rpExtra["$pKey.scm.url"] as String)
					}
				}
			}
			// End default publication
		}
	}
}

/* ------------------------------------------------------------------
 * Signing Configurations
 * ------------------------------------------------------------------ */

afterEvaluate {
	signing {
		useInMemoryPgpKeys(
			rpExtra["SIGNING_KEY_ID"] as String,
			rpExtra["SIGNING_KEY_B64"] as String,
			rpExtra["SIGNING_PASSWORD"] as String
		)
		sign(publishing.publications)
	}
}

/* ------------------------------------------------------------------
 * Dependencies
 * ------------------------------------------------------------------ */

dependencies {
	compileOnly("org.jetbrains:annotations:22.0.0")
	// Test dependencies
	testImplementation("junit:junit:4.13.2")
}
