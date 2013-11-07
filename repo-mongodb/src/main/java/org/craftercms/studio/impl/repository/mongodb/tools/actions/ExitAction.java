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

import java.util.Arrays;

import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.RepoShellContext;

/**
 * Quits mongo repo shell.
 */
public class ExitAction extends AbstractAction {
    @Override
    public boolean responseTo(final String action) {
        return Arrays.asList("exit","quit").contains(action);
    }

    @Override
    public void run(final RepoShellContext context, final String[] args) {
        context.getOut().println("Bye");
        System.exit(0);
    }

    @Override
    public String printHelp() {
        return "Quits the system";
    }

    @Override
    public String actionName() {
        return "exit,quit";
    }
}
