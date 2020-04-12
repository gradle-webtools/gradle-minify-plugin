package org.padler.gradle.minify;

import lombok.Getter;
import lombok.Setter;

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
}
