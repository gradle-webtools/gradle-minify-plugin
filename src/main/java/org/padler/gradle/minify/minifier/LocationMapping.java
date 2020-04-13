package org.padler.gradle.minify.minifier;

import com.google.javascript.jscomp.SourceMap;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;

public class LocationMapping implements SourceMap.LocationMapping {

    private static String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
    private String baseDir;

    public LocationMapping(File baseDir) {
        this.baseDir = convertPath(baseDir);
    }

    @Nullable
    @Override
    public String map(String location) {
        if (location.contains(baseDir))
            return location.substring(baseDir.length() + 1);
        else
            return null;
    }

    @Nullable
    public String map(File location) {
        String file = convertPath(location);
        return map(file);
    }

    private static boolean isWindows() {
        return OS.contains("win");
    }

    private static String convertPath(File file) {
        if (isWindows()) {
            return file.getAbsolutePath().replace("\\", "/");
        } else {
            return file.getAbsolutePath();
        }
    }
}
