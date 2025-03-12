package org.gradlewebtools.minify.minifier.css

import net.logicsquad.minifier.AbstractMinifier
import net.logicsquad.minifier.MinificationException
import net.logicsquad.minifier.css.CSSMinifier
import org.gradlewebtools.minify.minifier.Minifier
import java.io.*

/**
 * Uses closure stylesheets.
 * Implemented with help from https://github.com/marcodelpercio https://github.com/google/closure-stylesheets/issues/101
 */
class CssMinifier(override var minifierOptions: CssMinifierOptions = CssMinifierOptions()) : Minifier() {

    override val minifierName = "Css Minifier"

    override val acceptedFileExtensions: List<String> = listOf("css")

    override fun minifyFile(srcFile: File, dstFile: File) {
        try {
            val input: Reader = FileReader(srcFile)
            val output: Writer = FileWriter(dstFile)
            val min: AbstractMinifier = CSSMinifier(input)

            min.minify(output)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        } catch (e: MinificationException) {
            throw RuntimeException(e)
        }
    }

    override fun rename(oldName: String): String {
        return oldName.replace(".css", ".min.css")
    }
}
