package org.craftercms.studio.api.security;

import java.net.URL;

/**
 * @author Sumer Jabri
 */
public interface SecurityManager {
    /**
     *
     * @param repositoryUrl
     * @param username
     * @param password
     * @return
     */
    Context login(URL repositoryUrl, String username, String password);

    /**
     *
     * @param context
     */
    void logout(Context context);
//    getPermissions ticket, item_id
//    addPermissions ticket, item_id, List<Permission>, Propagation
//    removePermissions ticket, item_id, List<Permission>
}
