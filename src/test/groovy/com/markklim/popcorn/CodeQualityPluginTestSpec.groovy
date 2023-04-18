package com.markklim.popcorn

import nebula.test.PluginProjectSpec

class CodeQualityPluginTestSpec extends PluginProjectSpec {

    @Override
    String getPluginName() {
        return "com.markklim.popcorn.quality"
    }
}
