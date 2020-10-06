plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("com.gradle.plugin-publish") version "0.12.0"
    id("maven-publish")
    id("java-gradle-plugin")
    id("groovy")
    id("io.freefair.lombok") version "5.2.1"
    id("jacoco")
    id("org.sonarqube") version "3.0"
}

group = "org.padler.gradle.minify"
version = "1.7.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

if (!project.hasProperty("gradle.publish.key"))
    ext["gradle.publish.key"] = System.getenv("GRADLE_PUBLISH_KEY")

if (!project.hasProperty("gradle.publish.secret"))
    ext["gradle.publish.secret"] = System.getenv("GRADLE_PUBLISH_SECRET")

tasks.test {
    finalizedBy("jacocoTestReport")
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

jacoco {
    toolVersion = "0.8.6"
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
    // https://mvnrepository.com/artifact/com.google.auto.value/auto-value-annotations
    implementation("com.google.auto.value:auto-value-annotations:1.7.4")

    // https://mvnrepository.com/artifact/com.google.javascript/closure-compiler
    implementation("com.google.javascript:closure-compiler:v20200920")

    implementation("org.padler:closure-stylesheets:1.6.0")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")

    // https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.17.2")
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
        property("sonar.coverage.exclusions", "**/*MinifyTask*,**/*Extension*")
        property("sonar.cpd.exclusions", "**/*Extension*,**/*Options*")
    }
}
