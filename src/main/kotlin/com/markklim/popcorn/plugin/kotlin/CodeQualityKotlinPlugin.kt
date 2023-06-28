package com.markklim.popcorn.plugin.kotlin

import com.markklim.popcorn.util.style.KotlinStyleConfigInitializer
import com.markklim.popcorn.util.style.KotlinStyleConfigInitializer.Companion.GRADLE_CONFIG_PATH
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class CodeQualityKotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(JavaPlugin::class.java)
        project.plugins.apply(DetektPlugin::class.java)

        project.extensions.configure(DetektExtension::class.java) {
            it.config = project.files("${project.rootDir}/$DETECT_CONFIG_PATH")
        }

        project.tasks.withType(Detekt::class.java) {
            it.reports.xml.required.set(false)
            it.reports.txt.required.set(false)
        }

        project.dependencies.add(
            DETEKT_PLUGINS,
            "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_VERSION"
        )

        KotlinStyleConfigInitializer(project).init()
    }

    private companion object {
        private const val DETECT_CONFIG_PATH = "$GRADLE_CONFIG_PATH/detekt/config.yml"
        private const val DETEKT_PLUGINS = "detektPlugins"
        private const val DETEKT_VERSION = "1.21.0"
    }
}

