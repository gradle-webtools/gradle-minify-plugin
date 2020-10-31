package org.gradlewebtools.minify.minifier.js

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.gradle.api.GradleException
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

class `JsMinifier - class` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    @Test
    fun minifyFile() {
        val jsMinifier = JsMinifier()
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        jsMinifier.minify(File("src/test/resources/js"), dst)
        val files = dst.walk().toList().filterNot { it.path.endsWith("dst") }
        files shouldHaveSize 3
        val subDir = File(dst, "sub")
        val subFiles = subDir.walk().toList().filterNot { it.path.endsWith("sub") }
        subFiles shouldHaveSize 1
    }

    @Test
    fun minifyFileWithoutRenaming() {
        val jsMinifier = JsMinifier()
        jsMinifier.minifierOptions.originalFileNames = true
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        jsMinifier.minify(File("src/test/resources/js"), dst)
        val files = dst.walk().toList().filter { it.path.endsWith("dst/js.min.js") }
        files shouldHaveSize 0
    }

    @Test
    fun minifyFileWithSourceMaps() {
        val jsMinifier = JsMinifier()
        jsMinifier.minifierOptions.createSourceMaps = true
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        jsMinifier.minify(File("src/test/resources/js"), dst)
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 3
        val minifiedJs = files.stream()
                .filter { path: Path? -> path!!.toFile().name.endsWith(".min.js") }
                .collect(Collectors.toList())
        minifiedJs shouldHaveSize 1
        val path = minifiedJs[0]
        val lines = BufferedReader(FileReader(path!!.toFile())).lines().collect(Collectors.toList())
        lines[lines.size - 1] shouldBe "//# sourceMappingURL=" + path.fileName + ".map"
        val subDir = files.stream().filter { p: Path? -> p!!.toFile().name.endsWith("sub") }.findFirst().orElse(null)
        val subFiles = Files.list(subDir).collect(Collectors.toList())
        subFiles shouldHaveSize 2
    }

    @Test
    fun minifyFileWithError() {
        val jsMinifier = JsMinifier()
        jsMinifier.minifierOptions.createSourceMaps = true
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        shouldThrow<GradleException> {
            jsMinifier.minify(File("src/test/resources/errors/js"), dst)
        }
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 0
        jsMinifier.report.errors shouldHaveSize 1
        jsMinifier.report.warnings shouldHaveSize 0
    }

    @Test
    fun minifyEmptyFile() {
        val jsMinifier = JsMinifier()
        val src = File(testProjectDir, "empty")
        src.mkdir()
        val empty = File(src, "empty.js")
        empty.createNewFile()
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        jsMinifier.minify(src, dst)
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 1
    }
}
