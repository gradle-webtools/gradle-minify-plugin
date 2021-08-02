package org.gradlewebtools.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.nio.file.Files

class `js minify task groovy - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val jsDir = File(testProjectDir, "js")
        jsDir.mkdir()
        val jsFile = File(jsDir, "js.js")
        val jsFileContent = """
                alert('Hello, world!');
                        
                alert('Hello, world!');
        
                alert('Hello, world!');
        
                alert('Hello, world!');
                """.trimIndent()
        jsFile.writeText(jsFileContent)
        val buildFile = File(testProjectDir, "build.gradle")
        val config = """
                plugins {
                    id 'org.gradlewebtools.minify'
                }
                task minify(type: org.gradlewebtools.minify.JsMinifyTask) {
                    srcDir = project.file("js")
                    dstDir = project.file("build/js")
                    options {
                        ignoreMinFiles = false
                        debug = false
                    }
                }
                """.trimIndent()
        buildFile.writeText(config)
    }

    @Test
    fun test() {
        setUpTestProject()
        val result = GradleRunner.create().apply {
            withProjectDir(testProjectDir)
            withPluginClasspath()
            withArguments("minify", "--stacktrace")
        }.build()
        result.task(":minify")!!.outcome shouldBe TaskOutcome.SUCCESS
        File(testProjectDir, "build/js/js.min.js").readText() shouldBe "'use strict';alert(\"Hello, world!\");" +
                "alert(\"Hello, world!\");alert(\"Hello, world!\");alert(\"Hello, world!\");"
    }
}
