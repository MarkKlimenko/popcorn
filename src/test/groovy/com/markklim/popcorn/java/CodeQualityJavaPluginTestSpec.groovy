package com.markklim.popcorn.java

import nebula.test.PluginProjectSpec

class CodeQualityJavaPluginTestSpec extends PluginProjectSpec {

    @Override
    String getPluginName() {
        return "com.markklim.popcorn.quality.java"
    }
}
