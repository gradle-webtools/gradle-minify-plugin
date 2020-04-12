package org.padler.gradle.minify.minifier;

import com.google.common.collect.Lists;
import com.google.common.css.*;
import com.google.common.css.compiler.ast.BasicErrorManager;
import com.google.common.css.compiler.ast.ErrorManager;
import com.google.common.css.compiler.ast.GssError;
import com.google.common.css.compiler.commandline.DefaultCommandLineCompiler;
import com.google.common.css.compiler.gssfunctions.DefaultGssFunctionMapProvider;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Uses closure stylesheets.
 * Implemented with help from https://github.com/marcodelpercio https://github.com/google/closure-stylesheets/issues/101
 */
public class CssMinifier extends Minifier {

    @Override
    protected void minifyFile(File srcFile, File dstFile, File baseDir) {
        try {
            JobDescription job = createJobDescription(srcFile);
            ExitCodeHandler exitCodeHandler = new DefaultExitCodeHandler();
            CompilerErrorManager errorManager = new CompilerErrorManager();
            ClosureStylesheetCompiler compiler = new ClosureStylesheetCompiler(job, exitCodeHandler, errorManager);

            File sourcemapFile = null;
            if (Boolean.TRUE.equals(minifierOptions.getCreateSoureMaps())) {
                sourcemapFile = new File(dstFile.getAbsolutePath() + ".map");
            }

            String compilerOutput = compiler.execute(null, sourcemapFile);
            writeToFile(dstFile, compilerOutput);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected String rename(String oldName) {
        return oldName.replace(".css", ".min.css");
    }

    @Override
    protected String getMinifierName() {
        return "CSS Minifier";
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
        builder.setCreateSourceMap(minifierOptions.getCreateSoureMaps());

        String fileContents = new String(Files.readAllBytes(file.toPath()));
        builder.addInput(new SourceCode(file.getName(), fileContents));

        return builder.getJobDescription();
    }

    final class CompilerErrorManager extends BasicErrorManager {
        @Override
        public void print(String msg) {
            // Do nothing to have all errors at the end
        }

        @Override
        public void report(GssError error) {
            report.add(new Error(error));
        }

        @Override
        public void reportWarning(GssError warning) {
            report.add(new Warning(warning));
        }
    }

    public class ClosureStylesheetCompiler extends DefaultCommandLineCompiler {

        public ClosureStylesheetCompiler(JobDescription job, ExitCodeHandler exitCodeHandler, ErrorManager errorManager) {
            super(job, exitCodeHandler, errorManager);
        }

        @Override
        public String execute(@Nullable File renameFile, @Nullable File sourcemapFile) {
            return super.execute(renameFile, sourcemapFile);
        }
    }
}
