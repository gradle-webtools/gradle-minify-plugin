package org.padler.gradle.minify;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MinifyPlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = "minification";
    public static final String TASK_NAME = "minify";

    @Override
    public void apply(Project project) {
        project.getExtensions().create(EXTENSION_NAME, MinifyPluginExtension.class);
        MinifyTask minify = project.getTasks().create(TASK_NAME, MinifyTask.class);
        minify.setGroup("build setup");
    }

}
