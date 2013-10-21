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

import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.ActionContext;

public class CdAction extends AbstractAction {
    @Override
    public boolean responseTo(final String action) {
        return "cd".equals(action);
    }

    @Override
    public void run(final ActionContext context, final String[] args) throws StudioException {
        if (args.length == 1) {
            String path = args[0];
            if (!path.startsWith(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR)) {
                String tmp=context.getPathService().getPathByItemId("INTERNAL","INTERNAL",
                    context.getCurrentNode().getId());
                path=tmp+path;
            }
            String id = context.getPathService().getItemIdByPath("InternalTooling", "InternalSite", path);

            if (id == null) {
                context.getOut().printf("Folder %s Does not exist \n", path);
                return;
            }
            Node n = context.getNodeService().getNode(id);
            if (n == null) {
                context.getOut().printf("Folder with id %s Does not exist \n", id);
                return;
            }
            if (context.getNodeService().isNodeFolder(n)) {
                context.setCurrentNode(n);
            } else {
                context.getOut().printf("Folder %s is not a folder \n", path);
            }
        } else {
            context.getOut().println("Cd cmd requires a param");
        }
    }

    @Override
    public String printHelp() {
        return "Change current folder";
    }

    @Override
    public String actionName() {
        return "cd";
    }
}
