package org.padler.gradle.minify;

public class MinifyPluginExtension {

    private String cssDstDir = "";

    private String cssSrcDir = "";

    private String jsDstDir = "";

    private String jsSrcDir = "";

    public String getCssDstDir() {
        return cssDstDir;
    }

    public void setCssDstDir(String cssDstDir) {
        this.cssDstDir = cssDstDir;
    }

    public String getCssSrcDir() {
        return cssSrcDir;
    }

    public void setCssSrcDir(String cssSrcDir) {
        this.cssSrcDir = cssSrcDir;
    }

    public String getJsDstDir() {
        return jsDstDir;
    }

    public void setJsDstDir(String jsDstDir) {
        this.jsDstDir = jsDstDir;
    }

    public String getJsSrcDir() {
        return jsSrcDir;
    }

    public void setJsSrcDir(String jsSrcDir) {
        this.jsSrcDir = jsSrcDir;
    }

}
