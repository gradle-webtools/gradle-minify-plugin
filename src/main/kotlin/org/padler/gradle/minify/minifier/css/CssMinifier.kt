package org.padler.gradle.minify.minifier.css

import com.google.common.css.*
import com.google.common.css.compiler.ast.BasicErrorManager
import com.google.common.css.compiler.ast.ErrorManager
import com.google.common.css.compiler.ast.GssError
import com.google.common.css.compiler.commandline.DefaultCommandLineCompiler
import com.google.common.css.compiler.gssfunctions.DefaultGssFunctionMapProvider
import org.padler.gradle.minify.minifier.Minifier
import org.padler.gradle.minify.minifier.result.Error
import org.padler.gradle.minify.minifier.result.Warning
import java.io.File
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path

/**
 * Uses closure stylesheets.
 * Implemented with help from https://github.com/marcodelpercio https://github.com/google/closure-stylesheets/issues/101
 */
class CssMinifier(override var minifierOptions: CSSMinifierOptions = CSSMinifierOptions()) : Minifier() {

    override fun minifyFile(srcFile: File, dstFile: File) {
        try {
            val job = createJobDescription(srcFile)
            val exitCodeHandler = DefaultExitCodeHandler()
            val errorManager = CompilerErrorManager()
            val compiler = ClosureStylesheetCompiler(job, exitCodeHandler, errorManager)
            var sourcemapFile: File? = null
            if (minifierOptions.createSourceMaps) {
                sourcemapFile = File(dstFile.absolutePath + ".map")
            }
            var compilerOutput = compiler.execute(null, sourcemapFile)
            if (sourcemapFile != null) {
                compilerOutput += "\n//# sourceMappingURL=${sourcemapFile.name}"
            }
            writeToFile(dstFile, compilerOutput)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    override fun fileTypeMatches(f: Path): Boolean {
        return "css" == getExtension(f.toString())
    }

    override val minifierName = "CSS Minifier"

    override fun rename(oldName: String): String {
        return oldName.replace(".css", ".min.css")
    }

    @Throws(IOException::class)
    private fun createJobDescription(file: File): JobDescription {
        val builder = JobDescriptionBuilder()
        builder.setInputOrientation(minifierOptions.inputOrientation)
        builder.setOutputOrientation(minifierOptions.outputOrientation)
        builder.setOutputFormat(minifierOptions.outputFormat)
        builder.setCopyrightNotice(minifierOptions.copyrightNotice)
        builder.setTrueConditionNames(minifierOptions.trueConditionNames)
        builder.setAllowDefPropagation(minifierOptions.allowDefPropagation)
        builder.setAllowUnrecognizedFunctions(minifierOptions.allowUnrecognizedFunctions)
        builder.setAllowedNonStandardFunctions(minifierOptions.allowedNonStandardFunctions)
        builder.setAllowedUnrecognizedProperties(minifierOptions.allowedUnrecognizedProperties)
        builder.setAllowUnrecognizedProperties(minifierOptions.allowUnrecognizedProperties)
        builder.setVendor(minifierOptions.vendor)
        builder.setAllowKeyframes(minifierOptions.allowKeyframes)
        builder.setAllowWebkitKeyframes(minifierOptions.allowWebkitKeyframes)
        builder.setProcessDependencies(minifierOptions.processDependencies)
        builder.setExcludedClassesFromRenaming(minifierOptions.excludedClassesFromRenaming)
        builder.setSimplifyCss(minifierOptions.simplifyCss)
        builder.setEliminateDeadStyles(minifierOptions.eliminateDeadStyles)
        builder.setCssSubstitutionMapProvider { IdentitySubstitutionMap() }
        builder.setCssRenamingPrefix(minifierOptions.cssRenamingPrefix)
        builder.setPreserveComments(minifierOptions.preserveComments)
        builder.setOutputRenamingMapFormat(minifierOptions.outputRenamingMapFormat)
        builder.setCompileConstants(minifierOptions.compileConstants)
        builder.setGssFunctionMapProvider(DefaultGssFunctionMapProvider())
        builder.setSourceMapLevel(minifierOptions.sourceMapLevel)
        builder.setCreateSourceMap(minifierOptions.createSourceMaps)
        val fileContents = String(Files.readAllBytes(file.toPath()))
        builder.addInput(SourceCode(file.name, fileContents))
        return builder.jobDescription
    }

    internal inner class CompilerErrorManager : BasicErrorManager() {

        override fun print(msg: String) {
            // Do nothing to have all errors at the end
        }

        override fun report(error: GssError) {
            report.add(Error(error))
        }

        override fun reportWarning(warning: GssError) {
            report.add(Warning(warning))
        }
    }

    inner class ClosureStylesheetCompiler(
            job: JobDescription?,
            exitCodeHandler: ExitCodeHandler?,
            errorManager: ErrorManager?
    ) : DefaultCommandLineCompiler(job, exitCodeHandler, errorManager) {

        public override fun execute(renameFile: File?, sourcemapFile: File?): String {
            return super.execute(renameFile, sourcemapFile)
        }
    }
}
