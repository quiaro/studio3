package org.craftercms.studio.utils;

import java.util.Map;
import java.util.UUID;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final Logger log=LoggerFactory.getLogger(RestControllerUtils.class);
        log.debug("About to filter from {} {}", map, keys);
        for (String keyToRemove : keys) {
            map.remove(keyToRemove);
        }
        log.debug("Finish filtering of map");
    }

    public static Context createMockContext() {
        Tenant tenant = new Tenant();
        Context context = new Context(UUID.randomUUID().toString(), tenant);
        return context;
    }
}
