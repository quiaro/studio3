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

package org.craftercms.studio.impl.content;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.RepositoryException;
import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.commons.extractor.ItemExtractor;
import org.craftercms.studio.commons.filter.ItemFilter;
import org.craftercms.studio.impl.AbstractManagerTest;
import org.craftercms.studio.impl.RepositoryMockException;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for Content Manager Implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class ContentManagerImplTest extends AbstractManagerTest {

    @Mock
    private ContentService contentServiceMock;

    @Autowired
    @InjectMocks
    private ContentManagerImpl contentManagerSUT;


    @After
    public void tearDown() throws Exception {
        reset(this.contentServiceMock);
    }

    @Test
    public void testRead() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    private InputStream getSampleContent() {
        InputStream sampleContent = this.getClass().getResourceAsStream("/content/sample.xml");
        URL url = this.getClass().getResource("/content/sample.xml");
        assertNotNull(sampleContent);
        return sampleContent;
    }

    @Test(expected = ItemNotFoundException.class)
    public void testReadItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenThrow(ItemNotFoundException.class);

        this.contentManagerSUT.read(new Context(), testItemId);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = RepositoryException.class)
    public void testReadInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenThrow(RepositoryMockException.class);

        this.contentManagerSUT.read(new Context(), testItemId);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testReadVersion() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId, testVersion);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testReadVersionItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId, testVersion);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testReadVersionInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId, testVersion);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testReadVersionVersionDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId, testVersion);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testReadVersionInvalidVersion() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);

        when(this.contentServiceMock.read(Mockito.anyString(), Mockito.anyString())).thenReturn(getSampleContent());

        this.contentManagerSUT.read(new Context(), testItemId, testVersion);

        verify(this.contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.eq(testItemId));
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdate() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        this.contentManagerSUT.update(new Context(), testItemId, getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        this.contentManagerSUT.update(new Context(), testItemId, getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        this.contentManagerSUT.update(new Context(), testItemId, getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateContentEmpty() throws Exception {
        String testItemId = UUID.randomUUID().toString();

        this.contentManagerSUT.update(new Context(), testItemId, getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testOpen() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.open(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testOpenItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.open(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testOpenInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.open(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testSave() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.save(new Context(), testItemId, new LockHandle(), getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testSaveItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.save(new Context(), testItemId, new LockHandle(), getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testSaveInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.save(new Context(), testItemId, new LockHandle(), getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testSaveInvalidLockHandle() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.save(new Context(), testItemId, new LockHandle(), getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testSaveContentEmpty() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.save(new Context(), testItemId, new LockHandle(), getSampleContent());
    }

    @Test(expected = NotImplementedException.class)
    public void testClose() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.close(new Context(), testItemId, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testCloseItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.close(new Context(), testItemId, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testCloseInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.close(new Context(), testItemId, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testCloseInvalidLockHandle() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.close(new Context(), testItemId, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testDelete() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.delete(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testDeleteEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.delete(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testDeleteItemDoesNotExist() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.delete(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyIncludeChildren() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, true);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyIncludeChildrenEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, true);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyIncludeChildrenDestinationInvalid() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, true);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyIncludeChildrenItemsAlreadyExistOnDestination() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, true);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyExcludeChildren() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, false);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyExcludeChildrenEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, false);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyExcludeChildrenInvalidDestinationPath() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, false);
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyExcludeChildrenItemsAlreadyExistOnDestination() throws Exception {
        List<Item> testItems = createItemListMock();
        String testDstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.copy(new Context(), testItems, testDstPath, false);
    }

    @Test(expected = NotImplementedException.class)
    public void testMove() throws Exception {
        List<Item> testItems = createItemListMock();
        String dstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.move(new Context(), testItems, dstPath);
    }

    @Test(expected = NotImplementedException.class)
    public void testMoveEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        String dstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.move(new Context(), testItems, dstPath);
    }

    @Test(expected = NotImplementedException.class)
    public void testMoveInvalidDestination() throws Exception {
        List<Item> testItems = createItemListMock();
        String dstPath = RandomStringUtils.randomAlphabetic(256);
        this.contentManagerSUT.move(new Context(), testItems, dstPath);
    }

    @Test(expected = NotImplementedException.class)
    public void testLock() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.lock(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testLockItemDoesNotExist() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.lock(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testLockEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.lock(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testUnlock() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.unlock(new Context(), testItems, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testUnlockEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.unlock(new Context(), testItems, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testUnlockItemDoesNotExist() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.unlock(new Context(), testItems, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testUnlockInvalidLockHandle() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.unlock(new Context(), testItems, new LockHandle());
    }

    @Test(expected = NotImplementedException.class)
    public void testGetLockStatus() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.getLockStatus(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testGetLockStatusEmptyList() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.getLockStatus(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testGetLockStatusItemDoesNotExist() throws Exception {
        List<Item> testItems = createItemListMock();
        this.contentManagerSUT.getLockStatus(new Context(), testItems);
    }

    @Test(expected = NotImplementedException.class)
    public void testList() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.list(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testListItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.list(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testListInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.contentManagerSUT.list(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testTree() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        int testDepth = (int)(Math.random() * 10);
        List<ItemFilter> testFilters = createItemFilterListMock();
        List<ItemExtractor> testExtractors = createItemExtractorListMock();
        this.contentManagerSUT.tree(new Context(), testItemId, testDepth, testFilters, testExtractors);
    }

    @Test(expected = NotImplementedException.class)
    public void testGetSiteList() throws Exception {
        this.contentManagerSUT.getSiteList(new Context());
    }
}
