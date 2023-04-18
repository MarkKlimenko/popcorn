package com.markklim.popcorn.util.style

import org.gradle.api.Project
import ru.vyarus.gradle.plugin.quality.QualityExtension
import java.io.File

class JavaStyleConfigInitializer(
    project: Project,
) : AbstractStyleConfigInitializer(project) {

    override fun init() {
        CONFIG_LOCATIONS.forEach {
            copyConfig(project.file(USER_CONFIG_PATH), getConfigDir(), it)
        }
    }

    private fun getConfigDir(): File =
        project.rootProject.file(
            project.extensions.findByType(QualityExtension::class.java)?.configDir
                ?: throw IllegalArgumentException("QualityExtension not found")
        )

    companion object {
        private const val CHECK_STYLE = "checkstyle/checkstyle.xml"
        private const val CHECK_STYLE_SUPPRESSIONS = "checkstyle/suppressions.xml"
        private const val PMD = "pmd/pmd.xml"
        private const val SPOT_BUGS_EXCLUDE = "spotbugs/exclude.xml"
        private const val SPOT_BUGS = "spotbugs/html-report-style.xsl"
        private const val CODE_NARC = "codenarc/codenarc.xml"

        private val CONFIG_LOCATIONS: List<String> = listOf(
            CHECK_STYLE, CHECK_STYLE_SUPPRESSIONS, PMD, SPOT_BUGS_EXCLUDE, SPOT_BUGS, CODE_NARC
        )
    }
}