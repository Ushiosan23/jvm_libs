plugins {
	`kotlin-dsl`
}

repositories {
	mavenCentral()
	mavenLocal()
	maven {
		setUrl("https://plugins.gradle.org/m2/")
	}
}

dependencies {
	implementation("com.github.ushiosan23:jvm-utilities:1.0.0")
	implementation("io.github.gradle-nexus:publish-plugin:1.1.0")
}