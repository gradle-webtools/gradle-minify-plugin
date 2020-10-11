package org.padler.gradle.minify.minifier.options

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
import java.nio.charset.Charset
import java.util.*

object CharsetSerializer : KSerializer<Charset> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Charset", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Charset) = encoder.encodeString(value.name())
    override fun deserialize(decoder: Decoder) = charset(decoder.decodeString())
}

@Serializable
data class JSMinifierOptions(
        var compilationLevel: CompilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS,
        var env: CompilerOptions.Environment = CompilerOptions.Environment.BROWSER,
        var languageIn: CompilerOptions.LanguageMode? = null,
        var languageOut: CompilerOptions.LanguageMode? = null,
        var warningLevel: WarningLevel = WarningLevel.QUIET,
        var extraAnnotationNames: List<String> = ArrayList(),
        var strictModeInput: Boolean = false,
        var debug: Boolean = false,
        var exportLocalPropertyDefinitions: Boolean = false,
        var formatting: List<CommandLineRunner.FormattingOption> = ArrayList(),
        var generateExports: Boolean = false,
        var renamePrefixNamespace: String? = null,
        var renameVariablePrefix: String? = null,
        var moduleResolution: ModuleLoader.ResolutionMode = ModuleLoader.ResolutionMode.BROWSER,
        var processCommonJsModules: Boolean = false,
        var packageJsonEntryNames: List<String> = ArrayList(),
        var angularPass: Boolean = false,
        var dartPass: Boolean = false,
        var forceInjectLibrary: List<String> = ArrayList(),
        var polymerVersion: Int? = null,
        var rewritePolyfills: Boolean = false,
        @Serializable(with = CharsetSerializer::class)
        var charset: Charset = Charsets.UTF_8,
        var checksOnly: Boolean = false,
        var browserFeaturesetYear: Int? = null
) : MinifierOptions()