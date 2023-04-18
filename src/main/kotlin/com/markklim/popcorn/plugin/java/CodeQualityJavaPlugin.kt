package com.markklim.popcorn.plugin.java

import com.markklim.popcorn.util.style.JavaStyleConfigInitializer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import ru.vyarus.gradle.plugin.quality.QualityExtension
import ru.vyarus.gradle.plugin.quality.QualityPlugin

class CodeQualityJavaPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(JavaPlugin::class.java)
        project.plugins.apply(QualityPlugin::class.java)

        project.extensions.configure(QualityExtension::class.java) {
            it.configDir = "${project.rootDir}/$QUALITY_CONFIG_PATH"
            it.pmd = false
        }

        project.afterEvaluate {
            if (project.plugins.hasPlugin(CheckstylePlugin::class.java)) {
                project.extensions.configure(CheckstyleExtension::class.java) {
                    it.configDirectory.set(project.rootProject.file("$QUALITY_CONFIG_PATH/$CHECKSTYLE"))
                }
            }
        }

        JavaStyleConfigInitializer(project).init()
    }

    private companion object {
        const val QUALITY_CONFIG_PATH = ".gradle/quality/config"
        const val CHECKSTYLE = "checkstyle"
    }
}
