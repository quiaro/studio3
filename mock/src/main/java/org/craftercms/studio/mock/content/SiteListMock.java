
/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.mock.content;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.craftercms.studio.commons.dto.Site;

/**
 * Site List mock object.
 *
 * @author Dejan Brkic
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "site-list")
public class SiteListMock {

    @XmlElement(name = "site")
    private List<Site> siteList = new ArrayList<Site>();

    public List<Site> getSiteList() {
        return siteList;
    }

    public void setSiteList(final List<Site> siteList) {
        this.siteList = siteList;
    }
}
