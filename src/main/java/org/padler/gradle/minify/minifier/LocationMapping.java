package org.padler.gradle.minify.minifier;

import com.google.javascript.jscomp.SourceMap;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;

public class LocationMapping implements SourceMap.LocationMapping {

    private static String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
    private String baseDir;

    public LocationMapping(File baseDir) {
        if (isWindows()) {
            this.baseDir = baseDir.getAbsolutePath().replace("\\", "/");
        } else {
            this.baseDir = baseDir.getAbsolutePath();
        }
    }

    @Nullable
    @Override
    public String map(String location) {
        if (location.contains(baseDir))
            return location.substring(baseDir.length() + 1);
        else
            return null;
    }

    private static boolean isWindows() {
        return OS.contains("win");
    }

}
