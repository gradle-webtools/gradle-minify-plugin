package org.padler.gradle.minify.minifier.css

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

class `CssMinifier - class` : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    var testProjectDir: File = Files.createTempDirectory("test_gradle_project_dir").toFile().apply {
        afterSpec {
            deleteRecursively()
        }
    }

    @Test
    fun minifyFile() {
        val cssMinifier = CssMinifier()
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        cssMinifier.minify(File("src/test/resources/css"), dst)
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 2
        val subDir = files.stream().filter { p: Path? -> p!!.toFile().name.endsWith("sub") }.findFirst().orElse(null)
        val subFiles = Files.list(subDir).collect(Collectors.toList())
        subFiles shouldHaveSize 1
    }

    @Test
    fun minifyFileWithSourceMaps() {
        val cssMinifier = CssMinifier()
        cssMinifier.minifierOptions.createSourceMaps = true
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        cssMinifier.minify(File("src/test/resources/css"), dst)
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 3
        val minifiedCss = files.stream()
                .filter { path: Path? -> path!!.toFile().name.endsWith(".min.css") }
                .collect(Collectors.toList())
        minifiedCss shouldHaveSize 1
        val path = minifiedCss[0]
        val lines = BufferedReader(FileReader(path!!.toFile())).lines().collect(Collectors.toList())
        lines[lines.size - 1] shouldBe "//# sourceMappingURL=" + path.fileName + ".map"
        val subDir = files.stream().filter { p: Path? -> p!!.toFile().name.endsWith("sub") }.findFirst().orElse(null)
        val subFiles = Files.list(subDir).collect(Collectors.toList())
        subFiles shouldHaveSize 2
    }

    @Test
    fun minifyFileWithError() {
        val cssMinifier = CssMinifier()
        val dst = File(testProjectDir, "dst")
        dst.mkdir()
        cssMinifier.minify(File("src/test/resources/errors/css"), dst)
        val files = Files.list(Paths.get(dst.absolutePath + "/")).collect(Collectors.toList())
        files shouldHaveSize 1
        cssMinifier.report.errors shouldHaveSize 0
        cssMinifier.report.warnings shouldHaveSize 1
    }
}
