package org.gradlewebtools.minify.minifier.css

import kotlinx.serialization.Serializable
import org.gradlewebtools.minify.minifier.MinifierOptions

@Serializable
data class CssMinifierOptions(
        var dummy: String? = null
) : MinifierOptions() {
    constructor(
            ignoreMinFiles: Boolean? = null,
            dummy: String? = null
    ) : this(
            dummy
    ) {
        if (ignoreMinFiles != null) this.ignoreMinFiles = ignoreMinFiles
        if (createSourceMaps != null) this.createSourceMaps = createSourceMaps
        if (originalFileNames != null) this.originalFileNames = originalFileNames
        if (copyOriginalFile != null) this.copyOriginalFile = copyOriginalFile
    }
}
