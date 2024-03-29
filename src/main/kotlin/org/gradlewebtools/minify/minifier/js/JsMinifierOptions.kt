package org.gradlewebtools.minify.minifier.js

import com.google.javascript.jscomp.CommandLineRunner
import com.google.javascript.jscomp.CompilationLevel
import com.google.javascript.jscomp.CompilerOptions
import com.google.javascript.jscomp.WarningLevel
import com.google.javascript.jscomp.deps.ModuleLoader
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.gradlewebtools.minify.minifier.MinifierOptions
import java.nio.charset.Charset

internal object CharsetSerializer : KSerializer<Charset> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Charset", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Charset) = encoder.encodeString(value.name())
    override fun deserialize(decoder: Decoder) = charset(decoder.decodeString())
}

@Serializable
data class JsMinifierOptions(
        var compilationLevel: CompilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS,
        var env: CompilerOptions.Environment = CompilerOptions.Environment.BROWSER,
        var languageIn: CompilerOptions.LanguageMode? = null,
        var languageOut: CompilerOptions.LanguageMode? = null,
        var warningLevel: WarningLevel = WarningLevel.QUIET,
        var extraAnnotationNames: List<String> = listOf(),
        var strictModeInput: Boolean = false,
        var debug: Boolean = false,
        var exportLocalPropertyDefinitions: Boolean = false,
        var formatting: List<CommandLineRunner.FormattingOption> = listOf(),
        var generateExports: Boolean = false,
        var renamePrefixNamespace: String? = null,
        var renameVariablePrefix: String? = null,
        var moduleResolution: ModuleLoader.ResolutionMode = ModuleLoader.ResolutionMode.BROWSER,
        var processCommonJsModules: Boolean = false,
        var packageJsonEntryNames: List<String> = listOf(),
        var angularPass: Boolean = false,
        var forceInjectLibrary: List<String> = listOf(),
        var polymerVersion: Int? = null,
        var rewritePolyfills: Boolean = false,
        @Serializable(with = CharsetSerializer::class)
        var charset: Charset = Charsets.UTF_8,
        var checksOnly: Boolean = false,
        var browserFeaturesetYear: Int? = null,
        var emitUseStrict: Boolean = true
) : MinifierOptions() {

    constructor(
            ignoreMinFiles: Boolean? = null,
            compilationLevel: CompilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS,
            env: CompilerOptions.Environment = CompilerOptions.Environment.BROWSER,
            languageIn: CompilerOptions.LanguageMode? = null,
            languageOut: CompilerOptions.LanguageMode? = null,
            warningLevel: WarningLevel = WarningLevel.QUIET,
            extraAnnotationNames: List<String> = listOf(),
            strictModeInput: Boolean = false,
            debug: Boolean = false,
            exportLocalPropertyDefinitions: Boolean = false,
            formatting: List<CommandLineRunner.FormattingOption> = listOf(),
            generateExports: Boolean = false,
            renamePrefixNamespace: String? = null,
            renameVariablePrefix: String? = null,
            moduleResolution: ModuleLoader.ResolutionMode = ModuleLoader.ResolutionMode.BROWSER,
            processCommonJsModules: Boolean = false,
            packageJsonEntryNames: List<String> = listOf(),
            angularPass: Boolean = false,
            forceInjectLibrary: List<String> = listOf(),
            polymerVersion: Int? = null,
            rewritePolyfills: Boolean = false,
            charset: Charset = Charsets.UTF_8,
            checksOnly: Boolean = false,
            browserFeaturesetYear: Int? = null,
            createSourceMaps: Boolean? = null,
            originalFileNames: Boolean? = null,
            copyOriginalFile: Boolean? = null,
            emitUseStrict: Boolean = true
    ) : this(
            compilationLevel,
            env,
            languageIn,
            languageOut,
            warningLevel,
            extraAnnotationNames,
            strictModeInput,
            debug,
            exportLocalPropertyDefinitions,
            formatting,
            generateExports,
            renamePrefixNamespace,
            renameVariablePrefix,
            moduleResolution,
            processCommonJsModules,
            packageJsonEntryNames,
            angularPass,
            forceInjectLibrary,
            polymerVersion,
            rewritePolyfills,
            charset,
            checksOnly,
            browserFeaturesetYear,
            emitUseStrict
    ) {
        if (ignoreMinFiles != null) this.ignoreMinFiles = ignoreMinFiles
        if (createSourceMaps != null) this.createSourceMaps = createSourceMaps
        if (originalFileNames != null) this.originalFileNames = originalFileNames
        if (copyOriginalFile != null) this.copyOriginalFile = copyOriginalFile
    }
}
