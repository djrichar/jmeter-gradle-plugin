package com.github.djrichar.gradle.plugins.jmeter.task

import com.github.djrichar.gradle.plugins.jmeter.JmeterPluginExtension
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf
import org.apache.poi.hssf.usermodel.HeaderFooter
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction

import java.lang.annotation.Target

/**
 * Created by danielr928 on 4/20/14.
 */
class JmeterEditor extends JavaExec{

    @Override
    public void exec(){
//        super.workingDir = project.jmeter.jmeterHomeDir
        super.setWorkingDir(new File(project.jmeter.jmeterHomeDir,"lib"))
        super.setClasspath(project.jmeter.jmeterRuntime)
        super.jvmArgs([ "-Djmeter.home=$project.jmeter.jmeterHomeDir", "-Xms${project.jmeter.maxHeapSize}", "-Xmx${project.jmeter.maxHeapSize}"])
        super.args(["-p", new File(project.jmeter.jmeterHomeDir,"jmeter.properties").getCanonicalPath()])
        buildWorkDir()
        super.exec()
    }

    @Override
    public String getMain(){
        return "org.apache.jmeter.NewDriver";
    }


    public void buildWorkDir(){
        new File(project.jmeter.jmeterHomeDir,"jmeter.properties") << getClass().getClassLoader().getResourceAsStream('jmeter.properties').text
        File outputDir = new File(project.jmeter.jmeterHomeDir,"lib/ext")
        File junitDir = new File(outputDir.getParent(),"junit")
        logger.info("creating workDir: $outputDir")
        outputDir.mkdir()
        junitDir.mkdir()

        project.copy {
            from project.jmeter.jmeterRuntime
            into outputDir.getParentFile()
        }
        project.copy {
            from project.jmeter.jmeterExtension
            into outputDir
        }
    }
}
