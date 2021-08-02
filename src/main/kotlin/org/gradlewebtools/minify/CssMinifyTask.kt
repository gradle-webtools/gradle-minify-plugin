package org.gradlewebtools.minify

import kotlinx.serialization.json.Json
import org.gradle.api.Action
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradlewebtools.minify.minifier.css.CssMinifierOptions
import org.gradlewebtools.minify.minifier.css.CssMinifier

open class CssMinifyTask : MinifyTask() {

    @Internal
    var options = CssMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(CssMinifierOptions.serializer(), value)
        }

    @Input
    protected var inputOptions = Json.encodeToString(CssMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            CssMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }

    fun options(action: Action<CssMinifierOptions>) = options { action.execute(this) }

    fun options(block: CssMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }
}
