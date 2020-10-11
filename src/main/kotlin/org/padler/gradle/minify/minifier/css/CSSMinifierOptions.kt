package org.padler.gradle.minify.minifier.css

import com.google.common.collect.Lists
import com.google.common.css.JobDescription.*
import com.google.common.css.OutputRenamingMapFormat
import com.google.common.css.Vendor
import kotlinx.serialization.Serializable
import org.padler.gradle.minify.minifier.MinifierOptions
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
) : MinifierOptions() {

    constructor(
            inputOrientation: InputOrientation = InputOrientation.LTR,
            outputOrientation: OutputOrientation = OutputOrientation.LTR,
            outputFormat: OutputFormat = OutputFormat.COMPRESSED,
            copyrightNotice: String? = null,
            trueConditionNames: List<String> = Lists.newArrayList(),
            allowDefPropagation: Boolean = true,
            allowUnrecognizedFunctions: Boolean = true,
            allowedNonStandardFunctions: List<String> = Lists.newArrayList(),
            allowedUnrecognizedProperties: List<String> = Lists.newArrayList(),
            allowUnrecognizedProperties: Boolean = true,
            vendor: Vendor? = null,
            allowKeyframes: Boolean = true,
            allowWebkitKeyframes: Boolean = true,
            processDependencies: Boolean = true,
            excludedClassesFromRenaming: List<String> = Lists.newArrayList(),
            simplifyCss: Boolean = true,
            eliminateDeadStyles: Boolean = false,
            cssRenamingPrefix: String = "",
            preserveComments: Boolean = false,
            outputRenamingMapFormat: OutputRenamingMapFormat = OutputRenamingMapFormat.JSON,
            compileConstants: Map<String, Int> = HashMap(),
            sourceMapLevel: SourceMapDetailLevel = SourceMapDetailLevel.DEFAULT,
            createSourceMaps: Boolean? = null,
            originalFileNames: Boolean? = null
    ) : this(
            inputOrientation,
            outputOrientation,
            outputFormat,
            copyrightNotice,
            trueConditionNames,
            allowDefPropagation,
            allowUnrecognizedFunctions,
            allowedNonStandardFunctions,
            allowedUnrecognizedProperties,
            allowUnrecognizedProperties,
            vendor,
            allowKeyframes,
            allowWebkitKeyframes,
            processDependencies,
            excludedClassesFromRenaming,
            simplifyCss,
            eliminateDeadStyles,
            cssRenamingPrefix,
            preserveComments,
            outputRenamingMapFormat,
            compileConstants,
            sourceMapLevel
    ) {
        if (createSourceMaps != null) this.createSourceMaps = createSourceMaps
        if (originalFileNames != null) this.originalFileNames = originalFileNames
    }
}
