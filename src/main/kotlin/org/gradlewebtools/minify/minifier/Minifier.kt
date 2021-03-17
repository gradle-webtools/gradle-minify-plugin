package org.gradlewebtools.minify.minifier

import org.gradle.api.GradleException
import org.gradlewebtools.minify.minifier.result.Report
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption

abstract class Minifier {

    abstract val acceptedFileExtensions: List<String>
    abstract val minifierName: String
    abstract val minifierOptions: MinifierOptions

    val report = Report()

    companion object {

        private val LOGGER = LoggerFactory.getLogger(Minifier::class.java)
    }

    fun minify(srcDir: File, dstDir: File) {
        minifyInternal(srcDir, dstDir)
        if (LOGGER.isErrorEnabled) {
            LOGGER.error(createReport())
        }
        if (report.errors.isNotEmpty()) throw GradleException(report.errors.toString() + " Errors in " + minifierName)
    }

    private fun minifyInternal(srcDir: File, dstDir: File) {
        try {
            srcDir.listFiles()?.forEach {
                val f = it.toPath()
                if (it.isFile) {
                    handleFile(f, dstDir, it)
                } else if (it.isDirectory) {
                    minifyInternal(it, File(dstDir, f.fileName.toString()))
                }
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun shouldMinify(f: Path): Boolean {
        return !minifierOptions.ignoreMinFiles || !f.fileName.toString().contains(".min.");
    }

    private fun handleFile(f: Path, dstDir: File, it: File) {
        val dst = dstDir.toPath()
        var fileName = f.fileName.toString()
        val copy = File(dst.toString(), fileName)
        if (!minifierOptions.originalFileNames) {
            fileName = rename(fileName)
        }
        val dstFile = File(dst.toString(), fileName)
        dstFile.parentFile.mkdirs()
        if (acceptedFileExtensions.find { ext -> ext == it.extension } != null) {
            if ((minifierOptions.copyOriginalFile && !minifierOptions.originalFileNames) ||
                    !shouldMinify(f)) {
                Files.copy(f, copy.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
            if (shouldMinify(f)) {
                minifyFileInternal(f.toFile(), dstFile)
            }
        }
    }

    private fun minifyFileInternal(srcFile: File, dstFile: File) {
        try {
            minifyFile(srcFile, dstFile)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun createReport() = buildString {
        report.errors.forEach {
            appendLine("Error: $it")
        }
        report.warnings.forEach {
            appendLine("Warning: $it")
        }
        appendLine("$minifierName: ${report.errors.size} error(s), ${report.warnings.size} warning(s)")
    }

    protected fun writeToFile(dstFile: File, string: String) {
        try {
            val options = arrayOf(
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
            Files.write(dstFile.toPath(), string.toByteArray(), *options)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    protected abstract fun minifyFile(srcFile: File, dstFile: File)
    protected abstract fun rename(oldName: String): String
}
