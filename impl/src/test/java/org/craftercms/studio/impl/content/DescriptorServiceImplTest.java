/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for DescriptorService implementation.
 *
 * @author Dejan Brkic
 */
public class DescriptorServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private DescriptorServiceImpl descriptorServiceSUT;

    @Autowired
    private ContentManager contentManagerMock;

    @Autowired
    private SecurityService securityServiceMock;


    @Override
    protected void resetMocks() {
        reset(contentManagerMock, securityServiceMock);
    }

    /**
     * Use case 1:
     * Test create method using input stream and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStream() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream,
            props);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 2:
     * Test create method using input stream and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamNullContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 3:
     * Test create method using input stream and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 4:
     * Test create method using input stream and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 5:
     * Test create method using input stream and duplicate content type id.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidContentType() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 6:
     * Test create method using content string and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentString() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
            props);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 7:
     * Test create method using content string and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringNullContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 8:
     * Test create method using content string and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 9:
     * Test create method using content string and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 10:
     * Test create method using content string and duplicate content type id.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidContentType() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 11:
     * Test duplicate method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicate() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(duplicatedId));
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 12:
     * Test duplicate method using null context.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingNullContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 13:
     * Test duplicate method using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 14:
     * Test duplicate method using content string and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).read(Mockito
            .any(Context.class), Mockito.anyString(), Mockito.anyString());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 15:
     * Test duplicate method using content string and invalid duplicated content type id.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidContentType() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }
}
