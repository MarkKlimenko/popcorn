# popcorn

Gradle quality plugin

## Usage
### Gradle setup
```
buildscript {
    ext {
        coverageExclusion = ['**/**']

        // versions
        codeQualityVersion = "1.1"
    }

    dependencies {
        classpath "com.markklim.plugins:popcorn-quality:$codeQualityVersion"
    }
}

plugins {
    id "com.markklim.popcorn.quality" version "$codeQualityVersion"
}
```
### Command
```
./gradlew clean build
```
### Configs
Configs location
```
gradle/style/config
```
Java config types
```
"checkstyle/checkstyle.xml"
"checkstyle/suppressions.xml"
"pmd/pmd.xml"
"spotbugs/exclude.xml"
"spotbugs/html-report-style.xsl"
"codenarc/codenarc.xml"
```
Kotlin config types
```
"detekt/config.yml"
```

## Plugin local testing
Add to plugin `build.gradle` file
```
// https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html

publishing {
    repositories {
        maven {
            name = 'localPluginRepository'
            url = '../local-plugin-repository'
        }
    }
}
```
Start publishing
```
./gradlew publish
```
Add to target project `settings.gradle` file
```
// https://docs.gradle.org/current/userguide/plugins.html#sec:custom_plugin_repositories

pluginManagement {
    repositories {
        maven {
            url '../local-plugin-repository'
        }
        gradlePluginPortal()
    }
}
```

## Publishing to portal 
``` 
./gradlew publishPlugins -Pgradle.publish.key= -Pgradle.publish.secret=
```