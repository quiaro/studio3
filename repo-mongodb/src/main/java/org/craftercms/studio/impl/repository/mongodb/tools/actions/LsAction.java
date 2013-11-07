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

package org.craftercms.studio.impl.repository.mongodb.tools.actions;

import java.util.List;

import org.craftercms.studio.api.RepositoryException;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.RepoShellContext;

/**
 * Returns a list of the files in the current directory.<br/>
 * This function returns with output relative to the current shell session, and does not impact the repository.
 */
public class LsAction extends AbstractAction {
    @Override
    public boolean responseTo(final String action) {
        return "ls".equals(action);
    }

    @Override
    public void run(final RepoShellContext context, final String[] args) throws StudioException {
        if (args == null || args.length == 0) {
            lsCurrentNode(context);
        } else if (args[0].startsWith(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR)) {
            lsFromPath(args[0], context);
        } else {
            context.getOut().println("Given path is not valid to do ls");
        }
    }

    private void lsFromPath(final String arg, final RepoShellContext context) throws RepositoryException {

        String id = context.getPathService().getItemIdByPath("INTERNAL", "INTERNAL", arg);
        ls(context.getNodeService().getChildren(id), context);
    }

    private void lsCurrentNode(final RepoShellContext context) throws RepositoryException {
        List<Node> children = context.getNodeService().getChildren(context.getCurrentNode().getId());
        ls(children, context);
    }

    private void ls(List<Node> children, RepoShellContext context) {
        for (Node node : children) {
            context.getOut().print("\t");
            if (context.getNodeService().isNodeFolder(node)) {
                context.getOut().printf("/%s", node.getMetadata().getCore().getNodeName());
            } else {
                context.getOut().printf("%s", node.getMetadata().getCore().getNodeName());
            }
            context.getOut().print("\n");
        }
    }

    @Override
    public String printHelp() {
        return "List all children fo current folder";
    }

    @Override
    public String actionName() {
        return "ls";
    }
}
