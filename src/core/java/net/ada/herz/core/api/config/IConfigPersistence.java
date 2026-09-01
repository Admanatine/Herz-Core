package net.ada.herz.core.api.config;

import java.util.Map;

/**
 * Platform hook for persisting config namespaces.
 *
 * Web targets back this with localStorage; desktop targets use a JSON file.
 * Register an implementation via Herz.INSTANCE.setConfigPersistence() before
 * any package tries to load config.
 */
public interface IConfigPersistence {
    /**
     * Load all stored key/value pairs for the given namespace.
     * Returns an empty map if nothing has been saved yet.
     */
    Map<String, String> load(String namespace);

    /**
     * Persist all key/value pairs for the given namespace.
     */
    void save(String namespace, Map<String, String> values);
}
