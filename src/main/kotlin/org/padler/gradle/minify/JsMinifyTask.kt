package org.padler.gradle.minify

import org.gradle.api.tasks.Input
import org.padler.gradle.minify.minifier.JsMinifier
import org.padler.gradle.minify.minifier.options.JSMinifierOptions

open class JsMinifyTask : MinifyTask() {

    var options = JSMinifierOptions()
        set(value) {
            field = value
            inputOptions = value
        }
        get() = field

    @Input
    protected var inputOptions = options

    override fun minify() {
        if (srcDir == null && dstDir == null) {
            JsMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }
}
