# Gradle Minify Plugin! [![Build Status](https://travis-ci.org/616slayer616/gradle-minify-plugin.svg?branch=master)](https://travis-ci.org/616slayer616/gradle-minify-plugin)
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
    classpath "org.adler:gradle-minify-plugin:2.14.1"
  }
}

apply plugin: "org.padler.gradle.minify"
```

## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
