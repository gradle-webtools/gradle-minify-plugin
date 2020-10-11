package org.padler.gradle.minify.minifier.options

import kotlinx.serialization.Serializable

@Serializable
open class MinifierOptions(
        open var createSourceMaps: Boolean = false,
        open var originalFileNames: Boolean = false,
        open var copyOriginalFile: Boolean = false
)
