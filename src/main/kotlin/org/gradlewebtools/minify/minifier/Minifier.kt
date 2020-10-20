package org.gradlewebtools.minify.minifier

import org.gradle.api.GradleException
import org.gradlewebtools.minify.minifier.result.Report
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.OpenOption
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
                    val dst = dstDir.toPath()
                    var fileName = f.fileName.toString()
                    val copy = File(dst.toString(), fileName)
                    if (!minifierOptions.originalFileNames) {
                        fileName = rename(fileName)
                    }
                    val dstFile = File(dst.toString(), fileName)
                    dstFile.parentFile.mkdirs()
                    if (acceptedFileExtensions.find { ext -> ext == it.extension } != null) {
                        if (minifierOptions.copyOriginalFile && !minifierOptions.originalFileNames) {
                            Files.copy(f, copy.toPath(), StandardCopyOption.REPLACE_EXISTING)
                        }
                        minifyFileInternal(f.toFile(), dstFile)
                    }
                } else if (it.isDirectory) {
                    minifyInternal(it, File(dstDir, f.fileName.toString()))
                }
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
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
            appendln("Error: $it")
        }
        report.warnings.forEach {
            appendln("Warning: $it")
        }
        appendln("$minifierName: ${report.errors.size} error(s), ${report.warnings.size} warning(s)")
    }

    protected fun writeToFile(dstFile: File, string: String) {
        val create: OpenOption = StandardOpenOption.CREATE
        val write: OpenOption = StandardOpenOption.WRITE
        val truncateExisting: OpenOption = StandardOpenOption.TRUNCATE_EXISTING
        try {
            Files.write(dstFile.toPath(), string.toByteArray(), create, write, truncateExisting)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    protected abstract fun minifyFile(srcFile: File, dstFile: File)
    protected abstract fun rename(oldName: String): String
}
