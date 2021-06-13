plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    id("com.gradle.plugin-publish") version "0.15.0"
    id("maven-publish")
    id("java-gradle-plugin")
    id("jacoco")
    id("org.sonarqube") version "3.3"
}

group = "org.gradle-webtools.minify"
version = "1.1.1"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
    toolVersion = "0.8.7"
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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("com.google.javascript:closure-compiler:v20210601")
    implementation("org.padler:closure-stylesheets:1.6.0")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.0")
    testImplementation("io.kotest:kotest-assertions-core:4.6.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("minifyPlugin") {
            id = "org.gradlewebtools.minify"
            implementationClass = "org.gradlewebtools.minify.MinifyPlugin"
            displayName = "Gradle Minify Plugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/gradle-webtools/gradle-minify-plugin"
    vcsUrl = "https://github.com/gradle-webtools/gradle-minify-plugin"
    description = "A simple gradle plugin to minify CSS and JavaScript files"
    tags = listOf("css", "javascript", "js", "minify", "minification")
}

sonarqube {
    properties {
        property("sonar.projectName", "Gradle Minify Plugin")
        property("sonar.projectKey", "gradle-minify-plugin")
        property("sonar.coverage.exclusions", "**/*MinifyTask*,**/*Extension*")
        property("sonar.cpd.exclusions", "**/*Extension*,**/*Options*")
    }
}
