package org.padler.gradle.minify.minifier.result

import lombok.Getter
import java.util.*

@Getter
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
