/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.tools;

import java.io.BufferedReader;
import java.io.PrintStream;

import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.repo.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.services.GridFSService;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;

/**
 * Repo Shell Context.
 * This object is just a getters for actions to interact with the shell.
 */
public class RepoShellContext {

    /**
     * Out Printer.
     */
    private final PrintStream out;
    /**
     * Input Reader.
     */
    private final BufferedReader in;
    /**
     * Current shell node.
     */
    private Node currentNode;
    /**
     * Node Services.
     */
    private NodeService nodeService;
    /**
     * Path Services.
     */
    private PathService pathService;
    /**
     * GridFs Services.
     */
    private GridFSService gridFSService;
    /**
     * Content Services.
     */
    private ContentService contentService;


    /**
     * Ctor.
     * @param currentNode  Current Node.
     * @param nodeService Node service Impl.
     * @param pathService Path service Impl.
     * @param gridFSService Grid Service Impl.
     * @param contentService Content Service Impl.
     * @param out Out printer.
     * @param in Input Reader.
     */
    public RepoShellContext(final Node currentNode, final NodeService nodeService, final PathService pathService,
                            final GridFSService gridFSService, final ContentService contentService,
                            final PrintStream out, final BufferedReader in) {
        this.currentNode = currentNode;
        this.nodeService = nodeService;
        this.pathService = pathService;
        this.gridFSService = gridFSService;
        this.contentService = contentService;
        this.out = out;
        this.in = in;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(final Node currentNode) {
        this.currentNode = currentNode;
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public PathService getPathService() {
        return pathService;
    }

    public GridFSService getGridFSService() {
        return gridFSService;
    }

    public ContentService getContentService() {
        return contentService;
    }

    public PrintStream getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
}
