package org.padler.gradle.minify.minifier.result;

import com.google.common.css.compiler.ast.GssError;
import com.google.javascript.jscomp.JSError;

public class Error extends Event {
    public Error(GssError error) {
        super(error);
    }

    public Error(JSError error) {
        super(error);
    }
}
