package org.padler.gradle.minify.minifier.options;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinifierOptions {
    private Boolean createSoureMaps = false;
    private Boolean originalFileNames = false;
}
