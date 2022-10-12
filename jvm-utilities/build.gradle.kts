import defined.project.JvmUtilitiesProject

plugins {
	id("common-java-library")
	id("common-maven-publishing")
}

JvmUtilitiesProject.configureAll(project)