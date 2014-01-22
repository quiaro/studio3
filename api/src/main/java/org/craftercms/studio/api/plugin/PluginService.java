package org.craftercms.studio.api.plugin;

import java.util.List;

/**
 * Component Service provides an API to managing Components in a CrafterCMS-based site.
 *
 * @author Sumer Jabri
 */
public interface PluginService {
    // TODO FINISH THIS
    void createComponent();
    String getComponent();
    void updateComponent();
    void deleteComponent();
    List<String> listComponentsBy();
}
