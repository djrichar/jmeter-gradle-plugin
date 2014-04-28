package com.github.djrichar.gradle.plugins.jmeter

import com.github.djrichar.gradle.plugins.jmeter.task.JmeterEditor
import com.github.kulya.gradle.plugins.jmeter.JmeterAbstractTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by danielr928 on 4/20/14.
 */
class JmeterPlugin implements Plugin<Project> {
    private final static String JMETER = 'jmeter'
    private final static String JMETER_RUNTIME = 'jmeterRuntime'
    private final static String JMETER_EXTENSION = 'jmeterExtendsion'

    private Project project
    public void apply(final Project project) {
        //create configurations
        createConfiguration(project)
        //apply extension
        JmeterPluginExtension extension = project.extensions.create(JMETER, JmeterPluginExtension, project)
        //applyConfigurations
        applyRuntimeConfiguration(project, extension)
        //register tasks
        project.task("jmeterEditor",  type: JmeterEditor)
    }

    private void createConfiguration(Project project){
        project.configurations.create(JMETER_RUNTIME).with {
            visible = false
            transitive = true
            description = 'The jmeter runtime Jars used for running jmeter.'
        }
        project.configurations.create(JMETER_EXTENSION).with {
            visible = true
            transitive = true
            description = 'The jmeter extension Jars used for applying jmeter plugins.'
        }
    }

    private void applyRuntimeConfiguration(Project project, JmeterPluginExtension extension){
        def jmeterRuntime = project.configurations[JMETER_RUNTIME]
        def jmeterExtension = project.configurations[JMETER_EXTENSION]

//        project.tasks.withType(JmeterAbstractTask) { task ->
//            task.conventionMapping.jmeterRuntime = { jmeterRuntime }
//            task.conventionMapping.jmeterRuntime = { jmeterExtension }
//        }

//        jmeterRuntime.incoming.beforeResolve {
            if (jmeterRuntime.dependencies.empty) {
                jmeterRuntime.dependencies.add(project.dependencies.create("org.apache.jmeter:ApacheJMeter:${extension.jmeterVersion}"))
            }
//        }


        if (jmeterExtension.dependencies.empty) {
            jmeterExtension.dependencies.addAll(
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_http:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_junit:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_java:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_report:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_jdbc:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_tcp:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_mail:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_ldap:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_ftp:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_functions:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_monitors:${extension.jmeterVersion}"),
                    project.dependencies.create("org.apache.jmeter:ApacheJMeter_jms:${extension.jmeterVersion}"))
        }

        extension.jmeterRuntime = jmeterRuntime
        extension.jmeterExtension = jmeterExtension
    }
}