package org.gradlewebtools.minify.minifier.js

import com.google.javascript.jscomp.*
import org.gradlewebtools.minify.minifier.Minifier
import org.gradlewebtools.minify.minifier.result.Error
import org.gradlewebtools.minify.minifier.result.Warning
import java.io.File
import java.util.*

class JsMinifier(override var minifierOptions: JsMinifierOptions = JsMinifierOptions()) : Minifier() {

    override val minifierName = "Js Minifier"

    override val acceptedFileExtensions: List<String> = listOf("js")

    private val options = CompilerOptions()

    override fun minifyFile(srcFile: File, dstFile: File) {
        val compiler = Compiler()
        setOptions()
        val externals = AbstractCommandLineRunner.getBuiltinExterns(CompilerOptions().environment)
        val sourceFile = SourceFile.fromFile(srcFile.absolutePath)
        val sourcemapFile = File(dstFile.absolutePath + ".map")
        if (minifierOptions.createSourceMaps) {
            options.setSourceMapOutputPath(sourcemapFile.absolutePath)
            options.setSourceMapLocationMappings(listOf(SourceMap.LocationMapping {
                it.substringAfterLast("/").takeIf { it.isNotEmpty() }
            }))
        }
        val result = compiler.compile(externals, Collections.singletonList(sourceFile), options)
        if (result.success) {
            var source = compiler.toSource()
            if (minifierOptions.createSourceMaps) {
                writeToFile(sourcemapFile, buildString { result.sourceMap.appendTo(this, dstFile.name) })
                source += "\n//# sourceMappingURL=${sourcemapFile.name}"
            }
            writeToFile(dstFile, source!!)
        } else {
            result.errors.forEach {
                report.add(Error(it))
            }
            result.warnings.forEach {
                report.add(Warning(it))
            }
        }
    }

    private fun setOptions() {
        minifierOptions.compilationLevel.setOptionsForCompilationLevel(options)
        options.environment = minifierOptions.env
        if (minifierOptions.languageIn != null) options.languageIn = minifierOptions.languageIn
        if (minifierOptions.languageOut != null) options.setLanguageOut(minifierOptions.languageOut)
        minifierOptions.warningLevel.setOptionsForWarningLevel(options)
        options.setExtraAnnotationNames(minifierOptions.extraAnnotationNames)
        options.setStrictModeInput(minifierOptions.strictModeInput)
        options.setStrictModeInput(minifierOptions.strictModeInput)
        if (minifierOptions.debug) minifierOptions.compilationLevel
                .setDebugOptionsForCompilationLevel(options)
        options.setExportLocalPropertyDefinitions(minifierOptions.exportLocalPropertyDefinitions)
        minifierOptions.formatting.forEach {
            when (it) {
                CommandLineRunner.FormattingOption.PRETTY_PRINT -> options.isPrettyPrint = true
                CommandLineRunner.FormattingOption.PRINT_INPUT_DELIMITER -> options.printInputDelimiter = true
                CommandLineRunner.FormattingOption.SINGLE_QUOTES -> options.setPreferSingleQuotes(true)
                else -> throw IllegalStateException("Unknown formatting option: $this")
            }
        }
        options.setGenerateExports(minifierOptions.generateExports)
        options.setRenamePrefixNamespace(minifierOptions.renamePrefixNamespace)
        options.setRenamePrefix(minifierOptions.renameVariablePrefix)
        options.moduleResolutionMode = minifierOptions.moduleResolution
        options.processCommonJSModules = minifierOptions.processCommonJsModules
        options.packageJsonEntryNames = minifierOptions.packageJsonEntryNames
        options.setAngularPass(minifierOptions.angularPass)
        options.setForceLibraryInjection(minifierOptions.forceInjectLibrary)
        options.setPolymerVersion(minifierOptions.polymerVersion)
        options.rewritePolyfills = minifierOptions.rewritePolyfills
        options.setOutputCharset(minifierOptions.charset)
        options.setChecksOnly(minifierOptions.checksOnly)
        if (minifierOptions.browserFeaturesetYear != null) options.browserFeaturesetYear = minifierOptions.browserFeaturesetYear!!
        options.setEmitUseStrict(minifierOptions.emitUseStrict)
    }

    override fun rename(oldName: String): String {
        return oldName.replace(".js", ".min.js")
    }
}
