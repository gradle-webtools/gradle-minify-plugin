package org.padler.gradle.minify.minifier;

import org.gradle.api.GradleException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JsMinifierTest {

    @Rule
    public final TemporaryFolder testProjectDir =
            new TemporaryFolder();

    @Test
    public void minifyFile() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        File dst = testProjectDir.newFolder("dst");

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(3));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(2));
    }

    @Test
    public void minifyFileWithoutRenaming() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setOriginalFileNames(true);
        File dst = testProjectDir.newFolder("dst");

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(2));
        Path jsFile = files.stream().filter(path -> path.toFile().isFile()).findFirst().orElse(null);
        assertThat(jsFile.toFile().getAbsolutePath(), not(containsString("min.js")));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(1));
    }

    @Test
    public void minifyFileWithSourceMaps() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setCreateSoureMaps(true);
        File dst = testProjectDir.newFolder("dst");

        jsMinifier.minify("src/test/resources/js", dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(4));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(3));
    }

    @Test
    public void minifyFileWithError() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        jsMinifier.getMinifierOptions().setCreateSoureMaps(true);
        File dst = testProjectDir.newFolder("dst");

        try {
            jsMinifier.minify("src/test/resources/errors/js", dst.getAbsolutePath());
            fail("expected exception");
        } catch (GradleException e) {
            List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
            assertThat(files.size(), is(1));
            assertThat(jsMinifier.report.getErrors().size(), is(1));
            assertThat(jsMinifier.report.getWarnings().size(), is(0));
        }
    }

    @Test
    public void minifyEmptyFile() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        File src = testProjectDir.newFolder("empty");
        testProjectDir.newFile("empty/empty.js");
        File dst = testProjectDir.newFolder("dst");

        jsMinifier.minify(src.getAbsolutePath(), dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(2));
    }
}
