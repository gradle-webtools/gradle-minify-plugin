package org.padler.gradle.minify.minifier;

import com.google.common.collect.ImmutableList;
import com.google.javascript.jscomp.*;
import org.gradle.api.GradleException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS;

public class JsMinifier extends Minifier {

    CompilerOptions options = new CompilerOptions();

    public JsMinifier() {
        CompilationLevel.valueOf(SIMPLE_OPTIMIZATIONS.name()).setOptionsForCompilationLevel(options);
        WarningLevel.QUIET.setOptionsForWarningLevel(options);
    }

    @Override
    protected void minifyFile(File srcFile, File dstFile) throws IOException {
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler();

        try (OutputStreamWriter iosW = new OutputStreamWriter(new FileOutputStream(dstFile))) {

            List<SourceFile> externs = AbstractCommandLineRunner.getBuiltinExterns(new CompilerOptions().getEnvironment());
            SourceFile sourceFile = SourceFile.fromFile(srcFile.getAbsolutePath());
            Result result = compiler.compile(externs, ImmutableList.of(sourceFile), options);

            if (result.success) {
                iosW.write(compiler.toSource());
            } else {
                StringBuilder error = new StringBuilder();
                for (JSError jsError : result.errors) {
                    error.append(jsError.getSourceName()).append(":").append(jsError.getLineno()).append(" - ").append(jsError.getDescription());
                }
                throw new GradleException(error.toString());
            }
            iosW.flush();
        }
    }
}
