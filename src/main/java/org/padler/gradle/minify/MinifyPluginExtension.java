package org.padler.gradle.minify;

import groovy.lang.Closure;
import lombok.Getter;
import lombok.Setter;

import static groovy.lang.Closure.DELEGATE_FIRST;

@Getter
@Setter
public class MinifyPluginExtension {
    private String cssDstDir = "";
    private String cssSrcDir = "";
    private String jsDstDir = "";
    private String jsSrcDir = "";
    private Boolean createJsSourceMaps = false;
    private Boolean createCssSourceMaps = false;
    private Boolean originalFileNames = false;

    private MinifyCSSPluginExtension css = new MinifyCSSPluginExtension();

    public void css(Closure c) {
        c.setResolveStrategy(DELEGATE_FIRST);
        c.setDelegate(css);
        c.call();
    }

    private MinifyJSPluginExtension js = new MinifyJSPluginExtension();

    public void js(Closure c) {
        c.setResolveStrategy(DELEGATE_FIRST);
        c.setDelegate(js);
        c.call();
    }
}
