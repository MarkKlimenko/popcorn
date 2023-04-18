package com.markklim.popcorn.plugin.coverage

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class CodeCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(JavaPlugin::class.java)
        project.plugins.apply(JacocoPlugin::class.java)
        configureJacoco(project)
    }

    private fun configureJacoco(project: Project) {
        val jacocoExclusion: List<String> =
            if (project.hasProperty(COVERAGE_EXCLUSION_PROPERTY)) {
                DEFAULT_EXCLUSION + project.property(COVERAGE_EXCLUSION_PROPERTY) as List<String>
            } else {
                DEFAULT_EXCLUSION
            }

        project.extensions.configure(JacocoPluginExtension::class.java) { plugin ->
            plugin.toolVersion = JACOCO_VERSION
        }

        project.tasks.withType(JacocoReport::class.java) { report ->
            report.reports {
                it.xml.required.set(false)
                it.csv.required.set(false)
                it.html.outputLocation.set(project.file("${project.buildDir}/reports/coverage"))
            }
            project.afterEvaluate {
                report.classDirectories.setFrom(project.files(
                    report.classDirectories.files.map {
                        project.fileTree(
                            mapOf(
                                "dir" to it,
                                "exclude" to jacocoExclusion
                            )
                        )
                    }
                ))
            }
        }

        project.tasks.withType(Test::class.java) { test ->
            test.extensions.configure(JacocoTaskExtension::class.java) {
                it.destinationFile = project.file("${project.buildDir}/jacoco/test.exec")
            }
        }

        project.tasks.withType(JacocoCoverageVerification::class.java) { verification ->
            verification.violationRules.rule { rule ->
                rule.element = "CLASS"
                rule.excludes = jacocoExclusion.map { it.replace('/', '.') }

                rule.limit {
                    it.counter = "INSTRUCTION"
                    it.minimum = 0.7.toBigDecimal()
                }
                rule.limit {
                    it.counter = "METHOD"
                    it.minimum = 0.8.toBigDecimal()
                }
                rule.limit {
                    it.counter = "BRANCH"
                    it.minimum = 0.8.toBigDecimal()
                }
            }
        }

        project.afterEvaluate {
            project.tasks.findByName("test")
                ?.finalizedBy(JACOCO_TEST_REPORT)
            project.tasks.findByName("check")
                ?.dependsOn(JACOCO_TEST_COVERAGE_VERIFICATION)

            if (project.plugins.hasPlugin("org.junit.platform.gradle.plugin")) {
                val junitPlatformTestTask: JavaExec = project.tasks.getByName(JUNIT_PLATFORM_TEST) as JavaExec

                project.extensions.findByType(JacocoPluginExtension::class.java)
                    ?.applyTo(junitPlatformTestTask)

                val extension: JacocoTaskExtension =
                    junitPlatformTestTask.extensions.findByName(JACOCO) as JacocoTaskExtension

                extension.destinationFile = project.file("${project.buildDir}/jacoco/test.exec")

                project.tasks.findByName(JACOCO_TEST_COVERAGE_VERIFICATION)
                    ?.dependsOn(JUNIT_PLATFORM_TEST)

                project.tasks.findByName(DETEKT)
                    ?.dependsOn(JACOCO_TEST_COVERAGE_VERIFICATION)

                project.tasks.findByName(CHECKSTYLE)
                    ?.dependsOn(JACOCO_TEST_COVERAGE_VERIFICATION)
            }
        }
    }

    companion object {
        const val COVERAGE_EXCLUSION_PROPERTY = "coverageExclusion"

        private const val JACOCO_VERSION = "0.8.8"

        private const val JACOCO_TEST_COVERAGE_VERIFICATION = "jacocoTestCoverageVerification"
        private const val JACOCO_TEST_REPORT = "jacocoTestReport"
        private const val JACOCO = "jacoco"
        private const val CHECKSTYLE = "checkstyle"
        private const val DETEKT = "detekt"
        private const val JUNIT_PLATFORM_TEST = "junitPlatformTest"

        private val DEFAULT_EXCLUSION: List<String> = listOf()
    }
}
