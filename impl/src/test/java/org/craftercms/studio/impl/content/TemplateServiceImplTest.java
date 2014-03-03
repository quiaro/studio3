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
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link org.craftercms.studio.impl.content.TemplateServiceImpl}.
 *
 * @author Dejan Brkic
 */
public class TemplateServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private TemplateServiceImpl templateServiceSUT;

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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        Item testItem = templateServiceSUT.create(context, site, parentId, fileName, fileStream, props);

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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, fileStream, props);
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, fileStream, props);
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, fileStream, props);
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        Item testItem = templateServiceSUT.create(context, site, parentId, fileName, content, props);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 6:
     * Test create method using input stream and null context.
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, content, props);
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
     * Use case 7:
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, content, props);
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
     * Use case 8:
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
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, content, props);
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
     * Use case 9:
     * Test read method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);

        Item testItem = templateServiceSUT.read(context, site, templateItemId);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 10:
     * Test read method using null context.
     *
     * @throws Exception
     */
    @Test
    public void testReadUsingNullContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);

        try {
            Item testItem = templateServiceSUT.read(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 11:
     * Test read method using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testReadUsingInvalidContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = templateServiceSUT.create(context, site, parentId, fileName, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 12:
     * Test read method using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testReadUsingInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).read(Mockito
            .any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);

        try {
            Item testItem = templateServiceSUT.read(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 13:
     * Test read method using invalid template item id.
     *
     * @throws Exception
     */
    @Test
    public void testReadUsingInvalidItem() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock).read(Mockito
            .any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);

        try {
            Item testItem = templateServiceSUT.read(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 14:
     * Test update method using input stream and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStream() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        InputStream contentStream = IOUtils.toInputStream(content);
        Map<String, String> props = new HashMap<>();

        templateServiceSUT.update(context, site, templateItemId, contentStream, props);

        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 15:
     * Test update method using input stream and null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamNullContext() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        InputStream contentStream = IOUtils.toInputStream(content);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, contentStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 16:
     * Test update method using input stream invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamInvalidContext() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        InputStream contentStream = IOUtils.toInputStream(content);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, contentStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 17:
     * Test update method using invalid site and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        InputStream contentStream = IOUtils.toInputStream(content);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, contentStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 18:
     * Test update method using input stream invalid template item id.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamInvalidItem() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        InputStream contentStream = IOUtils.toInputStream(content);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, contentStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 19:
     * Test update method using input stream and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentString() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        templateServiceSUT.update(context, site, templateItemId, content, props);

        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 20:
     * Test update method using content string null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringNullContext() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 21:
     * Test update method using content string invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringInvalidContext() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 22:
     * Test update method using invalid site and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 23:
     * Test update method using content string invalid template item id.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringInvalidItem() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = UUID.randomUUID().toString();
        ItemId templateItemId = new ItemId(templateId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            templateServiceSUT.update(context, site, templateItemId, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 24:
     * Test delete method.
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = RandomStringUtils.random(10);
        ItemId templateItemId = new ItemId(templateId);

        // execute delete method
        templateServiceSUT.delete(context, site, templateItemId);

        verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class),Mockito.anyListOf(Item.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 25:
     * Test delete method using null context.
     * @throws Exception
     */
    @Test
    public void testDeleteWithNullContext() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String templateId = RandomStringUtils.random(10);
        ItemId templateItemId = new ItemId(templateId);

        // execute delete method
        try {
            templateServiceSUT.delete(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 26:
     * Test delete method using invalid context.
     * @throws Exception
     */
    @Test
    public void testDeleteWithInvalidContext() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = RandomStringUtils.random(10);
        ItemId templateItemId = new ItemId(templateId);

        // execute delete method
        try {
            templateServiceSUT.delete(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 27:
     * Test delete method for invalid site.
     * @throws Exception
     */
    @Test
    public void testDeleteSiteNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test.")).when(contentManagerMock)
            .delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = RandomStringUtils.random(10);
        ItemId templateItemId = new ItemId(templateId);

        // execute delete method
        try {
            templateServiceSUT.delete(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 28:
     * Test delete method for invalid item.
     * @throws Exception
     */
    @Test
    public void testDeleteItemNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test.")).when(contentManagerMock)
            .delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String templateId = RandomStringUtils.random(10);
        ItemId templateItemId = new ItemId(templateId);

        // execute delete method
        try {
            templateServiceSUT.delete(context, site, templateItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }
}
