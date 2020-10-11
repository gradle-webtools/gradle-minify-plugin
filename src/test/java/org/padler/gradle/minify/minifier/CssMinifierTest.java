package org.padler.gradle.minify.minifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CssMinifierTest {

    @TempDir
    public File testProjectDir;

    @Test
    void minifyFile() throws Exception {
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
    void minifyFileWithSourceMaps() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        cssMinifier.getMinifierOptions().setCreateSourceMaps(true);
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        cssMinifier.minify("src/test/resources/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(4);

        List<Path> minifiedCss = files.stream()
                .filter((path) -> path.toFile().getName().endsWith(".min.css"))
                .collect(Collectors.toList());
        assertThat(minifiedCss).hasSize(1);
        Path path = minifiedCss.get(0);
        List<String> lines = new BufferedReader(new FileReader(path.toFile())).lines().collect(Collectors.toList());
        assertThat(lines.get(lines.size() - 1)).isEqualTo("/*# sourceMappingURL=" + path.getFileName() + ".map */");

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(3);
    }

    @Test
    void minifyFileWithError() throws Exception {
        CssMinifier cssMinifier = new CssMinifier();
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        cssMinifier.minify("src/test/resources/errors/css", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(2);

        assertThat(cssMinifier.getReport().getErrors()).isEmpty();
        assertThat(cssMinifier.getReport().getWarnings().size()).isEqualTo(1);
    }
}
