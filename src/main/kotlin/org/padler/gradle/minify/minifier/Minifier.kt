package org.padler.gradle.minify.minifier

import org.gradle.api.GradleException
import org.padler.gradle.minify.minifier.options.MinifierOptions
import org.padler.gradle.minify.minifier.result.Report
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.*
import java.util.*
import java.util.stream.Collectors

abstract class Minifier {

    val report = Report()

    companion object {

        private val LOGGER = LoggerFactory.getLogger(Minifier::class.java)
    }

    fun minify(srcDir: File, dstDir: File): Unit = minify(srcDir.path, dstDir.path)

    fun minify(srcDir: String, dstDir: String) {
        minifyInternal(srcDir, dstDir)
        if (LOGGER.isErrorEnabled) {
            LOGGER.error(createReport())
        }
        if (report.errors.isNotEmpty()) throw GradleException(report.errors.toString() + " Errors in " + minifierName)
    }

    private fun minifyInternal(srcDir: String, dstDir: String) {
        try {
            Files.list(Paths.get(srcDir)).filter { f: Path -> f.toString() != srcDir }
                    .use { filesStream ->
                        val files = filesStream.collect(Collectors.toList())
                        for (f in files) {
                            if (f.toFile().isFile) {
                                val dst = Paths.get(dstDir)
                                var fileName = f.fileName.toString()
                                val copy = File(dst.toString(), fileName)
                                if (!minifierOptions.originalFileNames) {
                                    fileName = rename(fileName)
                                }
                                val dstFile = File(dst.toString(), fileName)
                                dstFile.parentFile.mkdirs()
                                if (fileTypeMatches(f)) {
                                    if (minifierOptions.copyOriginalFile) {
                                        Files.copy(f, copy.toPath(), StandardCopyOption.REPLACE_EXISTING)
                                    }
                                    minifyFileSave(f.toFile(), dstFile)
                                }
                            } else if (f.toFile().isDirectory) {
                                val newDstDir = dstDir + "/" + f.fileName.toString()
                                minifyInternal(f.toString(), newDstDir)
                            }
                        }
                    }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun minifyFileSave(srcFile: File, dstFile: File) {
        try {
            minifyFile(srcFile, dstFile)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun createReport(): String {
        val reportStr = StringBuilder()
        for (error in report.errors) {
            reportStr.append("Error: ")
            reportStr.append(error)
            reportStr.append("\n")
        }
        for (warning in report.warnings) {
            reportStr.append("Warning: ")
            reportStr.append(warning)
            reportStr.append("\n")
        }
        reportStr.append(minifierName)
                .append(": ")
                .append(report.errors.size)
                .append(" error(s), ")
                .append(report.warnings.size)
                .append(" warning(s)\n")
        return reportStr.toString()
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

    protected fun getExtension(filename: String): String {
        return Optional.ofNullable(filename)
                .filter { f: String -> f.contains(".") }
                .map { f: String -> f.substring(filename.lastIndexOf('.') + 1) }
                .orElse("")
    }

    protected abstract fun fileTypeMatches(f: Path): Boolean
    abstract val minifierOptions: MinifierOptions
    protected abstract val minifierName: String
    protected abstract fun minifyFile(srcFile: File, dstFile: File)

    protected abstract fun rename(oldName: String): String
}
