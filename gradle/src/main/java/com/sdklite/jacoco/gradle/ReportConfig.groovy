package com.sdklite.jacoco.gradle

/**
 * Unit Test Report Configuration
 */
public class ReportConfig {

    private boolean enabled;

    ReportConfig(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns true if only this configuration is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Set the enabled state of this configuration.
     *
     * @param enabled
     *          True if this configuration is enabled, false otherwise.
     */
    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }
}
