package org.padler.gradle.minify.minifier;

import com.google.common.collect.Lists;
import com.google.common.css.*;
import com.google.common.css.compiler.ast.BasicErrorManager;
import com.google.common.css.compiler.ast.ErrorManager;
import com.google.common.css.compiler.ast.GssError;
import com.google.common.css.compiler.commandline.DefaultCommandLineCompiler;
import com.google.common.css.compiler.gssfunctions.DefaultGssFunctionMapProvider;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 * Uses closure stylesheets.
 * Implemented with help from https://github.com/marcodelpercio https://github.com/google/closure-stylesheets/issues/101
 */
public class CssMinifier extends Minifier {

    @Override
    protected void minifyFile(File srcFile, File dstFile) {
        try {
            JobDescription job = createJobDescription(srcFile);
            ExitCodeHandler exitCodeHandler = new DefaultExitCodeHandler();
            CompilerErrorManager errorManager = new CompilerErrorManager();
            ClosureStylesheetCompiler compiler = new ClosureStylesheetCompiler(job, exitCodeHandler, errorManager);
            String compilerOutput = compiler.execute();
            OpenOption create = StandardOpenOption.CREATE;
            OpenOption write = StandardOpenOption.WRITE;
            OpenOption truncateExisting = StandardOpenOption.TRUNCATE_EXISTING;
            Files.write(dstFile.toPath(), compilerOutput.getBytes(), create, write, truncateExisting);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private JobDescription createJobDescription(File file) throws IOException {
        JobDescriptionBuilder builder = new JobDescriptionBuilder();
        builder.setInputOrientation(JobDescription.InputOrientation.LTR);
        builder.setOutputOrientation(JobDescription.OutputOrientation.LTR);
        builder.setOutputFormat(JobDescription.OutputFormat.COMPRESSED);
        builder.setCopyrightNotice(null);
        builder.setTrueConditionNames(Lists.newArrayList());
        builder.setAllowDefPropagation(true);
        builder.setAllowUnrecognizedFunctions(true);
        builder.setAllowedNonStandardFunctions(Lists.newArrayList());
        builder.setAllowedUnrecognizedProperties(Lists.newArrayList());
        builder.setAllowUnrecognizedProperties(true);
        builder.setVendor(null);
        builder.setAllowKeyframes(true);
        builder.setAllowWebkitKeyframes(true);
        builder.setProcessDependencies(true);
        builder.setExcludedClassesFromRenaming(Lists.newArrayList());
        builder.setSimplifyCss(true);
        /* sadly the following line is necessary until they introduce support for --allow-duplicate-declarations  */
        builder.setEliminateDeadStyles(false);
        builder.setCssSubstitutionMapProvider(IdentitySubstitutionMap::new);
        builder.setCssRenamingPrefix("");
        builder.setPreserveComments(false);
        builder.setOutputRenamingMapFormat(OutputRenamingMapFormat.JSON);
        builder.setCompileConstants(new HashMap<>());
        GssFunctionMapProvider gssFunMapProv = new DefaultGssFunctionMapProvider();
        builder.setGssFunctionMapProvider(gssFunMapProv);
        builder.setSourceMapLevel(JobDescription.SourceMapDetailLevel.DEFAULT);
        builder.setCreateSourceMap(false);

        String fileContents = new String(Files.readAllBytes(file.toPath()));
        builder.addInput(new SourceCode(file.getName(), fileContents));

        return builder.getJobDescription();
    }

    final class CompilerErrorManager extends BasicErrorManager {
        private boolean warningsAsErrors = false;

        @Override
        public void print(String msg) {
            System.err.println(msg);
        }

        @Override
        public void reportWarning(GssError warning) {
            if (warningsAsErrors) {
                report(warning);
            } else {
                super.reportWarning(warning);
            }
        }

        public void setWarningsAsErrors(boolean state) {
            warningsAsErrors = state;
        }
    }

    public class ClosureStylesheetCompiler extends DefaultCommandLineCompiler {

        public ClosureStylesheetCompiler(JobDescription job, ExitCodeHandler exitCodeHandler, ErrorManager errorManager) {
            super(job, exitCodeHandler, errorManager);
        }

        public String execute() {
            return super.execute(null, null);
        }
    }
}
