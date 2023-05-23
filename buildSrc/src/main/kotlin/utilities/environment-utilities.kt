package utilities

import org.gradle.api.Project
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/* -----------------------------------------------------
 * Properties
 * ----------------------------------------------------- */

/**
 * Regular expression to detect a property extension
 */
private val filterRegex = Regex(".*\\.(properties|env)")

/**
 * All cached files
 */
private val cacheFiles: MutableList<Path> =
	mutableListOf()

/**
 * Properties cache
 */
private val cache: MutableMap<String, String> =
	mutableMapOf()

/* -----------------------------------------------------
 * Functions
 * ----------------------------------------------------- */

fun env(project: Project, key: String): String? {
	// Check if cache is empty
	checkForNews(project)
	// Check if key exists
	return cache[key]
}

fun env(project: Project, key: String, default: String): String =
	env(project, key) ?: default

/* -----------------------------------------------------
 * Internal functions
 * ----------------------------------------------------- */

fun checkForNews(project: Project) {
	val root = project.rootProject
	val files = Files.walk(root.projectDir.toPath(), 1)
		.filter(Files::isRegularFile)
		.filter { filterRegex.containsMatchIn(it.fileName.toString()) }
	
	// Check if cache contains any of these files
	for (file in files) {
		// Check if file already exists
		if (file in cacheFiles) continue
		// Load file
		val tmpProps = Properties()
		attachFile(file, tmpProps, project)
		
		// Combine content
		for ((k, v) in tmpProps) {
			cache[k as String] = v as String
		}
	}
	
	// Attach environment
	attachEnvironment()
}

private fun attachFile(file: Path, properties: Properties, project: Project) {
	// Check if file exists
	if (!Files.exists(file) || cacheFiles.contains(file)) return
	
	println("Loading: \"$file\" -> :${project.name}")
	// Load configuration
	try {
		val tmpProperties = Properties()
		val tmpStream = Files.newInputStream(file)
		
		// Load properties
		tmpStream.use { tmpProperties.load(it) }
		
		// Set properties from file
		for (entry in tmpProperties.entries) {
			// Put entries
			properties[entry.key] = entry.value
		}
		// Attach to loaded files
		cacheFiles.add(file)
	} catch (e: Exception) {
		println("\"$file\" Failed. ${e.message}")
	}
}

private fun attachEnvironment() {
	val environment = System.getenv()
	for ((key, value) in environment) {
		if (key in cache && cache[key] == value) continue
		cache[key] = value
	}
}