package org.padler.gradle.minify.minifier.options

import com.google.common.collect.Lists
import com.google.common.css.JobDescription.*
import com.google.common.css.OutputRenamingMapFormat
import com.google.common.css.Vendor
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CSSMinifierOptions(
        var inputOrientation: InputOrientation = InputOrientation.LTR,
        var outputOrientation: OutputOrientation = OutputOrientation.LTR,
        var outputFormat: OutputFormat = OutputFormat.COMPRESSED,
        var copyrightNotice: String? = null,
        var trueConditionNames: List<String> = Lists.newArrayList(),
        var allowDefPropagation: Boolean = true,
        var allowUnrecognizedFunctions: Boolean = true,
        var allowedNonStandardFunctions: List<String> = Lists.newArrayList(),
        var allowedUnrecognizedProperties: List<String> = Lists.newArrayList(),
        var allowUnrecognizedProperties: Boolean = true,
        var vendor: Vendor? = null,
        var allowKeyframes: Boolean = true,
        var allowWebkitKeyframes: Boolean = true,
        var processDependencies: Boolean = true,
        var excludedClassesFromRenaming: List<String> = Lists.newArrayList(),
        var simplifyCss: Boolean = true,
        var eliminateDeadStyles: Boolean = false,
        var cssRenamingPrefix: String = "",
        var preserveComments: Boolean = false,
        var outputRenamingMapFormat: OutputRenamingMapFormat = OutputRenamingMapFormat.JSON,
        var compileConstants: Map<String, Int> = HashMap(),
        var sourceMapLevel: SourceMapDetailLevel = SourceMapDetailLevel.DEFAULT
) : MinifierOptions()
