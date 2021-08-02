package org.gradlewebtools.minify

import kotlinx.serialization.json.Json
import org.gradle.api.Action
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradlewebtools.minify.minifier.js.JsMinifierOptions
import org.gradlewebtools.minify.minifier.js.JsMinifier

open class JsMinifyTask : MinifyTask() {

    @Internal
    var options = JsMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(JsMinifierOptions.serializer(), value)
        }

    @Input
    protected var inputOptions = Json.encodeToString(JsMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            JsMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }

    fun options(action: Action<JsMinifierOptions>) = options { action.execute(this) }

    fun options(block: JsMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }
}
