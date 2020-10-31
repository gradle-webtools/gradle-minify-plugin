package org.gradlewebtools.minify.minifier.result

class Report {
    val errors: MutableList<Error> = mutableListOf()
    val warnings: MutableList<Warning> = mutableListOf()
    fun add(error: Error) {
        errors.add(error)
    }
    fun add(warning: Warning) {
        warnings.add(warning)
    }
}
