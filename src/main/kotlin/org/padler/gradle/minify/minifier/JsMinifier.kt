package org.padler.gradle.minify.minifier

import com.google.common.collect.ImmutableList
import com.google.javascript.jscomp.*
import org.padler.gradle.minify.minifier.options.JSMinifierOptions
import org.padler.gradle.minify.minifier.result.Error
import org.padler.gradle.minify.minifier.result.Warning
import java.io.File
import java.nio.file.Path

class JsMinifier(override var minifierOptions: JSMinifierOptions = JSMinifierOptions()) : Minifier() {

    private val options = CompilerOptions()

    override fun minifyFile(srcFile: File, dstFile: File) {
        val compiler = Compiler()
        setOptions()
        val externals = AbstractCommandLineRunner.getBuiltinExterns(CompilerOptions().environment)
        val sourceFile = SourceFile.fromFile(srcFile.absolutePath)
        var sourcemapFile: File? = null
        if (minifierOptions.createSourceMaps) {
            sourcemapFile = File(dstFile.absolutePath + ".map")
            options.setSourceMapOutputPath(sourcemapFile.absolutePath)
            options.setSourceMapLocationMappings(listOf(SourceMap.LocationMapping { location ->
                val index = location.lastIndexOf('/')
                if (index != -1) location.substring(index + 1) else null
            }))
        }
        val result = compiler.compile(externals, ImmutableList.of(sourceFile), options)
        if (result.success) {
            var source = compiler.toSource()
            if (minifierOptions.createSourceMaps) {
                val sourceMapContent = StringBuilder()
                result.sourceMap.appendTo(sourceMapContent, dstFile.name)
                writeToFile(sourcemapFile!!, sourceMapContent.toString())
                source += "\n//# sourceMappingURL=${sourcemapFile.name}"
            }
            writeToFile(dstFile, source!!)
        } else {
            for (error in result.errors) {
                report.add(Error(error))
            }
            for (warning in result.warnings) {
                report.add(Warning(warning))
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
        if (minifierOptions.debug) minifierOptions.compilationLevel
                .setDebugOptionsForCompilationLevel(options)
        options.setExportLocalPropertyDefinitions(minifierOptions.exportLocalPropertyDefinitions)
        for (formattingOption in minifierOptions.formatting) {
            when (formattingOption) {
                CommandLineRunner.FormattingOption.PRETTY_PRINT -> options.isPrettyPrint = true
                CommandLineRunner.FormattingOption.PRINT_INPUT_DELIMITER -> options.printInputDelimiter = true
                CommandLineRunner.FormattingOption.SINGLE_QUOTES -> options.setPreferSingleQuotes(true)
                else                                                     -> throw IllegalStateException("Unknown formatting option: $this")
            }
        }
        options.setGenerateExports(minifierOptions.generateExports)
        options.setRenamePrefixNamespace(minifierOptions.renamePrefixNamespace)
        options.setRenamePrefix(minifierOptions.renameVariablePrefix)
        options.moduleResolutionMode = minifierOptions.moduleResolution
        options.processCommonJSModules = minifierOptions.processCommonJsModules
        options.packageJsonEntryNames = minifierOptions.packageJsonEntryNames
        options.setAngularPass(minifierOptions.angularPass)
        options.setDartPass(minifierOptions.dartPass)
        options.setForceLibraryInjection(minifierOptions.forceInjectLibrary)
        options.setPolymerVersion(minifierOptions.polymerVersion)
        options.rewritePolyfills = minifierOptions.rewritePolyfills
        options.setOutputCharset(minifierOptions.charset)
        options.setChecksOnly(minifierOptions.checksOnly)
        if (minifierOptions.browserFeaturesetYear != null) options.browserFeaturesetYear = minifierOptions
                .browserFeaturesetYear
    }

    override fun fileTypeMatches(f: Path): Boolean {
        return "js" == getExtension(f.toString())
    }

    override val minifierName = "JS Minifier"

    override fun rename(oldName: String): String {
        return oldName.replace(".js", ".min.js")
    }
}
