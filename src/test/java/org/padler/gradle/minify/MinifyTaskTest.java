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
import static org.padler.gradle.minify.MinifyPlugin.TASK_NAME;

class MinifyTaskTest {

    @TempDir
    public File testProjectDir;

    private void setUpTestProject() throws Exception {
        File buildFile = new File(testProjectDir, "build.gradle");
        File cssDir = new File(testProjectDir, "css");
        File jsDir = new File(testProjectDir, "js");
        cssDir.mkdir();
        jsDir.mkdir();
        File cssFile = new File(cssDir, "css.css");
        File jsFile = new File(jsDir, "js.js");
        cssFile.createNewFile();
        Files.write(jsFile.toPath(), "alert('Hello, world!');".getBytes());
        String plugin = "plugins { id 'org.padler.gradle.minify' version '1.6.0' }";
        String config = "minification{cssDstDir=\"$buildDir/dist/css\"\ncssSrcDir=\"${rootDir}/css\"\njsDstDir=\"$buildDir/dist/js\"\njsSrcDir=\"${rootDir}/js\"}";
        writeFile(plugin + "\n" + config, buildFile);
    }

    @Test
    void test() throws Exception {
        setUpTestProject();

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments(TASK_NAME, "--stacktrace")
                .build();

        assertThat(result.task(":" + TASK_NAME).getOutcome()).isEqualTo(SUCCESS);
    }

}
