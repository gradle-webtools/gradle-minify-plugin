package org.gradlewebtools.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.nio.file.Files

class `css minify task groovy - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val cssDir = File(testProjectDir, "css")
        cssDir.mkdir()
        val cssFile = File(cssDir, "css.css")
        val cssFileContent = """
                body {
                    color: black;
                }
                """.trimIndent()
        cssFile.writeText(cssFileContent)
        val buildFile = File(testProjectDir, "build.gradle")
        val config = """
                plugins {
                    id 'org.gradlewebtools.minify'
                }
                task minify(type: org.gradlewebtools.minify.CssMinifyTask) {
                    srcDir = project.file("css")
                    dstDir = project.file("build/css")
                    options {
                        ignoreMinFiles = false
                        eliminateDeadStyles = false
                    }
                }
                """.trimIndent()
        buildFile.writeText(config)
    }

    @Test
    fun test() {
        setUpTestProject()
        val result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("minify", "--stacktrace")
                .build()
        result.task(":minify")!!.outcome shouldBe TaskOutcome.SUCCESS
        File(testProjectDir, "build/css/css.min.css").readText() shouldBe "body{color:black}"
    }
}
