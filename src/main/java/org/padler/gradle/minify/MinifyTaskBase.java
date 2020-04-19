package org.padler.gradle.minify;

import com.google.common.css.JobDescription;
import com.google.common.css.OutputRenamingMapFormat;
import com.google.common.css.Vendor;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MinifyTaskBase extends DefaultTask {

    protected MinifyPluginExtension extension = getProject().getExtensions().findByType(MinifyPluginExtension.class);

    public MinifyTaskBase() {
        if (extension == null) {
            extension = new MinifyPluginExtension();
        }
    }

    @Optional
    @InputDirectory
    public File getJsSrcDir() {
        if (extension.getJsSrcDir().isEmpty()) return null;
        return new File(extension.getJsSrcDir());
    }

    @Optional
    @InputDirectory
    public File getCssSrcDir() {
        if (extension.getCssSrcDir().isEmpty()) return null;
        return new File(extension.getCssSrcDir());
    }

    @Optional
    @OutputDirectory
    public File getJsDstDir() {
        if (extension.getJsDstDir().isEmpty()) return null;
        return new File(extension.getJsDstDir());
    }

    @Optional
    @OutputDirectory
    public File getCssDstDir() {
        if (extension.getCssDstDir().isEmpty()) return null;
        return new File(extension.getCssDstDir());
    }

    @Optional
    @Input
    public Boolean getCreateCssSourceMaps() {
        return extension.getCreateCssSourceMaps();
    }

    @Optional
    @Input
    public Boolean getCreateJsSourceMaps() {
        return extension.getCreateJsSourceMaps();
    }

    @Optional
    @Input
    public Boolean getOriginalFileNames() {
        return extension.getOriginalFileNames();
    }

    // CSS
    @Optional
    @Input
    public JobDescription.InputOrientation getInputOrientation() {
        return extension.getCss().getInputOrientation();
    }

    @Optional
    @Input
    public JobDescription.OutputOrientation getOutputOrientation() {
        return extension.getCss().getOutputOrientation();
    }

    @Optional
    @Input
    public JobDescription.OutputFormat getOutputFormat() {
        return extension.getCss().getOutputFormat();
    }

    @Optional
    @Input
    public String getCopyrightNotice() {
        return extension.getCss().getCopyrightNotice();
    }

    @Optional
    @Input
    public List<String> getTrueConditionNames() {
        return extension.getCss().getTrueConditionNames();
    }

    @Optional
    @Input
    public Boolean getAllowDefPropagation() {
        return extension.getCss().getAllowDefPropagation();
    }

    @Optional
    @Input
    public Boolean getAllowUnrecognizedFunctions() {
        return extension.getCss().getAllowUnrecognizedFunctions();
    }

    @Optional
    @Input
    public List<String> getAllowedNonStandardFunctions() {
        return extension.getCss().getAllowedNonStandardFunctions();
    }

    @Optional
    @Input
    public List<String> getAllowedUnrecognizedProperties() {
        return extension.getCss().getAllowedUnrecognizedProperties();
    }

    @Optional
    @Input
    public Boolean getAllowUnrecognizedProperties() {
        return extension.getCss().getAllowUnrecognizedProperties();
    }

    @Optional
    @Input
    public Vendor getVendor() {
        return extension.getCss().getVendor();
    }

    @Optional
    @Input
    public Boolean getAllowKeyframes() {
        return extension.getCss().getAllowKeyframes();
    }

    @Optional
    @Input
    public Boolean getAllowWebkitKeyframes() {
        return extension.getCss().getAllowWebkitKeyframes();
    }

    @Optional
    @Input
    public Boolean getProcessDependencies() {
        return extension.getCss().getProcessDependencies();
    }

    @Optional
    @Input
    public List<String> getExcludedClassesFromRenaming() {
        return extension.getCss().getExcludedClassesFromRenaming();
    }

    @Optional
    @Input
    public Boolean getSimplifyCss() {
        return extension.getCss().getSimplifyCss();
    }

    @Optional
    @Input
    public Boolean getEliminateDeadStyles() {
        return extension.getCss().getEliminateDeadStyles();
    }

    @Optional
    @Input
    public String getCssRenamingPrefix() {
        return extension.getCss().getCssRenamingPrefix();
    }

    @Optional
    @Input
    public Boolean getPreserveComments() {
        return extension.getCss().getPreserveComments();
    }

    @Optional
    @Input
    public OutputRenamingMapFormat getOutputRenamingMapFormat() {
        return extension.getCss().getOutputRenamingMapFormat();
    }

    @Optional
    @Input
    public Map<String, Integer> getCompileConstants() {
        return extension.getCss().getCompileConstants();
    }

    @Optional
    @Input
    public JobDescription.SourceMapDetailLevel getSourceMapLevel() {
        return extension.getCss().getSourceMapLevel();
    }
}
