/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.api.workflow;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.WorkflowPackage;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.filter.WorkflowPackageFilter;

/**
 * Workflow Manager.
 *
 * @author Carlos Ortiz
 */
public interface WorkflowManager {

    /**
     * Start workflow.
     *
     * @param packageName package name
     * @param comments    comments
     * @param items       items
     * @return package id
     */
    String start(String packageName, List<String> comments, List<Item> items);

    /**
     * Get package.
     *
     * @param packageId package id
     * @return list of items in package
     */
    List<Item> getPackage(String packageId);

    /**
     * Get workflow packages.
     *
     * @param site    site
     * @param filters filters
     * @return list of packages
     */
    List<WorkflowPackage> getPackages(String site, List<WorkflowPackageFilter> filters);

    /**
     * Get transitions.
     *
     * @param packageId package id
     * @return workflow transitions
     */
    List<WorkflowTransition> getTransitions(String packageId);

    /**
     * Transition.
     *
     * @param packageId  package id
     * @param transition transition
     * @param params     parameters
     */
    void transition(String packageId, WorkflowTransition transition, Map<String, Object> params);

    /**
     * Cancel workflow.
     *
     * @param packageId package id
     */
    void cancel(String packageId);
}
