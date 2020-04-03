package org.padler.gradle.minify.minifier;

import com.google.common.css.compiler.commandline.ClosureCommandLineCompiler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CssMinifier extends Minifier {

    @Override
    protected void minifyFile(File srcFile, File dstFile) {
        List<String> argList = new ArrayList<>();
        String input = srcFile.getAbsolutePath();
        String outputArg = "--output-file";
        String output = dstFile.getAbsolutePath();
        String flag1 = "--allow-unrecognized-functions";
        argList.add(input);
        argList.add(outputArg);
        argList.add(output);
        argList.add(flag1);

        String[] args = new String[argList.size()];
        args = argList.toArray(args);
        ClosureCommandLineCompiler.main(args);
    }
}
