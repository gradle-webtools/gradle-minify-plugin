package org.padler.gradle.minify.minifier;

import com.google.common.css.compiler.ast.GssError;
import com.google.javascript.jscomp.JSError;

public class Warning extends Event {
    public Warning(GssError error) {
        super(error);
    }

    public Warning(JSError error) {
        super(error);
    }
}
