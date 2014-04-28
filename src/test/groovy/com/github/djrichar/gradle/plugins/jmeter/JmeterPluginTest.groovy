package com.github.djrichar.gradle.plugins.jmeter

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created by danielr928 on 4/20/14.
 */
class JmeterPluginTest {

    @Test
    public void test(){
        Project project = ProjectBuilder.builder().build();
        project.apply(plugin: 'jmeter2')
        project.repositories.mavenCentral()
        project.jmeterEditor.execute()
    }
}
