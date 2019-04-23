package org.padler.gradle.minify;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MinifyPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("minification", MinifyPluginExtension.class);
        MinifyTask minify = project.getTasks().create("minify", MinifyTask.class);
        minify.setGroup("build setup");
    }

}
