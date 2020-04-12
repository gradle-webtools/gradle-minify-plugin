package org.padler.gradle.minify.minifier;

import com.google.javascript.jscomp.SourceMap;

import javax.annotation.Nullable;

public class LocationMapping implements SourceMap.LocationMapping {
    @Nullable
    @Override
    public String map(String location) {
        if (location.contains("/"))
            return location.substring(location.lastIndexOf("/") + 1);
        else
            return null;
    }
}
