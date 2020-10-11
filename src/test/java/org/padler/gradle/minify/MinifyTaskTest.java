package org.padler.gradle.minify;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.util.GFileUtils.writeFile;

class MinifyTaskTest {

    @TempDir
    public File testProjectDir;

    private void setUpTestProject() throws Exception {
        File buildFile = new File(testProjectDir, "build.gradle.kts");
        File cssDir = new File(testProjectDir, "css");
        File jsDir = new File(testProjectDir, "js");
        cssDir.mkdir();
        jsDir.mkdir();
        File cssFile = new File(cssDir, "css.css");
        File jsFile = new File(jsDir, "js.js");
        cssFile.createNewFile();
        Files.write(jsFile.toPath(), "alert('Hello, world!');\n\nalert('Hello, world!');\n\nalert('Hello, world!');\n\nalert('Hello, world!');\n\n".getBytes());
        String plugin = "plugins { id (\"org.padler.gradle.minify\") version \"1.6.0\" }";
        String config = "tasks.create<org.padler.gradle.minify.JsMinifyTask>(\"minify\") { \n" +
                "srcDir = project.file(\"js\")\n" +
                "dstDir = project.file(\"build/js\")" +
                "}";
        writeFile(plugin + "\n" + config, buildFile);
    }

    @Test
    void test() throws Exception {
        setUpTestProject();

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("minify", "--stacktrace")
                .build();

        assertThat(result.task(":minify").getOutcome()).isEqualTo(SUCCESS);
        assertThat(new File(testProjectDir, "build/js/js.js")).hasContent("'use strict';alert(\"Hello, world!\");alert(\"Hello, world!\");alert(\"Hello, world!\");alert(\"Hello, world!\");");
    }
}
