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

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link org.craftercms.studio.impl.content.AssetServiceImpl}.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImplTest extends AbstractServiceTest {

    @Autowired
    @InjectMocks
    private AssetServiceImpl assetServiceSUT;

    @Autowired
    private ContentManager contentManagerMock;

    @Test
    public void testCreateMethod() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(createItemMock().getId());

        Tenant tenant = new Tenant();
        Context context = new Context(RandomStringUtils.randomAlphanumeric(32), tenant);
        InputStream content = this.getClass().getResourceAsStream("/content/sample.xml");
        Map<String, String> properties = new HashMap<String, String>();

        assetServiceSUT.create(context, "site", "destinationPath", "fileName", content, "mimeType", properties);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    // TODO: Use case for invalid executions (exceptions, invalid arguments)


    @Test
    public void testCreateMethod2() throws Exception {
        //when(contentManagerMock.create())

    }
}
