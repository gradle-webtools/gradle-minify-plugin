package org.gradlewebtools.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.nio.file.Files

class `minify plugin groovy - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val buildFile = File(testProjectDir, "build.gradle")
        val config = """
                plugins { 
                    id 'org.gradlewebtools.minify'
                }
                minification {
                    js {
                        srcDir = file("js")
                        dstDir = file("build/js")
                    }
                    css {
                        srcDir = file("css")
                        dstDir = file("build/css")
                    }
                }
                """.trimIndent()
        buildFile.writeText(config)
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
        val cssDir = File(testProjectDir, "css")
        cssDir.mkdir()
        val cssFile = File(cssDir, "css.css")
        val cssFileContent = """
                body {
                    color: black;
                }
                """.trimIndent()
        cssFile.writeText(cssFileContent)
    }

    @Test
    fun test() {
        setUpTestProject()
        val result = GradleRunner.create().apply {
            withProjectDir(testProjectDir)
            withPluginClasspath()
            withArguments("jsMinify", "cssMinify", "--stacktrace")
        }.build()
        result.task(":jsMinify") shouldNotBe null
        result.task(":jsMinify")!!.outcome shouldBe TaskOutcome.SUCCESS
        result.task(":cssMinify") shouldNotBe null
        result.task(":cssMinify")!!.outcome shouldBe TaskOutcome.SUCCESS
    }
}
