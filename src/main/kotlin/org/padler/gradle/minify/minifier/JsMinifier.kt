package org.padler.gradle.minify.minifier;

import com.google.common.collect.ImmutableList;
import com.google.javascript.jscomp.*;
import org.padler.gradle.minify.minifier.options.JSMinifierOptions;
import org.padler.gradle.minify.minifier.result.Error;
import org.padler.gradle.minify.minifier.result.Warning;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class JsMinifier extends Minifier {

    protected JSMinifierOptions minifierOptions = new JSMinifierOptions();

    private CompilerOptions options = new CompilerOptions();

    @Override
    @SuppressWarnings("java:S2259") // suppresses false positive at sourcemapFile.getName()
    protected void minifyFile(File srcFile, File dstFile) throws IOException {
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler();
        setOptions();

        List<SourceFile> externs = AbstractCommandLineRunner.getBuiltinExterns(new CompilerOptions().getEnvironment());
        SourceFile sourceFile = SourceFile.fromFile(srcFile.getAbsolutePath());

        File sourcemapFile = null;
        if (Boolean.TRUE.equals(minifierOptions.getCreateSoureMaps())) {
            sourcemapFile = new File(dstFile.getAbsolutePath() + ".map");
            options.setSourceMapOutputPath(sourcemapFile.getAbsolutePath());
            options.setSourceMapLocationMappings(Collections.singletonList(new RelativePathLocationMapping()));
        }

        Result result = compiler.compile(externs, ImmutableList.of(sourceFile), options);

        if (result.success) {
            String source = compiler.toSource();

            if (Boolean.TRUE.equals(minifierOptions.getCreateSoureMaps())) {
                StringBuilder sourceMapContent = new StringBuilder();
                result.sourceMap.appendTo(sourceMapContent, dstFile.getName());
                writeToFile(sourcemapFile, sourceMapContent.toString());

                source += "\n//# sourceMappingURL=" + sourcemapFile.getName();
            }
            writeToFile(dstFile, source);
        } else {
            for (JSError error : result.errors) {
                report.add(new Error(error));
            }
            for (JSError warning : result.warnings) {
                report.add(new Warning(warning));
            }
        }

    }

    private void setOptions() {
        minifierOptions.getCompilationLevel().setOptionsForCompilationLevel(options);
        options.setEnvironment(minifierOptions.getEnv());
        if (minifierOptions.getLanguageIn() != null)
            options.setLanguageIn(minifierOptions.getLanguageIn());
        if (minifierOptions.getLanguageOut() != null)
            options.setLanguageOut(minifierOptions.getLanguageOut());

        minifierOptions.getWarningLevel().setOptionsForWarningLevel(options);
        options.setExtraAnnotationNames(minifierOptions.getExtraAnnotationNames());
        options.setStrictModeInput(minifierOptions.getStrictModeInput());

        if (Boolean.TRUE.equals(minifierOptions.getDebug()))
            minifierOptions.getCompilationLevel().setDebugOptionsForCompilationLevel(options);

        options.setExportLocalPropertyDefinitions(minifierOptions.getExportLocalPropertyDefinitions());

        for (CommandLineRunner.FormattingOption formattingOption : minifierOptions.getFormatting()) {
            switch (formattingOption) {
                case PRETTY_PRINT:
                    options.setPrettyPrint(true);
                    break;
                case PRINT_INPUT_DELIMITER:
                    options.printInputDelimiter = true;
                    break;
                case SINGLE_QUOTES:
                    options.setPreferSingleQuotes(true);
                    break;
                default:
                    throw new IllegalStateException("Unknown formatting option: " + this);
            }
        }

        options.setGenerateExports(minifierOptions.getGenerateExports());
        options.setRenamePrefixNamespace(minifierOptions.getRenamePrefixNamespace());
        options.setRenamePrefix(minifierOptions.getRenameVariablePrefix());
        options.setModuleResolutionMode(minifierOptions.getModuleResolution());
        options.setProcessCommonJSModules(minifierOptions.getProcessCommonJsModules());
        options.setPackageJsonEntryNames(minifierOptions.getPackageJsonEntryNames());
        options.setAngularPass(minifierOptions.getAngularPass());
        options.setDartPass(minifierOptions.getDartPass());
        options.setForceLibraryInjection(minifierOptions.getForceInjectLibrary());
        options.setPolymerVersion(minifierOptions.getPolymerVersion());
        options.setRewritePolyfills(minifierOptions.getRewritePolyfills());
        options.setOutputCharset(minifierOptions.getCharset());
        options.setChecksOnly(minifierOptions.getChecksOnly());
        if (minifierOptions.getBrowserFeaturesetYear() != null)
            options.setBrowserFeaturesetYear(minifierOptions.getBrowserFeaturesetYear());
    }

    @Override
    protected boolean fileTypeMatches(Path f) {
        return "js".equals(getExtension(f.toString()));
    }

    @Override
    public JSMinifierOptions getMinifierOptions() {
        return minifierOptions;
    }

    @Override
    protected String getMinifierName() {
        return "JS Minifier";
    }

    @Override
    protected String rename(String oldName) {
        return oldName.replace(".js", ".min.js");
    }
}
