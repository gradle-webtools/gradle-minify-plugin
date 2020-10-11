package org.padler.gradle.minify

import org.gradle.api.tasks.Input
import org.padler.gradle.minify.minifier.CssMinifier
import org.padler.gradle.minify.minifier.options.CSSMinifierOptions

open class CssMinifyTask : MinifyTask() {

    var options = CSSMinifierOptions()
        set(value) {
            field = value
            inputOptions = value
        }
        get() = field

    @Input
    protected var inputOptions = options

    override fun minify() {
        if (srcDir == null && dstDir == null) {
            CssMinifier(options).minify(srcDir!!, dstDir!!)
        }
    }
}
