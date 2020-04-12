package org.padler.gradle.minify;

import org.gradle.api.tasks.TaskAction;
import org.padler.gradle.minify.minifier.CssMinifier;
import org.padler.gradle.minify.minifier.JsMinifier;
import org.padler.gradle.minify.minifier.options.CSSMinifierOptions;
import org.padler.gradle.minify.minifier.options.JSMinifierOptions;

public class MinifyTask extends MinifyTaskBase {
    @TaskAction
    public void minify() {
        if (!extension.getCssSrcDir().isEmpty() && !extension.getCssDstDir().isEmpty()) {
            CssMinifier cssMinifier = new CssMinifier();
            CSSMinifierOptions minifierOptions = cssMinifier.getMinifierOptions();
            minifierOptions.setCreateSoureMaps(extension.getCreateCssSourceMaps());
            minifierOptions.setOriginalFileNames(extension.getOriginalFileNames());
            cssMinifier.minify(extension.getCssSrcDir(), extension.getCssDstDir());
        }
        if (!extension.getJsSrcDir().isEmpty() && !extension.getJsDstDir().isEmpty()) {
            JsMinifier jsMinifier = new JsMinifier();
            JSMinifierOptions minifierOptions = jsMinifier.getMinifierOptions();
            minifierOptions.setCreateSoureMaps(extension.getCreateJsSourceMaps());
            minifierOptions.setOriginalFileNames(extension.getOriginalFileNames());
            jsMinifier.minify(extension.getJsSrcDir(), extension.getJsDstDir());
        }
    }
}
