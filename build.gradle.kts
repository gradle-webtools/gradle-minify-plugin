plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.71"
    id("com.gradle.plugin-publish") version "0.11.0"
    id("maven-publish")
    id("java-gradle-plugin")
    id("groovy")
    id("io.freefair.lombok") version "4.1.6"
    id("jacoco")
    id("org.sonarqube") version "2.8"
}

group = "org.padler.gradle.minify"
version = "1.4.5-SNAPSHOT"

if (!project.hasProperty("gradle.publish.key"))
    ext["gradle.publish.key"] = System.getenv("GRADLE_PUBLISH_KEY")

if (!project.hasProperty("gradle.publish.secret"))
    ext["gradle.publish.secret"] = System.getenv("GRADLE_PUBLISH_SECRET")

tasks.test {
    finalizedBy("jacocoTestReport")
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.yahoo.platform.yui:yuicompressor:2.4.8")

    // https://mvnrepository.com/artifact/com.google.auto.value/auto-value-annotations
    implementation("com.google.auto.value:auto-value-annotations:1.7")

    // https://mvnrepository.com/artifact/com.google.javascript/closure-compiler
    implementation("com.google.javascript:closure-compiler:v20200315")

    // https://mvnrepository.com/artifact/com.google.closure-stylesheets/closure-stylesheets
    implementation("com.google.closure-stylesheets:closure-stylesheets:1.5.0")

    testImplementation("junit:junit:4.12")
}

gradlePlugin {
    plugins {
        create("minifyPlugin") {
            id = "org.padler.gradle.minify"
            implementationClass = "org.padler.gradle.minify.MinifyPlugin"
            displayName = "Gradle Minify Plugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/616slayer616/gradle-minify-plugin"
    vcsUrl = "https://github.com/616slayer616/gradle-minify-plugin"
    description = "A simple gradle plugin to minify CSS and JavaScript files"
    tags = listOf("css", "javascript", "js", "minify", "minification")
}

sonarqube {
    properties {
        property("sonar.projectName", "Gradle Minify Plugin")
        property("sonar.projectKey", "616slayer616_gradle-minify-plugin")
        property("sonar.coverage.exclusions", "**/*Extension,**/*MinifyTaskBase")
    }
}
