package org.padler.gradle.minify

import org.gradle.api.Plugin
import org.gradle.api.Project

open class MinifyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("minification", MinifyPluginExtension::class.java)
        if (extension.defaultJsMinifyTaskContext != null) { //todo consider using boolean flag instead to avoid null
            val task = project.tasks.create("jsMinify", JsMinifyTask::class.java)
            extension.defaultJsMinifyTaskContext!!.applyOn(task)
        }
        if (extension.defaultCssMinifyTaskContext != null) { //todo consider using boolean flag instead to avoid null
            val task = project.tasks.create("cssMinify", CssMinifyTask::class.java)
            extension.defaultCssMinifyTaskContext!!.applyOn(task)
        }
    }
}
