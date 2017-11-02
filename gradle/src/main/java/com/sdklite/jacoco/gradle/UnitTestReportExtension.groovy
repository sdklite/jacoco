package com.sdklite.jacoco.gradle

/**
 * The extension of {@link AndroidJacocoPlugin}
 */
public class UnitTestReportExtension {

    /**
     * The exclusion patterns for Android data binding
     */
    public static final Collection<String> ANDROID_DATA_BINDING_EXCLUDES = [
        'android/databinding/**/*.class',
        '**/android/databinding/*Binding.class',
        '**/BR.*'
    ].asImmutable()

    /**
     * The exclusion patterns for Android build tools
     */
    public static final Collection<String> ANDROID_EXCLUDES = [
        'android/**/*.class',
        '**/R.class',
        '**/R$*.class',
        '**/BuildConfig.*',
        '**/Manifest*.*'
    ].asImmutable()

    /**
     * The exclusion patterns for Butter Knife
     */
    public static final Collection<String> BUTTER_KNIFE_EXCLUDES = [
        '**/*$ViewInjector*.*',
        '**/*$ViewBinder*.*'
    ].asImmutable()

    /**
     * The exclusion patterns for Dagger 2
     */
    public static final Collection<String> DAGGER_2_EXCLUDES = [
        '**/*_MembersInjector.class',
        '**/Dagger*Component.class',
        '**/Dagger*Component$Builder.class',
        '**/*Module_*Factory.class'
    ].asImmutable()

    /**
     * The default exclusion patterns
     */
    public static final Collection<String> DEFAULT_EXCLUDES =(ANDROID_DATA_BINDING_EXCLUDES + ANDROID_EXCLUDES + BUTTER_KNIFE_EXCLUDES + DAGGER_2_EXCLUDES).asImmutable()

    static Closure<Collection<String>> DEFAULT_EXCLUDES_FACTORY = { DEFAULT_EXCLUDES }

    /**
     * The exclusion patterns
     */
    Collection<String> excludes;

    /**
     * The report configuration for CSV format
     */
    ReportConfig csv;

    /**
     * The report configuration for XML format
     */
    ReportConfig xml;

    /**
     * The report configuration for HTML format
     */
    ReportConfig html;

    /**
     * Initialize with exclusion patterns, all report configurations are enabled in default.
     */
    public UnitTestReportExtension(final Collection<String> excludes) {
        this.excludes = excludes;
        this.csv = new ReportConfig(true);
        this.html = new ReportConfig(true);
        this.xml = new ReportConfig(true);
    }
}
