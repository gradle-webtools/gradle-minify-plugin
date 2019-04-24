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

public class JsMinifierTest {

    @Rule
    public final TemporaryFolder testProjectDir =
            new TemporaryFolder();

    @Test
    public void minifyFile() throws Exception {
        JsMinifier jsMinifier = new JsMinifier();
        File jsSrc = testProjectDir.newFolder("js");
        File sub = testProjectDir.newFolder("js", "sub");
        File dst = testProjectDir.newFolder("dst");
        File jsFile = new File(jsSrc, "js.js");
        File subFile = new File(sub, "subjs.js");
        Files.write(jsFile.toPath(), "alert('Hello, world!');".getBytes());
        Files.write(subFile.toPath(), "alert('Hello, world!');".getBytes());

        jsMinifier.minify(jsSrc.toString(), dst.getAbsolutePath());

        List<Path> files = Files.list(Paths.get(dst.getAbsolutePath() + "/")).collect(Collectors.toList());
        assertThat(files.size(), is(2));

        Path subDir = files.stream().filter(p -> p.toFile().getName().endsWith("sub")).findFirst().orElse(null);
        List<Path> subFiles = Files.list(subDir).collect(Collectors.toList());
        assertThat(subFiles.size(), is(1));

    }
}
