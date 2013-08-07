package org.craftercms.studio.api.audit;

import java.util.List;

import org.craftercms.studio.api.dto.AuditActivity;
import org.craftercms.studio.api.filter.Filter;
import org.craftercms.studio.api.security.Context;

/**
 * Audit management API
 *
 * @author Sumer Jabri
 */
public interface AuditManager {
    public List<AuditActivity> activity(Context context, List<Filter> filters);

    public void log(Context context, AuditActivity activity);
}
