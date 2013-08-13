package org.craftercms.studio.utils;

import java.util.Map;

/**
 * Utility Class for Rest controllers
 * contains reusable methods for Rest controllers.
 */
public final class RestControllerUtils {
    private RestControllerUtils() {
    }

    /**
     * Removes from the map the given keys.
     * @param map  Map in which the keys will be remove
     * @param keys keys to be remove from the map
     */
    public static void removeParamters(Map<String, Object> map, String... keys) {
        for (String keyToRemove : keys) {
            map.remove(keyToRemove);
        }
    }
}
