package org.padler.gradle.minify.minifier;

import com.google.common.collect.ImmutableList;
import com.google.javascript.jscomp.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS;

public class JsMinifier extends Minifier {

    private CompilerOptions options = new CompilerOptions();

    public JsMinifier() {
        CompilationLevel.valueOf(SIMPLE_OPTIMIZATIONS.name()).setOptionsForCompilationLevel(options);
        WarningLevel.QUIET.setOptionsForWarningLevel(options);
    }

    @Override
    protected void minifyFile(File srcFile, File dstFile) throws IOException {
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler();

        List<SourceFile> externs = AbstractCommandLineRunner.getBuiltinExterns(new CompilerOptions().getEnvironment());
        SourceFile sourceFile = SourceFile.fromFile(srcFile.getAbsolutePath());

        File sourcemapFile = null;
        if (Boolean.TRUE.equals(minifierOptions.getCreateSoureMaps())) {
            sourcemapFile = new File(dstFile.getAbsolutePath() + ".map");
            options.setSourceMapOutputPath(sourcemapFile.getAbsolutePath());
            options.setSourceMapLocationMappings(Collections.singletonList(new LocationMapping()));
        }

        Result result = compiler.compile(externs, ImmutableList.of(sourceFile), options);

        if (result.success) {
            writeToFile(dstFile, compiler.toSource());

            if (Boolean.TRUE.equals(minifierOptions.getCreateSoureMaps())) {
                StringBuilder sourceMapContent = new StringBuilder();
                result.sourceMap.appendTo(sourceMapContent, dstFile.getName());
                writeToFile(sourcemapFile, sourceMapContent.toString());
            }
        } else {
            for (JSError error : result.errors) {
                report.add(new Error(error));
            }
            for (JSError warning : result.warnings) {
                report.add(new Warning(warning));
            }
        }

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
