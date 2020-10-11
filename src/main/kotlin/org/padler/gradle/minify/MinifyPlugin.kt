package org.padler.gradle.minify

import org.gradle.api.Plugin
import org.gradle.api.Project

open class MinifyPlugin : Plugin<Project> {

    override fun apply(project: Project) {

    }

    companion object {
        const val EXTENSION_NAME = "minification"
        const val TASK_NAME = "minify"
    }
}
