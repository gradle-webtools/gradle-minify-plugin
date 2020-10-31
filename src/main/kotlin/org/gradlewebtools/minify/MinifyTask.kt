package org.gradlewebtools.minify

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import java.io.File

abstract class MinifyTask : DefaultTask() {

    @InputDirectory
    open var srcDir: File? = null

    @OutputDirectory
    open var dstDir: File? = null
}
