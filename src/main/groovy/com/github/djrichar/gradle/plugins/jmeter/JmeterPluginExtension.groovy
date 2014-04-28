package com.github.djrichar.gradle.plugins.jmeter

import org.gradle.api.Project
import org.gradle.api.file.FileCollection

/**
 * Created by danielr928 on 4/20/14.
 */
class JmeterPluginExtension {

    /**
     * jmeter test files
     */
    List<File> jmeterTestFiles

    /**
     * directory where jmeter and the plugin jars should live
     */
    File jmeterHomeDir

    /**
     * directory where the reports should be stored
     */
    File reportDir

    /**
     * the version of Jmeter to use
     */
    String jmeterVersion = "2.9"

    /**
     * the size for the java heap
     */
    String maxHeapSize = "512m"

    /**
     * jmeterRuntime this is a Filecollection of all the classes needed to run jmeter
     */
    FileCollection jmeterRuntime

    /**
     * jmeterExtension these are all the jars the need to be in the lib/ext folder of jmeter for jmeter to run.
     */
    FileCollection jmeterExtension

    JmeterPluginExtension(Project project){
        jmeterHomeDir = new File(project.buildDir,"jmeter");
    }
}
