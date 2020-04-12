package org.padler.gradle.minify;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;

import java.io.File;

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
}
