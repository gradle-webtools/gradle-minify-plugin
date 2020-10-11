package org.padler.gradle.minify.minifier.result

import com.google.common.css.compiler.ast.GssError
import com.google.javascript.jscomp.JSError

open class Event {

    private var charNo: Int
    private var lineNo: Int
    private var message: String
    private var sourceFileName: String

    constructor(error: GssError) {
        charNo = error.location.beginCharacterIndex
        lineNo = error.location.lineNumber
        message = error.message
        sourceFileName = error.location.sourceCode.fileName
    }

    constructor(error: JSError) {
        charNo = error.charno
        lineNo = error.lineNumber
        message = error.description
        sourceFileName = error.sourceName
    }

    override fun toString(): String {
        return "$message at $sourceFileName line $lineNo : $charNo"
    }
}
