package com.markklim.popcorn.util.style

import org.gradle.api.Project
import java.io.File

class KotlinStyleConfigInitializer(
    project: Project,
) : AbstractStyleConfigInitializer(project) {

    override fun init() {
        CONFIG_LOCATIONS.forEach {
            copyConfig(project.file(USER_CONFIG_PATH), getConfigDir(), it)
        }
    }

    private fun getConfigDir(): File = project.rootProject.file(GRADLE_CONFIG_PATH)

    companion object {
        const val GRADLE_CONFIG_PATH = ".gradle/quality/configs/"

        private const val DETEKT = "detekt/config.yml"

        private val CONFIG_LOCATIONS: List<String> = listOf(
            DETEKT
        )
    }
}