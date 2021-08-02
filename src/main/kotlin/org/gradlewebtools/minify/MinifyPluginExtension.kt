package org.gradlewebtools.minify

import org.gradle.api.Action
import org.gradlewebtools.minify.minifier.css.CSSMinifierOptions
import org.gradlewebtools.minify.minifier.js.JSMinifierOptions
import java.io.File

open class MinifyPluginExtension {

    var addDefaultJsMinifyTask = false
    var addDefaultCssMinifyTask = false

    internal val defaultJsMinifyTaskContext = DefaultJsMinifyTaskContext()
    internal val defaultCssMinifyTaskContext = DefaultCssMinifyTaskContext()

    fun js(action: Action<DefaultJsMinifyTaskContext>) = js { action.execute(this) }

    fun js(block: DefaultJsMinifyTaskContext.() -> Unit) {
        defaultJsMinifyTaskContext.apply(block)
        addDefaultJsMinifyTask = true
    }

    fun css(action: Action<DefaultCssMinifyTaskContext>) = css { action.execute(this) }

    fun css(block: DefaultCssMinifyTaskContext.() -> Unit) {
        defaultCssMinifyTaskContext.apply(block)
        addDefaultCssMinifyTask = true
    }

    class DefaultJsMinifyTaskContext internal constructor() {

        var srcDir: File? = null
        var dstDir: File? = null

        var options = JSMinifierOptions()

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

        var options = CSSMinifierOptions()

        fun options(block: CSSMinifierOptions.() -> Unit) = options.copy().apply(block).apply { options = this }

        internal fun applyOn(task: CssMinifyTask) {
            task.srcDir = srcDir
            task.dstDir = dstDir
            task.options = options
        }
    }
}
