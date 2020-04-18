package org.padler.gradle.minify.minifier;

import com.google.javascript.jscomp.SourceMap;

import javax.annotation.Nullable;

public class LocationMapping implements SourceMap.LocationMapping {
    @Nullable
    @Override
    public String map(String location) {
        int index = location.lastIndexOf('/');
        if (index != -1)
            return location.substring(index + 1);
        else
            return null;
    }
}
