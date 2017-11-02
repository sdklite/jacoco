package com.sdklite.jacoco.gradle

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reporting.ConfigurableReport
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin

/**
 * The gradle plugin for coverage report generation
 */
public class AndroidJacocoPlugin implements Plugin<Project> {

    @Override
    void apply(final Project project) {
        project.extensions.create("jacocoUnitTestReport", UnitTestReportExtension, UnitTestReportExtension.DEFAULT_EXCLUDES_FACTORY());
        project.plugins.apply(JacocoPlugin);

        project.afterEvaluate {
            def isApp = project.plugins.hasPlugin(AppPlugin);
            def isLib = project.plugins.hasPlugin(LibraryPlugin);

            if ((!isApp) && (!isLib)) {
                throw new GradleException("Not an Android project");
            }

            def jacocoTestReportTask = project.tasks.findByName("jacocoTestReport");
            if (!jacocoTestReportTask) {
                jacocoTestReportTask = project.tasks.create("jacocoTestReport");
                jacocoTestReportTask.group = "Reporting";
            }

            def variants = isApp ? project.android.applicationVariants : project.android.libraryVariants;
            variants.all { variant ->
                if (!variant.buildType.testCoverageEnabled) {
                    return;
                }

                def testUnitTestTask = project.tasks.withType(Test).find { it.name =~ /test${variant.name.capitalize()}UnitTest/ }
                def jacocoReportTask = project.tasks.create("jacoco${testUnitTestTask.name.capitalize()}Report", JacocoReport);
                jacocoReportTask.group = "Reporting";
                jacocoReportTask.description = "Generates Jacoco coverage reports for the ${variant.name} variant."
                jacocoReportTask.executionData = project.fileTree(dir: "${project.buildDir}", includes: [
                    "jacoco/test${variant.name.capitalize()}UnitTest.exec",
                    "outputs/code-coverage/connected/*coverage.ec"
                ]);
                jacocoReportTask.sourceDirectories = project.files(variant.sourceSets.java.srcDirs.collect { it.path }.flatten());
                jacocoReportTask.classDirectories = project.fileTree(dir: variant.javaCompile.destinationDir, excludes: project.jacocoUnitTestReport.excludes)
                jacocoReportTask.reports {
                    csv.enabled project.jacocoUnitTestReport.csv.enabled
                    xml.enabled project.jacocoUnitTestReport.xml.enabled
                    html.enabled  project.jacocoUnitTestReport.html.enabled
                }

                jacocoReportTask.doLast {
                    jacocoReportTask.reports { report ->
                        if (!report.enabled) {
                            return;
                        }

                        if (report.csv.enabled) {
                            println "See the report at: ${report.csv.destination}";
                        }

                        if (report.xml.enabled) {
                            println "See the report at: ${report.xml.destination}";
                        }

                        if (report.html.enabled) {
                            println "See the report at: ${report.html.entryPoint}";
                        }
                    }
                }

                def createCoverageReportTask = project.tasks.findByName("create${variant.name.capitalize()}CoverageReport");
                if (createCoverageReportTask) {
                    jacocoReportTask.dependsOn(testUnitTestTask, createCoverageReportTask);
                } else {
                    jacocoReportTask.dependsOn(testUnitTestTask);
                }

                jacocoTestReportTask.dependsOn(jacocoReportTask);
            }
        }
    }

}
