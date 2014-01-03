package org.craftercms.studio.mock.content;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.craftercms.studio.commons.dto.LockStatus;

/**
 * Lock Status List mock objects.
 *
 * @author Dejan Brkic
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "lock-status-list")
public class LockStatusListMock {

    @XmlElement(name = "lock-status")
    private List<LockStatus> lockStatusList;

    public List<LockStatus> getLockStatusList() {
        return lockStatusList;
    }

    public void setLockStatusList(final List<LockStatus> lockStatusList) {
        this.lockStatusList = lockStatusList;
    }
}
