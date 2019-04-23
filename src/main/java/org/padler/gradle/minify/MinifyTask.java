package org.padler.gradle.minify;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.padler.gradle.minify.minifier.CssMinifier;
import org.padler.gradle.minify.minifier.JsMinifier;
import org.padler.gradle.minify.minifier.Minifier;

public class MinifyTask extends DefaultTask {

    @TaskAction
    public void greet() {
        MinifyPluginExtension extension = getProject().getExtensions().findByType(MinifyPluginExtension.class);
        if (extension == null) {
            extension = new MinifyPluginExtension();
        }

        Minifier cssMinifier = new CssMinifier();
        Minifier jsMinifier = new JsMinifier();

        cssMinifier.minify(extension.getCssSrcDir(), extension.getCssDstDir());
        jsMinifier.minify(extension.getJsSrcDir(), extension.getJsDstDir());
    }

}
