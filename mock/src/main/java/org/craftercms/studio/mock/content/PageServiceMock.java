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

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.craftercms.studio.api.content.PageService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.LockStatus;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.commons.extractor.ItemExtractor;
import org.craftercms.studio.commons.filter.ItemFilter;

/**
 * Content Manager mock implementation.
 *
 * @author Dejan Brkic
 */
public class PageServiceMock implements PageService {

    public InputStream read(final Context context, final String itemId) throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }


    public InputStream read(final Context context, final String itemId, final String version) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public void update(final Context context, final String itemId, final InputStream content) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public LockHandle open(final Context context, final String itemId) {
        try {
            JAXBContext jc = JAXBContext.newInstance(LockHandleMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("open.xml");
            return (LockHandleMock)unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public void save(final Context context, final String itemId, final LockHandle lockHandle, final InputStream
        content) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public void close(final Context context, final String itemId, final LockHandle lockHandle) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public void delete(final Context context, final List<Item> itemsToDelete) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public void copy(final Context context, final List<Item> itemsToCopy, final String destinationPath,
                     final boolean includeChildren) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public void move(final Context context, final List<Item> itemsToMove, final String destinationPath) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public LockHandle lock(final Context context, final List<Item> itemsToLock) {
        try {
            JAXBContext jc = JAXBContext.newInstance(LockHandleMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("lock.xml");
            return (LockHandleMock)unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public void unlock(final Context context, final List<Item> itemsToUnlock, final LockHandle lockHandle) {
        throw new NotImplementedException("Not implemented yet!");
    }


    public List<LockStatus> getLockStatus(final Context context, final List<Item> items) {
        try {
            JAXBContext jc = JAXBContext.newInstance(LockStatusListMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("get_lock_status.xml");
            LockStatusListMock lockStatusList = (LockStatusListMock)unmarshaller.unmarshal(is);
            if (lockStatusList != null) {
                return lockStatusList.getLockStatusList();
            }
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public List<Item> list(final Context context, final String itemId) {
        try {
            JAXBContext jc = JAXBContext.newInstance(ItemListMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("list.xml");
            ItemListMock itemList = (ItemListMock)unmarshaller.unmarshal(is);
            if (itemList != null) {
                return itemList.getItemList();
            }
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public Tree<Item> tree(final Context context, final String itemId, final int depth,
                           final List<ItemFilter> filters, final List<ItemExtractor> extractors) {
        throw new NotImplementedException("Not implemented yet!");
        /*
        try {
            JAXBContext jc = JAXBContext.newInstance(TreeMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("tree.xml");
            TreeMock tree = (TreeMock)unmarshaller.unmarshal(is);
            //return tree;

        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
        */
    }


    public List<Site> getSiteList(final Context context) {
        try {
            JAXBContext jc = JAXBContext.newInstance(SiteListMock.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("site_list.xml");
            SiteListMock siteList = (SiteListMock)unmarshaller.unmarshal(is);
            if (siteList != null) {
                return siteList.getSiteList();
            }
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
