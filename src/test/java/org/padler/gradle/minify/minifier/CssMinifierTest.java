package org.padler.gradle.minify.minifier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CssMinifierTest {

    @Rule
    public final TemporaryFolder testProjectDir =
            new TemporaryFolder();

    @Test
    public void minifyFile() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File dst = testProjectDir.newFolder("dst");

        cssMinifier.minify("src/test/resources/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(2));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(1));
    }

    @Test
    public void minifyFileWithSourceMaps() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        cssMinifier.getMinifierOptions().setCreateSoureMaps(true);
        File dst = testProjectDir.newFolder("dst");

        cssMinifier.minify("src/test/resources/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(3));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(2));
    }

    @Test
    public void minifyFileWithError() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File dst = testProjectDir.newFolder("dst");

        cssMinifier.minify("src/test/resources/errors/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(1));

        assertThat(cssMinifier.report.getErrors().size(), is(0));
        assertThat(cssMinifier.report.getWarnings().size(), is(1));
    }
}
