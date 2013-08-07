package org.craftercms.studio.api.config;

import java.io.InputStream;
import java.util.Map;

import org.craftercms.studio.api.security.Context;

/**
 * System configuration management API
 *
 * @author Sumer Jabri
 */
public interface ConfigManager {

    // TODO Consider inheritance with module config (master config and override)
    // todo must allow for global config
    String readConfig(Context context, String site, String module);

    void writeConfig(Context context, String module, Map<String, String> config);

//     read(Context context, String objectPath);
//    module_configuration	configuration			U	M	site	module
//    configure			A	S	site	module	configuration
//    binary	content			U	M	site	object
//    write			U	S	site	object	content
}
