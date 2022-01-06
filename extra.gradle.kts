import java.util.*

/* ------------------------------------------------------------------
 * Methods
 * ------------------------------------------------------------------ */

fun loadProperties(vararg files: File) = files.forEach { f ->
	if (!f.exists()) {
		println("File not found \"$f\"")
		return
	}
	// load data
	val props = Properties()
	props.load(f.inputStream())

	props.forEach { entry ->
		if (!rootProject.extra.has("${entry.key}")) {
			rootProject.extra.set("${entry.key}", entry.value)
		}
	}
}

fun loadEnvProps() {
	System.getenv().forEach { entry ->
		if (!rootProject.extra.has("${entry.key}")) {
			rootProject.extra.set("${entry.key}", entry.value)
		}
	}
}

/* ------------------------------------------------------------------
 * Calls
 * ------------------------------------------------------------------ */

loadEnvProps()

loadProperties(rootProject.file("gradle.properties"))
