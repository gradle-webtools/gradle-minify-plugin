package org.gradlewebtools.minify

import kotlinx.serialization.json.Json
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradlewebtools.minify.minifier.css.CSSMinifierOptions
import org.gradlewebtools.minify.minifier.css.CssMinifier

open class CssMinifyTask : MinifyTask() {

    @Internal
    var options = CSSMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(CSSMinifierOptions.serializer(), value)
        }

    @Input
    protected var inputOptions = Json.encodeToString(CSSMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            CssMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }

    fun options(block: CSSMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }
}
