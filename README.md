# popcorn

Gradle quality plugin

## Usage

### Gradle setup

**IMPORTANT!!!**

Plugin is tightly configured for kotlin version 1.9.23

```kotlin
kotlin("plugin.spring") version "1.9.23"
```

```.groovy
// build.gradle

buildscript {
    ext {
        coverageExclusion = ['**/**']

        // versions
        codeQualityVersion = "1.3.1"
    }
}

plugins {
    id "com.markklim.popcorn.quality" version "$codeQualityVersion"
}
```

```.kotlin
// build.gradle.kts

buildscript {
    extra.apply {
        set("coverageExclusion", listOf("**/**"))
    }
}

plugins {
    val codeQualityVersion = "1.3.1"
    id("com.markklim.popcorn.quality") version "$codeQualityVersion"
}
```

### Command

```.shell
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

```.groovy
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

```.shell
./gradlew publish
```

Add to target project `settings.gradle` file

```.groovy
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

```.kts
pluginManagement {
    repositories {
        maven(url = "../local-plugin-repository")
        gradlePluginPortal()
    }
}
```

## Publishing to portal

```.shell
./gradlew publishPlugins -Pgradle.publish.key= -Pgradle.publish.secret=
```
