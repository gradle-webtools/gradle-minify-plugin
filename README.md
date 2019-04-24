# Gradle Minify Plugin!
[![Build Status](https://travis-ci.org/616slayer616/gradle-minify-plugin.svg?branch=master)](https://travis-ci.org/616slayer616/gradle-minify-plugin)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=616slayer616_gradle-minify-plugin)](https://sonarcloud.io/dashboard/index/616slayer616_gradle-minify-plugin)
[![Comments (%)](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=comment_lines_density)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=comment_lines_density)
[![Open issues](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=open_issues)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=open_issues)
[![Code smells](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=code_smells)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=code_smells)
[![Technical debt](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=sqale_index)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=sqale_index)
[![Bugs](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=bugs)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=bugs)
[![Reliability remediation effort](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=reliability_remediation_effort)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=reliability_remediation_effort)
[![Coverage](https://sonarcloud.io/api/badges/measure?key=616slayer616_gradle-minify-plugin&metric=coverage)](https://sonarcloud.io/component_measures?id=616slayer616_gradle-minify-plugin&metric=coverage)

A simple gradle plugin to minify CSS and JavaScript files

# Getting started

### Gradle
```groovy
plugins {
  id "org.padler.gradle.minify" version "1.0"
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
    classpath "org.adler:gradle-minify-plugin:1.0"
  }
}

apply plugin: "org.padler.gradle.minify"
```

## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
