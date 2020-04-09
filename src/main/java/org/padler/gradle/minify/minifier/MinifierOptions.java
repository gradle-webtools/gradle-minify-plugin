package org.padler.gradle.minify.minifier;

public class MinifierOptions {
    private Boolean createSoureMaps = false;

    public Boolean getCreateSoureMaps() {
        return createSoureMaps;
    }

    public void setCreateSoureMaps(Boolean createSoureMaps) {
        this.createSoureMaps = createSoureMaps;
    }
}
