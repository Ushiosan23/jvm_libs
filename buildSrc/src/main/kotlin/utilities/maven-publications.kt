package utilities

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.*
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskContainer
import org.gradle.jvm.tasks.Jar
import types.*

fun generatePublication(ext: PublishingExtension, publication: PublicationInfo, project: Project) =
	with(ext) {
		// Enter to publications context
		publications {
			// Generate publication
			create(publication.name, MavenPublication::class.java) {
				configurePublication(Pair(this, publication), project)
			}
		}
	}

fun generatePublicationTasks(container: TaskContainer, publication: PublicationInfo, project: Project,
							 sourceSet: SourceSet?) =
	with(container) {
		// Generate javadoc task
		publication.javadoc.javadocTask?.let { taskName ->
			create(taskName, Jar::class.java) {
				archiveClassifier.set("javadoc")
				project.tasks
					.findByName("javadoc")
					?.let {
						from(it)
						dependsOn(it)
					}
			}
		}
		
		// Generate source task
		publication.javadoc.sourceTask?.let { taskName ->
			create(taskName, Jar::class.java) {
				archiveClassifier.set("sources")
				sourceSet?.let {
					from(it.java.srcDirs)
				}
			}
		}
	}

/* -----------------------------------------------------
 * Internal methods
 * ----------------------------------------------------- */

private fun configurePublication(config: Pair<MavenPublication, PublicationInfo>, project: Project) =
	with(config.first) {
		// Temporal variables
		val pGroupId = config.second.groupId ?: project.group as String
		val pTmpVersion = config.second.version ?: project.version as String
		val pArtifactId = config.second.artifactId ?: return@with
		val pVersion = if (config.second.isSnapshot) {
			"$pTmpVersion-SNAPSHOT"
		} else {
			pTmpVersion
		}
		
		// Base configuration
		groupId = pGroupId
		version = pVersion
		artifactId = pArtifactId
		
		// Configure components
		project.components
			.findByName(config.second.component)
			?.let(this::from)
		
		// Register javadoc information
		val javadocTasks = listOf(config.second.javadoc.javadocTask, config.second.javadoc.sourceTask)
		for (task in javadocTasks) {
			if (task == null) continue
			// Check if task already exists
			project.tasks
				.findByName(task)
				?.let(this::artifact)
		}
		
		// Configure POM information
		pom {
			configurePom(Pair(this, config.second))
		}
	}

private fun configurePom(config: Pair<MavenPom, PublicationInfo>) =
	with(config.first) {
		// Base pom configuration
		url.set(config.second.pom.artifactUrl)
		name.set(config.second.name)
		config.second.pom.description
			?.let(description::set)
		
		// Generate licenses
		licenses {
			val pomLicenses = config.second.pom.licenses ?: return@licenses
			for (item in pomLicenses) {
				license {
					name.set(item.name)
					url.set(item.url)
				}
			}
		}
		
		// Generate developers
		developers {
			val pomDevelopers = config.second.pom.developers ?: return@developers
			for (item in pomDevelopers) {
				developer {
					configureDeveloper(this, item)
				}
			}
		}
		
		// Generate SCM connection
		val pomScm = config.second.pom.scm ?: return@with
		var finalScm = pomScm
		if (finalScm.url.isBlank()) {
			finalScm = ScmInfo(config.second.pom.artifactUrl, finalScm.branch)
		}
		
		scm {
			url.set(finalScm.validUrl())
			connection.set(finalScm.validConnection())
			developerConnection.set(finalScm.validConnection(true))
		}
	}

private fun configureDeveloper(developer: MavenPomDeveloper, info: DeveloperInfo) =
	with(developer) {
		// Required information
		id.set(info.id)
		// Optional information
		info.organizationUrl?.let(organizationUrl::set)
		info.organization?.let(organization::set)
		info.timezone?.let(timezone::set)
		info.email?.let(email::set)
		info.name?.let(name::set)
		info.url?.let(url::set)
		info.roles?.let(roles::set)
	}