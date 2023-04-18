package com.markklim.popcorn.kotlin

import com.markklim.popcorn.plugin.kotlin.CodeQualityKotlinPlugin
import nebula.test.IntegrationSpec

class CodeQualityKotlinPluginTest extends IntegrationSpec {

    def 'should apply detekt-formatting plugin'() {
        given:
        buildFile << """
            apply plugin: 'java'
            ${applyPlugin(CodeQualityKotlinPlugin)}     
           
        """.stripIndent()

        expect:
        def result = runTasksSuccessfully('dependencies')
        result.standardOutput.contains "io.gitlab.arturbosch.detekt:detekt-formatting:${CodeQualityKotlinPlugin.DETEKT_VERSION}"
    }

    def 'should run detekt task'() {
        given:
        buildFile << """
            apply plugin: 'java'
            ${applyPlugin(CodeQualityKotlinPlugin)}     
           
        """.stripIndent()

        expect:
        runTasksSuccessfully('detekt')
    }
}
