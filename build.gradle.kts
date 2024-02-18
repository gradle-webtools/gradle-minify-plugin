plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.gradle.plugin-publish") version "1.2.1"
    id("maven-publish")
    id("java-gradle-plugin")
    id("jacoco")
    id("org.sonarqube") version "4.4.1.3373"
}

group = "org.gradle-webtools.minify"
version = "2.0.0"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
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
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.google.javascript:closure-compiler:v20231112")
    implementation("org.padler:closure-stylesheets:1.6.5")

    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

gradlePlugin {
    website.set("https://github.com/gradle-webtools/gradle-minify-plugin")
    vcsUrl.set("https://github.com/gradle-webtools/gradle-minify-plugin")
    plugins {
        create("minifyPlugin") {
            id = "org.gradlewebtools.minify"
            implementationClass = "org.gradlewebtools.minify.MinifyPlugin"
            displayName = "Gradle Minify Plugin"
            description = "A simple gradle plugin to minify CSS and JavaScript files"
            tags.set(listOf("css", "javascript", "js", "minify", "minification"))
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "gradle-minify-plugin")
        property("sonar.organization", "gradle-webtools")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.exclusions", "**/*MinifyTask*,**/*Extension*,**/*MinifierOptions*")
        property("sonar.cpd.exclusions", "**/*Extension*,**/*Options*")
    }
}
