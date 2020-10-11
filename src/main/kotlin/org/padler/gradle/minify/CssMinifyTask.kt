package org.padler.gradle.minify

import kotlinx.serialization.json.Json
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.padler.gradle.minify.minifier.CssMinifier
import org.padler.gradle.minify.minifier.options.CSSMinifierOptions

open class CssMinifyTask : MinifyTask() {

    private var options = CSSMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(CSSMinifierOptions.serializer(), value)
        }

    fun options(block: CSSMinifierOptions.() -> Unit) = options.apply(block).also { options = options }

    @Input
    protected var inputOptions = Json.encodeToString(CSSMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            CssMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }
}
