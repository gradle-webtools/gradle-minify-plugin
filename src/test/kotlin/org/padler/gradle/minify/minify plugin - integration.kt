package org.padler.gradle.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.gradle.util.GFileUtils
import java.io.File
import java.nio.file.Files

class `minify plugin - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val buildFile = File(testProjectDir, "build.gradle.kts")
        val config = """
            plugins { 
                id ("org.padler.gradle.minify")
            }
            minification {
                js {
                    srcDir = project.file("js")
                    dstDir = project.file("build/js")
                }
                css {
                    srcDir = project.file("css")
                    dstDir = project.file("build/css")
                }
            }
            """.trimIndent()
        buildFile.writeText(config)

        val jsDir = File(testProjectDir, "js")
        jsDir.mkdir()
        val jsFile = File(jsDir, "js.js")
        jsFile.writeText("alert('Hello, world!');\n\nalert('Hello, world!');\n\n" +
                         "alert('Hello, world!');\n\nalert('Hello, world!');\n\n")

        val cssDir = File(testProjectDir, "css")
        cssDir.mkdir()
        val cssFile = File(cssDir, "css.css")
        cssFile.writeText("body {\n  color: black;\n}")
    }

    @Test
    fun test() {
        setUpTestProject()
        val result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("jsMinify", "cssMinify", "--stacktrace")
                .build()
        result.task(":jsMinify") shouldNotBe null
        result.task(":jsMinify")!!.outcome shouldBe TaskOutcome.SUCCESS
        result.task(":cssMinify") shouldNotBe null
        result.task(":cssMinify")!!.outcome shouldBe TaskOutcome.SUCCESS
    }
}
