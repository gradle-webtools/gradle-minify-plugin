package org.padler.gradle.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import java.nio.file.Files

class `js minify task - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val buildFile = File(testProjectDir, "build.gradle.kts")
        val jsDir = File(testProjectDir, "js")
        jsDir.mkdir()
        val jsFile = File(jsDir, "js.js")
        jsFile.writeText("alert('Hello, world!');\n\nalert('Hello, world!');\n\n" +
                         "alert('Hello, world!');\n\nalert('Hello, world!');\n\n")
        val config = """
            plugins { id("org.padler.gradle.minify") }
            tasks.create<org.padler.gradle.minify.JsMinifyTask>("minify") { 
                srcDir = project.file("js")
                dstDir = project.file("build/js")
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
        File(testProjectDir, "build/js/js.min.js").readText() shouldBe "'use strict';alert(\"Hello, world!\");" +
                "alert(\"Hello, world!\");alert(\"Hello, world!\");alert(\"Hello, world!\");"
    }
}
