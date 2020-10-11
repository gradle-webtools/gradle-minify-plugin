package org.padler.gradle.minify.minifier

import com.google.javascript.jscomp.SourceMap

class RelativePathLocationMapping : SourceMap.LocationMapping {

    override fun map(location: String): String? {
        val index = location.lastIndexOf('/')
        return if (index != -1) location.substring(index + 1) else null
    }
}
