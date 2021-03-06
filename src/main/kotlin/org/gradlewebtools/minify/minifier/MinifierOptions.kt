package org.gradlewebtools.minify.minifier

import kotlinx.serialization.Serializable

@Serializable
open class MinifierOptions(
        open var ignoreMinFiles: Boolean = false,
        open var createSourceMaps: Boolean = false,
        open var originalFileNames: Boolean = false,
        open var copyOriginalFile: Boolean = false
)
