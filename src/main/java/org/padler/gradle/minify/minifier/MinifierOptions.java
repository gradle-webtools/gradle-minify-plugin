package org.padler.gradle.minify.minifier;

public class MinifierOptions {
    private Boolean createSoureMaps = false;
    private Boolean renameToMin = false;

    public Boolean getCreateSoureMaps() {
        return createSoureMaps;
    }

    public void setCreateSoureMaps(Boolean createSoureMaps) {
        this.createSoureMaps = createSoureMaps;
    }

    public Boolean getRenameToMin() {
        return renameToMin;
    }

    public void setRenameToMin(Boolean renameToMin) {
        this.renameToMin = renameToMin;
    }
}
