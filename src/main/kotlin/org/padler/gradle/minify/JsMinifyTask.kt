package org.padler.gradle.minify

import kotlinx.serialization.json.Json
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.padler.gradle.minify.minifier.JsMinifier
import org.padler.gradle.minify.minifier.options.JSMinifierOptions

open class JsMinifyTask : MinifyTask() {

    private var options = JSMinifierOptions()
        set(value) {
            field = value
            inputOptions = Json.encodeToString(JSMinifierOptions.serializer(), value)
        }

    fun options(block: JSMinifierOptions.() -> Unit) = options.apply(block).also { options = options }

    @Input
    protected var inputOptions = Json.encodeToString(JSMinifierOptions.serializer(), options)

    @TaskAction
    fun minify() {
        if (srcDir != null && dstDir != null) {
            JsMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }
}
