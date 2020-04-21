package org.padler.gradle.minify.minifier;

import com.google.common.css.*;
import com.google.common.css.compiler.ast.BasicErrorManager;
import com.google.common.css.compiler.ast.ErrorManager;
import com.google.common.css.compiler.ast.GssError;
import com.google.common.css.compiler.commandline.DefaultCommandLineCompiler;
import com.google.common.css.compiler.gssfunctions.DefaultGssFunctionMapProvider;
import org.padler.gradle.minify.minifier.options.CSSMinifierOptions;
import org.padler.gradle.minify.minifier.result.Error;
import org.padler.gradle.minify.minifier.result.Warning;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Uses closure stylesheets.
 * Implemented with help from https://github.com/marcodelpercio https://github.com/google/closure-stylesheets/issues/101
 */
public class CssMinifier extends Minifier {

    protected CSSMinifierOptions minifierOptions = new CSSMinifierOptions();

    @Override
    protected void minifyFile(File srcFile, File dstFile) {
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
    protected boolean fileTypeMatches(Path f) {
        return "css".equals(getExtension(f.toString()));
    }

    @Override
    public CSSMinifierOptions getMinifierOptions() {
        return minifierOptions;
    }

    @Override
    protected String getMinifierName() {
        return "CSS Minifier";
    }

    @Override
    protected String rename(String oldName) {
        return oldName.replace(".css", ".min.css");
    }

    private JobDescription createJobDescription(File file) throws IOException {
        JobDescriptionBuilder builder = new JobDescriptionBuilder();
        builder.setInputOrientation(minifierOptions.getInputOrientation());
        builder.setOutputOrientation(minifierOptions.getOutputOrientation());
        builder.setOutputFormat(minifierOptions.getOutputFormat());
        builder.setCopyrightNotice(minifierOptions.getCopyrightNotice());
        builder.setTrueConditionNames(minifierOptions.getTrueConditionNames());
        builder.setAllowDefPropagation(minifierOptions.getAllowDefPropagation());
        builder.setAllowUnrecognizedFunctions(minifierOptions.getAllowUnrecognizedFunctions());
        builder.setAllowedNonStandardFunctions(minifierOptions.getAllowedNonStandardFunctions());
        builder.setAllowedUnrecognizedProperties(minifierOptions.getAllowedUnrecognizedProperties());
        builder.setAllowUnrecognizedProperties(minifierOptions.getAllowUnrecognizedProperties());
        builder.setVendor(minifierOptions.getVendor());
        builder.setAllowKeyframes(minifierOptions.getAllowKeyframes());
        builder.setAllowWebkitKeyframes(minifierOptions.getAllowWebkitKeyframes());
        builder.setProcessDependencies(minifierOptions.getProcessDependencies());
        builder.setExcludedClassesFromRenaming(minifierOptions.getExcludedClassesFromRenaming());
        builder.setSimplifyCss(minifierOptions.getSimplifyCss());
        builder.setEliminateDeadStyles(minifierOptions.getEliminateDeadStyles());
        builder.setCssSubstitutionMapProvider(IdentitySubstitutionMap::new);
        builder.setCssRenamingPrefix(minifierOptions.getCssRenamingPrefix());
        builder.setPreserveComments(minifierOptions.getPreserveComments());
        builder.setOutputRenamingMapFormat(minifierOptions.getOutputRenamingMapFormat());
        builder.setCompileConstants(minifierOptions.getCompileConstants());
        GssFunctionMapProvider gssFunMapProv = new DefaultGssFunctionMapProvider();
        builder.setGssFunctionMapProvider(gssFunMapProv);
        builder.setSourceMapLevel(minifierOptions.getSourceMapLevel());
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
