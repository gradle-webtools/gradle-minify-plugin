package org.gradlewebtools.minify

import org.gradle.api.Plugin
import org.gradle.api.Project

open class MinifyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("minification", MinifyPluginExtension::class.java)
        project.afterEvaluate {
            if (extension.addDefaultJsMinifyTask) {
                val task = project.tasks.register("jsMinify", JsMinifyTask::class.java)
                extension.defaultJsMinifyTaskContext.applyOn(task.get())
            }
            if (extension.addDefaultCssMinifyTask) {
                val task = project.tasks.register("cssMinify", CssMinifyTask::class.java)
                extension.defaultCssMinifyTaskContext.applyOn(task.get())
            }
        }
    }
}
