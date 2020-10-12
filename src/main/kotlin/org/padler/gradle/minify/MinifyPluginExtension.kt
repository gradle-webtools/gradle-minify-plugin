package org.padler.gradle.minify

import org.padler.gradle.minify.minifier.css.CSSMinifierOptions
import org.padler.gradle.minify.minifier.js.JSMinifierOptions
import java.io.File

open class MinifyPluginExtension {

    internal var defaultJsMinifyTaskContext: DefaultJsMinifyTaskContext? = null
        private set
    internal var defaultCssMinifyTaskContext: DefaultCssMinifyTaskContext? = null
        private set

    fun js(block: DefaultJsMinifyTaskContext.() -> Unit) {
        defaultJsMinifyTaskContext = defaultJsMinifyTaskContext ?: DefaultJsMinifyTaskContext().apply(block)
    }

    fun css(block: DefaultCssMinifyTaskContext.() -> Unit) {
        defaultCssMinifyTaskContext = defaultCssMinifyTaskContext ?: DefaultCssMinifyTaskContext().apply(block)
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
