package org.padler.gradle.minify.minifier.options;

import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.WarningLevel;
import com.google.javascript.jscomp.deps.ModuleLoader;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS;
import static com.google.javascript.jscomp.CompilerOptions.Environment.BROWSER;
import static com.google.javascript.jscomp.WarningLevel.QUIET;

@Getter
@Setter
public class JSMinifierOptions extends MinifierOptions {
    private CompilationLevel compilationLevel = SIMPLE_OPTIMIZATIONS;
    private CompilerOptions.Environment env = BROWSER;
    private CompilerOptions.LanguageMode languageIn = null;
    private CompilerOptions.LanguageMode languageOut = null;
    private WarningLevel warningLevel = QUIET;
    private List<String> extraAnnotationNames = new ArrayList<>();
    private Boolean strictModeInput = false;
    private Boolean debug = false;
    private Boolean exportLocalPropertyDefinitions = false;
    private List<CommandLineRunner.FormattingOption> formatting = new ArrayList<>();
    private Boolean generateExports = false;
    private String renamePrefixNamespace = null;
    private String renameVariablePrefix = null;
    private ModuleLoader.ResolutionMode moduleResolution = ModuleLoader.ResolutionMode.BROWSER;
    private Boolean processCommonJsModules = false;
    private List<String> packageJsonEntryNames = new ArrayList<>();
    private Boolean angularPass = false;
    private Boolean dartPass = false;
    private List<String> forceInjectLibrary = new ArrayList<>();
    private Integer polymerVersion = null;
    private Boolean rewritePolyfills = false;
    private Charset charset = StandardCharsets.UTF_8;
    private Boolean checksOnly = false;
    private Integer browserFeaturesetYear = null;
}
