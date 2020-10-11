package org.padler.gradle.minify

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.gradle.util.GFileUtils
import java.io.File
import java.nio.file.Files

class `css minify task - integration` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    private fun setUpTestProject() {
        val buildFile = File(testProjectDir, "build.gradle.kts")
        val cssDir = File(testProjectDir, "css")
        cssDir.mkdir()
        val cssFile = File(cssDir, "css.css")
        cssFile.createNewFile()
        Files.write(
                cssFile.toPath(),
                "body {\n  color: black;\n}".toByteArray()
        )
        val plugin = "plugins { id (\"org.padler.gradle.minify\") version \"1.6.0\" }"
        val config = """
            tasks.create<org.padler.gradle.minify.CssMinifyTask>("minify") { 
                srcDir = project.file("css")
                dstDir = project.file("build/css")
            }
            """.trimIndent()
        GFileUtils.writeFile("$plugin\n$config", buildFile)
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
