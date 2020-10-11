package org.padler.gradle.minify

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.padler.gradle.minify.minifier.CssMinifier
import org.padler.gradle.minify.minifier.JsMinifier
import org.padler.gradle.minify.minifier.Minifier
import org.padler.gradle.minify.minifier.options.JSMinifierOptions
import org.padler.gradle.minify.minifier.options.MinifierOptions
import java.io.File

abstract class MinifyTask : DefaultTask() {

    @InputDirectory
    open var srcDir: File? = null

    @OutputDirectory
    open var dstDir: File? = null

    @TaskAction
    abstract fun minify()
}
