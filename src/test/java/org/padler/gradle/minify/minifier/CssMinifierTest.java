package org.padler.gradle.minify.minifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CssMinifierTest {

    @TempDir
    public File testProjectDir;

    @Test
    public void minifyFile() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        cssMinifier.minify("src/test/resources/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(3);

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(2);
    }

    @Test
    public void minifyFileWithSourceMaps() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        cssMinifier.getMinifierOptions().setCreateSoureMaps(true);
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        cssMinifier.minify("src/test/resources/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(4);

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(3);
    }

    @Test
    public void minifyFileWithError() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        cssMinifier.minify("src/test/resources/errors/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(2);

        assertThat(cssMinifier.report.getErrors().size()).isEqualTo(0);
        assertThat(cssMinifier.report.getWarnings().size()).isEqualTo(1);
    }
}
