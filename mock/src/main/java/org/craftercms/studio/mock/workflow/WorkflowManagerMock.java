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

package org.craftercms.studio.mock.workflow;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.workflow.WorkflowManager;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.WorkflowPackage;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.commons.filter.WorkflowPackageFilter;

/**
 * Workflow Manager Mock implementation.
 *
 * @author Dejan Brkic
 */
public class WorkflowManagerMock implements WorkflowManager {


    @Override
    public String start(final String packageName, final List<String> comments, final List<Item> items) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Item> getPackage(final String packageId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<WorkflowPackage> getPackages(final String site, final List<WorkflowPackageFilter> filters) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<WorkflowTransition> getTransitions(final String packageId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void transition(final String packageId, final WorkflowTransition transition, final Map<String, Object>
        params) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void cancel(final String packageId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
