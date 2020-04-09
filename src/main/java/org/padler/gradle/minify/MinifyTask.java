package org.padler.gradle.minify;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.padler.gradle.minify.minifier.CssMinifier;
import org.padler.gradle.minify.minifier.JsMinifier;
import org.padler.gradle.minify.minifier.Minifier;

import java.io.File;

public class MinifyTask extends DefaultTask {

    MinifyPluginExtension extension = getProject().getExtensions().findByType(MinifyPluginExtension.class);

    public MinifyTask() {
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

    @TaskAction
    public void minify() {
        if (!extension.getCssSrcDir().isEmpty() && !extension.getCssDstDir().isEmpty()) {
            Minifier cssMinifier = new CssMinifier();
            cssMinifier.minify(extension.getCssSrcDir(), extension.getCssDstDir());
        }
        if (!extension.getJsSrcDir().isEmpty() && !extension.getJsDstDir().isEmpty()) {
            Minifier jsMinifier = new JsMinifier();
            jsMinifier.minify(extension.getJsSrcDir(), extension.getJsDstDir());
        }
    }
}
