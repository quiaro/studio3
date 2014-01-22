package org.craftercms.studio.api.plugin;

import java.util.List;

/**
 * Plugin Service provides an API to managing Plugins to Crafter Studio.
 *
 * @author Sumer Jabri
 */
public interface PluginService {
    // TODO FINISH THIS
    void createPlugin();
    String getPlugin();
    void updatePlugin();
    void deletePlugin();
    List<String> listPluginsBy();
}
