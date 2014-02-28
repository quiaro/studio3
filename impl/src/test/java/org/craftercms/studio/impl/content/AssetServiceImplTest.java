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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link org.craftercms.studio.impl.content.AssetServiceImpl}.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private AssetServiceImpl assetServiceSUT;

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
     * Test create method with input stream using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStream() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, fileStream, mimeType, props);

        // assert and verify
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 2:
     * Test create method with string content using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentString() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, content, mimeType, props);

        // assert and verify
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 3:
     * Test create method with byte array using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingByteArray() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, contentByteArray, mimeType, props);

        // assert and verify
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use Case 4:
     * Test create methods with context = null.
     */
    @Test
    public void testCreateWithInputStreamUsingNullContext() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, fileStream, mimeType,
                props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use Case 5:
     * Test create methods with context = null.
     */
    @Test
    public void testCreateWithStringContentUsingNullContext() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, content, mimeType,
                props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());

            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use Case 6:
     * Test create methods with context = null.
     */
    @Test
    public void testCreateUsingNullContext() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
        Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, contentByteArray, mimeType,
            props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use Case 7:
     * Test create methods with invalid context.
     */
    @Test
    public void testCreateWithInputStreamUsingInvalidContext() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, fileStream, mimeType,
                props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use Case 8:
     * Test create methods with invalid context.
     */
    @Test
    public void testCreateWithStringContentUsingInvalidContext() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, content, mimeType,
                props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use Case 9:
     * Test create methods with invalid context.
     */
    @Test
    public void testCreateUsingInvalidContext() throws Exception {
        // Setup mock objects
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String destinationPath = RandomStringUtils.random(100);
        String fileName = RandomStringUtils.random(15);
        String content = RandomStringUtils.random(1000);
        InputStream fileStream = IOUtils.toInputStream(content);
        byte[] contentByteArray = content.getBytes();
        String mimeType = RandomStringUtils.random(10);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.create(context, site, destinationPath, fileName, contentByteArray, mimeType,
                props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(expectedException.getErrorCode(), StudioException.ErrorCode.INVALID_CONTEXT);
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 10:
     * Test read method
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        Item assetItem = assetServiceSUT.read(context, site, assetId);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 11:
     * Test read method using context = null
     * @throws Exception
     */
    @Test
    public void testReadWithNullContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            Item assetItem = assetServiceSUT.read(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 12:
     * Test read method using invalid context
     * @throws Exception
     */
    @Test
    public void testReadWithInvalidContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            Item assetItem = assetServiceSUT.read(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 13:
     * Test read method for non-existing site
     * @throws Exception
     */
    @Test
    public void testReadSiteNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test.")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            Item assetItem = assetServiceSUT.read(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 14:
     * Test read method
     * @throws Exception
     */
    @Test
    public void testReadAssetNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            Item assetItem = assetServiceSUT.read(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 15:
     * Test get text content method.
     * @throws Exception
     */
    @Test
    public void testGetTextContent() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        String assetContent = assetServiceSUT.getTextContent(context, site, assetId);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 16:
     * Test get text content method using null context.
     * @throws Exception
     */
    @Test
    public void testGetTextContentWithNullContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            String assetContent = assetServiceSUT.getTextContent(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 17:
     * Test get text content method using invalid context.
     * @throws Exception
     */
    @Test
    public void testGetTextContentWithInvalidContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            String assetContent = assetServiceSUT.getTextContent(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 18:
     * Test get text content method for invalid site.
     * @throws Exception
     */
    @Test
    public void testGetTextContentSiteNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test.")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            String assetContent = assetServiceSUT.getTextContent(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 19:
     * Test get text content method for invalid item.
     * @throws Exception
     */
    @Test
    public void testGetTextContentItemNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test.")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);

        // execute read method
        try {
            String assetContent = assetServiceSUT.getTextContent(context, site, assetId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 20:
     * Test get input stream method.
     * @throws Exception
     */
    @Test
    public void testGetInputStream() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemID = new ItemId(assetId);

        // execute read method
        InputStream assetContentStream = assetServiceSUT.getInputStream(context, site, assetItemID);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 21:
     * Test get input stream method using null context.
     * @throws Exception
     */
    @Test
    public void testGetInputStreamWithNullContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute read method
        try {
            InputStream assetContentStream = assetServiceSUT.getInputStream(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 22:
     * Test get input stream method using invalid context.
     * @throws Exception
     */
    @Test
    public void testGetInputStreamWithInvalidContext() throws Exception {
        // Set up mock objects
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute read method
        try {
            InputStream assetContentStream = assetServiceSUT.getInputStream(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 23:
     * Test get input stream method for invalid site.
     * @throws Exception
     */
    @Test
    public void testGetInputStreamSiteNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test.")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute read method
        try {
            InputStream assetContentStream = assetServiceSUT.getInputStream(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 24:
     * Test get text input stream for invalid item.
     * @throws Exception
     */
    @Test
    public void testGetInputStreamItemNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test.")).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute read method
        try {
            InputStream assetContentStream = assetServiceSUT.getInputStream(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 25:
     * Test update method with input stream using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStream() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.update(context, site, assetItemId, fileStream, props);

        // assert and verify
        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 26:
     * Test update method with string content using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentString() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.update(context, site, assetItemId, content, props);

        // assert and verify
        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 27:
     * Test update method with byte array using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingByteArray() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = assetServiceSUT.update(context, site, assetItemId, contentByteArray, props);

        // assert and verify
        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 28:
     * Test update method with input stream using null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithNullContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 29:
     * Test update method with string content using null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithNullContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 30:
     * Test update method with byte array using null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingByteArrayWithNullContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, contentByteArray, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));  return;
        }
        fail();
    }

    /**
     * Use case 31:
     * Test update method with input stream using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 32:
     * Test update method with string content using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 33:
     * Test update method with byte array using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingByteArrayWithInvalidContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, contentByteArray, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
        }
    }

    /**
     * Use case 34:
     * Test update method with input stream using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidSite() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 35:
     * Test update method with string content using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidSite() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 36:
     * Test update method with byte array using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingByteArrayWithInvalidSite() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, contentByteArray, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 37:
     * Test update method with input stream using invalid item.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidItem() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 38:
     * Test update method with string content using invalid item.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidItem() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 39:
     * Test update method with byte array using invalid item.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingByteArrayWithInvalidItem() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String assetId = UUID.randomUUID().toString();
        ItemId assetItemId = new ItemId(assetId);
        String content = RandomStringUtils.random(1000);
        byte[] contentByteArray = content.getBytes();
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = assetServiceSUT.update(context, site, assetItemId, contentByteArray, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 40:
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
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute delete method
        assetServiceSUT.delete(context, site, assetItemId);

        verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class),Mockito.anyListOf(Item.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 41:
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
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute delete method
        try {
            assetServiceSUT.delete(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 42:
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
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute delete method
        try {
            assetServiceSUT.delete(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 43:
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
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute delete method
        try {
            assetServiceSUT.delete(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 44:
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
        String assetId = RandomStringUtils.random(10);
        ItemId assetItemId = new ItemId(assetId);

        // execute delete method
        try {
            assetServiceSUT.delete(context, site, assetItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }
}
