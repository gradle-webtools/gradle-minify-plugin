package org.padler.gradle.minify;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.util.GFileUtils.writeFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.padler.gradle.minify.MinifyPlugin.TASK_NAME;

public class MinifyTaskTest {

    @Rule
    public final TemporaryFolder testProjectDir =
            new TemporaryFolder();

    private void setUpTestProject() throws Exception {
        File buildFile = testProjectDir.newFile("build.gradle");
        File cssDir = testProjectDir.newFolder("css");
        File jsDir = testProjectDir.newFolder("js");
        File cssFile = new File(cssDir, "css.css");
        File jsFile = new File(jsDir, "js.js");
        cssFile.createNewFile();
        Files.write(jsFile.toPath(), "alert('Hello, world!');".getBytes());
        String plugin = "plugins { id 'org.padler.gradle.minify' version '1.0' }";
        String config = "minification{cssDstDir=\"$buildDir/dist/css\"\ncssSrcDir=\"${rootDir}/css\"\njsDstDir=\"$buildDir/dist/js\"\njsSrcDir=\"${rootDir}/js\"}";
        writeFile(plugin + "\n" + config, buildFile);
    }

    @Test
    public void test() throws Exception {
        setUpTestProject();

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withPluginClasspath()
                .withArguments(TASK_NAME, "--stacktrace")
                .build();

        assertThat(result.task(":" + TASK_NAME).getOutcome(), equalTo(SUCCESS));
    }

}
