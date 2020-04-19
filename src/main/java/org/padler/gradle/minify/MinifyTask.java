package org.padler.gradle.minify;

import org.gradle.api.tasks.TaskAction;
import org.padler.gradle.minify.minifier.CssMinifier;
import org.padler.gradle.minify.minifier.JsMinifier;
import org.padler.gradle.minify.minifier.Minifier;
import org.padler.gradle.minify.minifier.options.CSSMinifierOptions;
import org.padler.gradle.minify.minifier.options.JSMinifierOptions;

public class MinifyTask extends MinifyTaskBase {
    @TaskAction
    public void minify() {
        if (!extension.getCssSrcDir().isEmpty() && !extension.getCssDstDir().isEmpty()) {
            Minifier minifier = createCSSMinifier();
            String srcDir = extension.getCssSrcDir();
            String dstDir = extension.getCssDstDir();
            minifier.minify(srcDir, dstDir);
        }
        if (!extension.getJsSrcDir().isEmpty() && !extension.getJsDstDir().isEmpty()) {
            Minifier minifier = createJsMinifier();
            String srcDir = extension.getJsSrcDir();
            String dstDir = extension.getJsDstDir();
            minifier.minify(srcDir, dstDir);
        }
    }

    private CssMinifier createCSSMinifier() {
        CssMinifier cssMinifier = new CssMinifier();
        CSSMinifierOptions minifierOptions = cssMinifier.getMinifierOptions();
        minifierOptions.setCreateSoureMaps(extension.getCreateCssSourceMaps());
        minifierOptions.setOriginalFileNames(extension.getOriginalFileNames());

        // CSS specific options
        minifierOptions.setInputOrientation(extension.getCss().getInputOrientation());
        minifierOptions.setOutputOrientation(extension.getCss().getOutputOrientation());
        minifierOptions.setOutputFormat(extension.getCss().getOutputFormat());
        minifierOptions.setCopyrightNotice(extension.getCss().getCopyrightNotice());
        minifierOptions.setTrueConditionNames(extension.getCss().getTrueConditionNames());
        minifierOptions.setAllowDefPropagation(extension.getCss().getAllowDefPropagation());
        minifierOptions.setAllowUnrecognizedFunctions(extension.getCss().getAllowUnrecognizedFunctions());
        minifierOptions.setAllowedNonStandardFunctions(extension.getCss().getAllowedNonStandardFunctions());
        minifierOptions.setAllowedUnrecognizedProperties(extension.getCss().getAllowedUnrecognizedProperties());
        minifierOptions.setAllowUnrecognizedProperties(extension.getCss().getAllowUnrecognizedProperties());
        minifierOptions.setVendor(extension.getCss().getVendor());
        minifierOptions.setAllowKeyframes(extension.getCss().getAllowKeyframes());
        minifierOptions.setAllowWebkitKeyframes(extension.getCss().getAllowWebkitKeyframes());
        minifierOptions.setProcessDependencies(extension.getCss().getProcessDependencies());
        minifierOptions.setExcludedClassesFromRenaming(extension.getCss().getExcludedClassesFromRenaming());
        minifierOptions.setSimplifyCss(extension.getCss().getSimplifyCss());
        minifierOptions.setEliminateDeadStyles(extension.getCss().getEliminateDeadStyles());
        minifierOptions.setCssRenamingPrefix(extension.getCss().getCssRenamingPrefix());
        minifierOptions.setPreserveComments(extension.getCss().getPreserveComments());
        minifierOptions.setOutputRenamingMapFormat(extension.getCss().getOutputRenamingMapFormat());
        minifierOptions.setCompileConstants(extension.getCss().getCompileConstants());
        minifierOptions.setSourceMapLevel(extension.getCss().getSourceMapLevel());
        return cssMinifier;
    }

    private JsMinifier createJsMinifier() {
        JsMinifier jsMinifier = new JsMinifier();
        JSMinifierOptions minifierOptions = jsMinifier.getMinifierOptions();
        minifierOptions.setCreateSoureMaps(extension.getCreateJsSourceMaps());
        minifierOptions.setOriginalFileNames(extension.getOriginalFileNames());

        return jsMinifier;
    }
}
