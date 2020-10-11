package org.padler.gradle.minify;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MinifyPluginTest {

    //@Test
    void should_add_task_to_project() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("org.padler.gradle.minify");

        assertTrue(project.getTasks().getByName("minify") instanceof MinifyTask);
    }

}
