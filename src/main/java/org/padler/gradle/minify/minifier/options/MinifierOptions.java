package org.padler.gradle.minify.minifier.options;

public class MinifierOptions {
    private Boolean createSoureMaps = false;
    private Boolean originalFileNames = false;

    public Boolean getCreateSoureMaps() {
        return createSoureMaps;
    }

    public void setCreateSoureMaps(Boolean createSoureMaps) {
        this.createSoureMaps = createSoureMaps;
    }

    public Boolean getOriginalFileNames() {
        return originalFileNames;
    }

    public void setOriginalFileNames(Boolean originalFileNames) {
        this.originalFileNames = originalFileNames;
    }
}
