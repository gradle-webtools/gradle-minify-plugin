# Gradle Minify Plugin! [![Build Status](https://secure.travis-ci.org/adler/gradle-minify-plugin.png)](http://travis-ci.org/adler/gradle-minify-plugin)
A simple gradle plugin to minify CSS and JavaScript files

# Getting started
As of now, usage requires a locally installed plugin artifact 

```bash
./gradlew install
```

### Gradle
```groovy
buildscript {
    repositories{
        mavenLocal()
    }
    dependencies {
        classpath 'org.padler.gradle.minify:gradle-minify-plugin:1.0'
    }
}

apply plugin: 'org.padler.gradle.minify'

minification {
    cssDstDir = "$buildDir/dist/css"
    cssSrcDir = "${rootDir}/src/main/resources/static/css"
    jsDstDir = "$buildDir/dist/js"
    jsSrcDir = "${rootDir}/src/main/resources/static/js"
}
```

## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
