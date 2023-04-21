# popcorn

Gradle quality plugin

## Usage
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