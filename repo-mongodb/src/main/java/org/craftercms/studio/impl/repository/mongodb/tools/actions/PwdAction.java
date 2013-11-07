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

import org.craftercms.studio.api.RepositoryException;
import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.RepoShellContext;

/**
 * Returns the current directory.<br/>
 * This function returns with output relative to the current shell session, and does not impact the server.
 */
public class PwdAction extends AbstractAction {
    @Override
    public boolean responseTo(final String action) {
        return "pwd".equals(action);
    }

    @Override
    public void run(final RepoShellContext context, final String[] args) throws RepositoryException {
        String pwd = context.getPathService().getPathByItemId("Internal", "Internal",
            context.getCurrentNode().getId());
        context.getOut().println(pwd);
    }

    @Override
    public String printHelp() {
       return "Prints current directory path";
    }

    @Override
    public String actionName() {
        return "pwd";
    }
}
