package com.markklim.popcorn.plugin

import com.markklim.popcorn.plugin.coverage.CodeCoveragePlugin
import com.markklim.popcorn.plugin.java.CodeQualityJavaPlugin
import com.markklim.popcorn.plugin.kotlin.CodeQualityKotlinPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class CodeQualityPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        applyToProject(project)

        project.subprojects { subProject: Project ->
            applyToProject(subProject)
        }
    }

    private fun applyToProject(project: Project) {
        if (isApplicable(project)) {
            applyPlugins(project)
        }
    }

    private fun isApplicable(project: Project): Boolean {
        return SUPPORTED_LANGUAGE_LOCATIONS.any { location ->
            project.file(location).exists()
        }
    }

    private fun applyPlugins(project: Project) {
        project.plugins.apply(CodeCoveragePlugin::class.java)

        if (project.file(KOTLIN_LANGUAGE_LOCATION).exists()) {
            project.plugins.apply(CodeQualityKotlinPlugin::class.java)
        } else {
            project.plugins.apply(CodeQualityJavaPlugin::class.java)
        }
    }

    private companion object {
        const val KOTLIN_LANGUAGE_LOCATION = "src/main/kotlin"
        const val JAVA_LANGUAGE_LOCATION = "src/main/java"
        const val GROOVY_LANGUAGE_LOCATION = "src/main/groovy"

        val SUPPORTED_LANGUAGE_LOCATIONS: List<String> = listOf(
            KOTLIN_LANGUAGE_LOCATION,
            JAVA_LANGUAGE_LOCATION,
            GROOVY_LANGUAGE_LOCATION,
        )
    }
}