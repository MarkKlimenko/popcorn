package com.markklim.popcorn.kotlin


import nebula.test.PluginProjectSpec

class CodeQualityKotlinPluginTestSpec extends PluginProjectSpec {

    @Override
    String getPluginName() {
        return "com.markklim.popcorn.quality.kotlin"
    }
}
