package com.markklim.popcorn.coverage

import com.markklim.popcorn.plugin.coverage.CodeCoveragePlugin
import nebula.test.IntegrationSpec

class CodeCoveragePluginTest extends IntegrationSpec {

    def 'run build'() {
        buildFile << """
            ext {
                ${CodeCoveragePlugin.COVERAGE_EXCLUSION_PROPERTY} = ['**/**Data**',
                 '**/**DTO**']
            }
            ${applyPlugin(CodeCoveragePlugin)}
        """

        expect:
        runTasksSuccessfully('test')
    }
}
