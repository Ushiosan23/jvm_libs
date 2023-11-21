package types

import org.gradle.external.javadoc.JavadocOutputLevel

/* -----------------------------------------------------
 * Java
 * ----------------------------------------------------- */

/**
 * Javadoc information
 *
 * @param title javadoc document title
 * @param windowTitle javadoc website title
 * @param outputLevel javadoc output level
 * @param urls other javadoc urls
 */
data class JavadocInfo(
	val title: String? = null,
	val windowTitle: String? = null,
	val outputLevel: JavadocOutputLevel = JavadocOutputLevel.VERBOSE,
	val urls: List<String>? = null)

/* -----------------------------------------------------
 * Maven
 * ----------------------------------------------------- */

/**
 * Maven publication information
 *
 * @param name publication name
 * @param isSnapshot snapshot flag
 * @param groupId artifact group
 * @param artifactId artifact name
 * @param version artifact version
 * @param component artifact component
 * @param javadoc javadoc tasks information
 * @param pom maven pom information
 */
data class PublicationInfo(
	val name: String,
	val isSnapshot: Boolean = false,
	val groupId: String? = null,
	val artifactId: String? = null,
	val version: String? = null,
	val component: String = "java",
	val javadoc: MavenJavadocInfo = MavenJavadocInfo(),
	val pom: MavenPomInfo)

/**
 * Maven javadoc task information
 *
 * @param javadocTask jar javadoc task name
 * @param sourceTask jar source task name
 */
data class MavenJavadocInfo(
	val javadocTask: String? = "java-jar-javadoc",
	val sourceTask: String? = "java-jar-sources")

/**
 * Maven POM information
 *
 * @param artifactUrl project artifact url
 * @param artifactId project artifact identifier
 * @param description project artifact description
 * @param licenses project artifact licenses
 * @param developers project artifact contributors
 * @param scm project artifact scm connection
 */
data class MavenPomInfo(
	val artifactUrl: String,
	val artifactId: String? = null,
	val description: String? = null,
	val licenses: List<LicenseInfo>? = null,
	val developers: List<DeveloperInfo>? = null,
	val scm: ScmInfo? = null)

/**
 * Project license information
 *
 * @param name license name
 * @param url project license url
 */
data class LicenseInfo(
	val name: String = "MIT",
	val url: String = "https://opensource.org/licenses/MIT")

/**
 * Developer class information
 *
 * @param id developer unique identifier
 * @param name developer name
 * @param email developer contact email
 * @param organization developer organization name
 * @param organizationUrl developer organization url
 * @param roles developer roles
 * @param timezone developer timezone
 * @param url developer personal website
 */
data class DeveloperInfo(
	val id: String,
	val name: String? = null,
	val email: String? = null,
	val organization: String? = null,
	val organizationUrl: String? = null,
	val roles: List<String>? = null,
	val timezone: String? = null,
	val url: String? = null)

/**
 * Version control connection information
 *
 * @param url scm connection url
 * @param branch scm target connection branch
 * @param connection scm type connection
 * @param developerConnection scm secure connection
 */
data class ScmInfo(
	val url: String = "",
	val branch: String = "main",
	val connection: String? = null,
	val developerConnection: String? = null) {
	
	fun validUrl(): String {
		var result = url
		// Check if url ends with git and slash suffix
		listOf(".git", "/").forEach {
			if (result.endsWith(it))
				result = result.substring(0..result.length - it.length)
		}
		
		return "$url/tree/$branch"
	}
	
	fun validConnection(ssh: Boolean = false): String {
		// Check if connection exists
		if (connection != null) return connection
		// Generate connection result
		var result = url
		listOf("http://", "https://").forEach {
			if (result.startsWith(it))
				result = result.substring(it.length)
		}
		
		// Check if connection ends with git url
		if (!result.endsWith(".git"))
			result += ".git"
		
		return (if (ssh) "scm:git:ssh:" else "scm:git:") + result
	}
	
}

/**
 * Maven signature information
 *
 * @param keyId signature name
 * @param password signature password
 * @param pgpKeyB64 signature blob (Base64)
 */
data class SigningInfo(
	val keyId: String,
	val password: String,
	val pgpKeyB64: String)