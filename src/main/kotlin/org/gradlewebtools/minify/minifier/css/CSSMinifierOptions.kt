package org.gradlewebtools.minify.minifier.css

import com.google.common.css.JobDescription.*
import com.google.common.css.OutputRenamingMapFormat
import com.google.common.css.Vendor
import kotlinx.serialization.Serializable
import org.gradlewebtools.minify.minifier.MinifierOptions

@Serializable
data class CSSMinifierOptions(
        var inputOrientation: InputOrientation = InputOrientation.LTR,
        var outputOrientation: OutputOrientation = OutputOrientation.LTR,
        var outputFormat: OutputFormat = OutputFormat.COMPRESSED,
        var copyrightNotice: String? = null,
        var trueConditionNames: List<String> = listOf(),
        var allowDefPropagation: Boolean = true,
        var allowUnrecognizedFunctions: Boolean = true,
        var allowedNonStandardFunctions: List<String> = listOf(),
        var allowedUnrecognizedProperties: List<String> = listOf(),
        var allowUnrecognizedProperties: Boolean = true,
        var vendor: Vendor? = null,
        var allowKeyframes: Boolean = true,
        var allowWebkitKeyframes: Boolean = true,
        var processDependencies: Boolean = true,
        var excludedClassesFromRenaming: List<String> = listOf(),
        var simplifyCss: Boolean = true,
        var eliminateDeadStyles: Boolean = false,
        var cssRenamingPrefix: String = "",
        var preserveComments: Boolean = false,
        var outputRenamingMapFormat: OutputRenamingMapFormat = OutputRenamingMapFormat.JSON,
        var compileConstants: Map<String, Int> = mapOf(),
        var sourceMapLevel: SourceMapDetailLevel = SourceMapDetailLevel.DEFAULT
) : MinifierOptions() {

    constructor(
            inputOrientation: InputOrientation = InputOrientation.LTR,
            outputOrientation: OutputOrientation = OutputOrientation.LTR,
            outputFormat: OutputFormat = OutputFormat.COMPRESSED,
            copyrightNotice: String? = null,
            trueConditionNames: List<String> = listOf(),
            allowDefPropagation: Boolean = true,
            allowUnrecognizedFunctions: Boolean = true,
            allowedNonStandardFunctions: List<String> = listOf(),
            allowedUnrecognizedProperties: List<String> = listOf(),
            allowUnrecognizedProperties: Boolean = true,
            vendor: Vendor? = null,
            allowKeyframes: Boolean = true,
            allowWebkitKeyframes: Boolean = true,
            processDependencies: Boolean = true,
            excludedClassesFromRenaming: List<String> = listOf(),
            simplifyCss: Boolean = true,
            eliminateDeadStyles: Boolean = false,
            cssRenamingPrefix: String = "",
            preserveComments: Boolean = false,
            outputRenamingMapFormat: OutputRenamingMapFormat = OutputRenamingMapFormat.JSON,
            compileConstants: Map<String, Int> = mapOf(),
            sourceMapLevel: SourceMapDetailLevel = SourceMapDetailLevel.DEFAULT,
            createSourceMaps: Boolean? = null,
            originalFileNames: Boolean? = null,
            copyOriginalFile: Boolean? = null
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
        if (copyOriginalFile != null) this.copyOriginalFile = copyOriginalFile
    }
}
