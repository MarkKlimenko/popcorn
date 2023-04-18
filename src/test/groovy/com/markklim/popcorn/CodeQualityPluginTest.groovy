package com.markklim.popcorn

import com.markklim.popcorn.plugin.CodeQualityPlugin
import nebula.test.IntegrationSpec

class CodeQualityPluginTest extends IntegrationSpec {

    def 'run java build'() {
        given:
        fork = true
        System.properties.setProperty('ignoreDeprecations', 'true')

        buildFile << """\
            apply plugin: 'java'
            ${applyPlugin(CodeQualityPlugin)}

            repositories {
                mavenCentral()
            }
        """

        writeHelloWorld()

        expect:
        runTasksSuccessfully('build')
        runTasksSuccessfully("checkstyleMain")
        runTasksWithFailure("detekt")
    }

    def 'run kotlin build'() {
        given:
        fork = true
        System.properties.setProperty('ignoreDeprecations', 'true')

        buildFile << """
            apply plugin: 'java'
            ${applyPlugin(CodeQualityPlugin)}

            repositories {
                mavenCentral()
            }
        """
        def source = """\
            package nebula;
        
            class HelloWorld {
                fun main() {
                    print("Hello World")
                }
            }
         """.stripIndent()

        def kotlinFile = createFile("src/main/kotlin/nebula/HelloWorld.kt", getProjectDir())
        kotlinFile.text = source

        expect:
        runTasksSuccessfully('build')
        runTasksSuccessfully("detekt")
        runTasksWithFailure("checkstyleMain")
    }
}
