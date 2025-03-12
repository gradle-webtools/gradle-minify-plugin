package org.gradlewebtools.minify.minifier.result

import com.google.javascript.jscomp.JSError

class Warning : Event {
    constructor(error: JSError) : super(error)
}
