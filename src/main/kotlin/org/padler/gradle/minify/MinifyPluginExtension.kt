package org.padler.gradle.minify

import org.padler.gradle.minify.minifier.css.CSSMinifierOptions
import org.padler.gradle.minify.minifier.js.JSMinifierOptions
import java.io.File

open class MinifyPluginExtension {

    var addDefaultJsMinifyTask = false
    var addDefaultCssMinifyTask = false

    internal val defaultJsMinifyTaskContext = DefaultJsMinifyTaskContext()
    internal val defaultCssMinifyTaskContext = DefaultCssMinifyTaskContext()

    fun js(block: DefaultJsMinifyTaskContext.() -> Unit) {
        defaultJsMinifyTaskContext.apply(block)
        addDefaultJsMinifyTask = true
    }

    fun css(block: DefaultCssMinifyTaskContext.() -> Unit) {
        defaultCssMinifyTaskContext.apply(block)
        addDefaultCssMinifyTask = true
    }

    class DefaultJsMinifyTaskContext internal constructor() {

        var srcDir: File? = null
        var dstDir: File? = null

        internal var options = JSMinifierOptions()

        fun options(block: JSMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }

        internal fun applyOn(task: JsMinifyTask) {
            task.srcDir = srcDir
            task.dstDir = dstDir
            task.options = options
        }
    }

    class DefaultCssMinifyTaskContext {

        var srcDir: File? = null
        var dstDir: File? = null

        internal var options = CSSMinifierOptions()

        fun options(block: CSSMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }

        internal fun applyOn(task: CssMinifyTask) {
            task.srcDir = srcDir
            task.dstDir = dstDir
            task.options = options
        }
    }
}
