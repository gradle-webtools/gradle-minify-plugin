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

    createJsSourceMaps = true
}
````

## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
