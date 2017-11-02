## Introduction

JaCoCo gradle plugin is used to generate code coverage reports for Android project, it creates `JaCoCoReport` task for the variants which `buildType.testCoverageEnabled` is `true`.

## Getting Started

1. Config JaCoCo gradle plugin in `build.gradle` of root project.

    ```groovy
    buildscript {
        repositories {
            ...
            jcenter()
        }
        dependencies {
            ...
            classpath 'com.sdklite.jacoco:gradle:0.1.0'
        }
    }
    ```

1. Apply JaCoCo gradle plugin in `build.gradle` of android project

    ```groovy
    apply plugin: 'com.sdklite.jacoco'

    android {
        ...
        buildTypes {
            debug {
                ...
                testCoverageEnabled true
            }
            ...
        }
    }

    jacocoUnitTestReport {
        csv.enabled false
        xml.enabled false
        html.enabled true
    }
    ```

    The plugin excludes Android generated classes from report by default. You can specify custom exclusion patterns by `jacocoUnitTestReport`:

    ```groovy
    jacocoUnitTestReport {
        ...
        excludes += [
            '**/AutoValue_*.*',
            ...
        ]
    }
    ```

1. Generate coverage report

    ```bash
    $ ./gradlew jacocoTestReport
    ```

## Example

- [https://github.com/sdklite/jacoco/blob/master/example](./example)

