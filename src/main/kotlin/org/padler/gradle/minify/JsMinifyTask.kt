package org.padler.gradle.minify

import kotlinx.serialization.json.Json
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.padler.gradle.minify.minifier.js.JSMinifierOptions
import org.padler.gradle.minify.minifier.js.JsMinifier

open class JsMinifyTask : MinifyTask() {

    var options = JSMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(JSMinifierOptions.serializer(), value)
        }

    @Input
    protected var inputOptions = Json.encodeToString(JSMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            JsMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }

    fun options(block: JSMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }
}
