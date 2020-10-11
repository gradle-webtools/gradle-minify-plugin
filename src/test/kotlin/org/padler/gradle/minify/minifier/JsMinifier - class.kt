package org.padler.gradle.minify.minifier;

import org.gradle.api.GradleException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsMinifierTest {

    @TempDir
    public File testProjectDir;

    @Test
    void minifyFile() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(3);

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(2);
    }

    @Test
    void minifyFileWithoutRenaming() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setOriginalFileNames(true);
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(2);
        Path jsFile = files.stream().filter(path -> path.toFile().isFile()).findFirst().orElse(null);
        assertThat(jsFile.toFile().getAbsolutePath()).doesNotContain("min.js");

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(1);
    }

    @Test
    void minifyFileWithSourceMaps() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setCreateSourceMaps(true);
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(4);

        List<Path> minifiedJs = files.stream()
                .filter((path) -> path.toFile().getName().endsWith(".min.js"))
                .collect(Collectors.toList());
        assertThat(minifiedJs).hasSize(1);
        Path path = minifiedJs.get(0);
        List<String> lines = new BufferedReader(new FileReader(path.toFile())).lines().collect(Collectors.toList());
        assertThat(lines.get(lines.size() - 1)).isEqualTo("//# sourceMappingURL=" + path.getFileName() + ".map");

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size()).isEqualTo(3);
    }

    @Test
    void minifyFileWithError() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setCreateSourceMaps(true);
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        String dstPath = dst.getAbsolutePath();
        assertThrows(GradleException.class, () -> jsMinifier.minify("src/test/resources/errors/js", dstPath));

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(1);
        assertThat(jsMinifier.getReport().getErrors().size()).isEqualTo(1);
        assertThat(jsMinifier.getReport().getWarnings()).isEmpty();
    }

    @Test
    void minifyEmptyFile() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        File src = new File(testProjectDir, "empty");
        src.mkdir();
        File empty = new File(src, "empty.js");
        empty.createNewFile();
        File dst = new File(testProjectDir, "dst");
        dst.mkdir();

        jsMinifier.minify(src.getAbsolutePath(), dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size()).isEqualTo(2);
    }
}
