package net.ada.herz.core.api.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Scoped key/value config store for a single package namespace.
 *
 * Obtain via Herz.INSTANCE.getConfig("your.namespace").
 * Values are strings; typed helpers (getInt, getBool, etc.) parse on read.
 *
 * Persistence is delegated to the platform's IConfigPersistence. Call load()
 * early (e.g. in ClientInitEvent) and save() whenever you mutate values.
 */
public class HerzConfigNamespace {

    private final String namespace;
    private final IConfigPersistence persistence;
    private final Map<String, String> values = new HashMap<>();

    public HerzConfigNamespace(String namespace, IConfigPersistence persistence) {
        this.namespace = namespace;
        this.persistence = persistence;
    }

    // --- Lifecycle ---

    public HerzConfigNamespace load() {
        values.clear();
        values.putAll(persistence.load(namespace));
        return this;
    }

    public HerzConfigNamespace save() {
        persistence.save(namespace, values);
        return this;
    }

    // --- String ---

    public HerzConfigNamespace set(String key, String value) {
        values.put(key, value);
        return this;
    }

    public String get(String key) {
        return values.get(key);
    }

    public String get(String key, String defaultValue) {
        return values.getOrDefault(key, defaultValue);
    }

    // --- Int ---

    public HerzConfigNamespace setInt(String key, int value) {
        return set(key, String.valueOf(value));
    }

    public int getInt(String key, int defaultValue) {
        String raw = values.get(key);
        if (raw == null) return defaultValue;
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // --- Boolean ---

    public HerzConfigNamespace setBool(String key, boolean value) {
        return set(key, String.valueOf(value));
    }

    public boolean getBool(String key, boolean defaultValue) {
        String raw = values.get(key);
        if (raw == null) return defaultValue;
        return Boolean.parseBoolean(raw);
    }

    // --- Float ---

    public HerzConfigNamespace setFloat(String key, float value) {
        return set(key, String.valueOf(value));
    }

    public float getFloat(String key, float defaultValue) {
        String raw = values.get(key);
        if (raw == null) return defaultValue;
        try {
            return Float.parseFloat(raw);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // --- Utility ---

    public boolean has(String key) {
        return values.containsKey(key);
    }

    public HerzConfigNamespace remove(String key) {
        values.remove(key);
        return this;
    }

    public HerzConfigNamespace clear() {
        values.clear();
        return this;
    }

    public String getNamespace() {
        return namespace;
    }
}
