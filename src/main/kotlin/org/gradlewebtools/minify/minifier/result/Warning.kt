package org.gradlewebtools.minify.minifier.result

import com.google.common.css.compiler.ast.GssError
import com.google.javascript.jscomp.JSError

class Warning : Event {
    constructor(error: GssError) : super(error)
    constructor(error: JSError) : super(error)
}
