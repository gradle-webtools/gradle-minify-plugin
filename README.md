# Gradle Minify Plugin!
[![Build Status](https://travis-ci.com/616slayer616/gradle-minify-plugin.svg?branch=master)](https://travis-ci.com/616slayer616/gradle-minify-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=code_smells)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)

A simple gradle plugin to minify CSS and JavaScript files.

Uses [Google Closure Compiler](https://github.com/google/closure-compiler) and [Google Closure Stylesheets
](https://github.com/google/closure-stylesheets) for minification.

# Getting started

### Gradle
```groovy
plugins {
  id "org.padler.gradle.minify" version "1.3.0"
}
```

#### Legacy plugin application
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.padler.gradle.minify:gradle-minify-plugin:1.3.0"
  }
}

apply plugin: "org.padler.gradle.minify"
```

### Configuration
````groovy
minification {
    cssDstDir = "$buildDir/dist/css"
    cssSrcDir = "${rootDir}/src/main/resources/static/css"
    jsDstDir = "$buildDir/dist/js"
    jsSrcDir = "${rootDir}/src/main/resources/static/js"

    createCssSourceMaps = true
    createJsSourceMaps = true

    css {
        cssRenamingPrefix = ""
    }
}
````

## Options
| option                      | effect                                                     | values                           | default                      |
| --------------------------- | :--------------------------------------------------------: | -------------------------------: | ---------------------------: |
| createCssSourceMaps         | Creates CSS source maps                                    | true, false                      | false                        |
| createJsSourceMaps          | Creates JS source maps                                     | true, false                      | false                        |
| originalFileNames           | Preserves filenames instead of renaming to .min.           | true, false                      | false                        |
| css                         | Sets CSS minifier options                                  | [css options](#css-options)      | [css options](#css-options)  |

### CSS options
| option                        | effect                                                     | values                              | default                 |
| ---------------------------   | :--------------------------------------------------------: | -------------------------------:    | ----------------------: |
| inputOrientation              | inputOrientation                                           | JobDescription.InputOrientation     | LTR                     |
| outputOrientation             | outputOrientation                                          | JobDescription.OutputOrientation    | LTR                     |
| outputFormat                  | outputFormat                                               | JobDescription.OutputFormat         | COMPRESSED              |
| copyrightNotice               | copyrightNotice                                            | String                              | null                    |
| trueConditionNames            | trueConditionNames                                         | list of strings                     | empty list              |
| allowDefPropagation           | allowDefPropagation                                        | true, false                         | true                    |
| allowUnrecognizedFunctions    | allowUnrecognizedFunctions                                 | true, false                         | true                    |
| allowedNonStandardFunctions   | allowedNonStandardFunctions                                | list of strings                     | empty list              |
| allowedUnrecognizedProperties | allowedUnrecognizedProperties                              | list of strings                     | empty list              |
| allowUnrecognizedProperties   | allowUnrecognizedProperties                                | true, false                         | true                    |
| vendor                        | vendor                                                     | Vendor                              | null                    |
| allowKeyframes                | allowKeyframes                                             | true, false                         | true                    |
| allowWebkitKeyframes          | allowWebkitKeyframes                                       | true, false                         | true                    |
| processDependencies           | processDependencies                                        | true, false                         | true                    |
| excludedClassesFromRenaming   | excludedClassesFromRenaming                                | list of strings                     | empty list              |
| simplifyCss                   | simplifyCss                                                | true, false                         | true                    |
| eliminateDeadStyles           | eliminateDeadStyles                                        | true, false                         | false                   |
| cssRenamingPrefix             | CSS renaming prefix                                        | String                              | empty string            |
| preserveComments              | preserveComments                                           | true, false                         | false                   |
| outputRenamingMapFormat       | outputRenamingMapFormat                                    | OutputRenamingMapFormat             | JSON                    |
| compileConstants              | compileConstants                                           | empty map                           | empty map               |
| sourceMapLevel                | sourceMapLevel                                             | JobDescription.SourceMapDetailLevel | DEFAULT                 |


## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
