package org.padler.gradle.minify.minifier;

import com.google.common.css.compiler.ast.GssError;
import com.google.javascript.jscomp.JSError;

public class Event {
    private int charNo;
    private int lineNo;
    private String message;
    private String sourceFileName;

    public Event(GssError error) {
        charNo = error.getLocation().getBeginCharacterIndex();
        lineNo = error.getLocation().getLineNumber();
        message = error.getMessage();
        sourceFileName = error.getLocation().getSourceCode().getFileName();
    }

    public Event(JSError error) {
        charNo = error.getCharno();
        lineNo = error.getLineNumber();
        message = error.getDescription();
        sourceFileName = error.getSourceName();
    }

    @Override
    public final String toString() {
        return message
                + " at "
                + sourceFileName
                + " line "
                + lineNo
                + " : "
                + charNo;
    }
}
