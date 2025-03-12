package org.gradlewebtools.minify.minifier.result

import com.google.javascript.jscomp.JSError

class Error : Event {
    constructor(error: JSError) : super(error)
}
