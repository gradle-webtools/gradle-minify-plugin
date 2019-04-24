package org.padler.gradle.minify.minifier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

public class CssMinifierTest {

    @Rule
    public final TemporaryFolder testProjectDir =
            new TemporaryFolder();

    @Test
    public void minifyFile() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File file = testProjectDir.newFile("css.css");

        cssMinifier.minify(file.toString(), testProjectDir.getRoot().getAbsolutePath());
    }
}
