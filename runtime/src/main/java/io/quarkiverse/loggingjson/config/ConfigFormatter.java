package io.quarkiverse.loggingjson.config;

public interface ConfigFormatter {

    /**
     * Determine whether the formatter is enabled.
     *
     * @return true if the formatter is enabled, false otherwise
     */
    boolean isEnabled();
}
