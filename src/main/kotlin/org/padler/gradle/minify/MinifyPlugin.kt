package org.padler.gradle.minify

import org.gradle.api.Plugin
import org.gradle.api.Project

open class MinifyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("minification", MinifyPluginExtension::class.java)
        project.afterEvaluate {
            if (extension.addDefaultCssMinifyTask) {
                val task = project.tasks.create("jsMinify", JsMinifyTask::class.java)
                extension.defaultJsMinifyTaskContext.applyOn(task)
            }
            if (extension.addDefaultCssMinifyTask) {
                val task = project.tasks.create("cssMinify", CssMinifyTask::class.java)
                extension.defaultCssMinifyTaskContext.applyOn(task)
            }
        }
    }
}
