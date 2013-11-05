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

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Defines an Shell Action.
 */
public abstract class AbstractAction {

    /**
     * This CTOR will be use for dynamic instantiations.
     */
    public AbstractAction() {
    }

    /**
     * Does this actions response to given.
     *
     * @param action Action name. this is send by the shell for example 'cd','ls','pwd'...
     * @return <b>true</b> if this action response,<b>false</b> if not.
     */
    public abstract boolean responseTo(String action);

    /**
     * Runs the action with the given context and
     * action arguments.
     *
     * @param context Context of the current repo shell.
     * @param args    Arguments of the action (can be null).
     */
    public abstract void run(final RepoShellContext context, String[] args) throws StudioException;

    /**
     * Prints long help message for this action. (more details about this action).
     *
     * @return Help message to be display on help.
     */
    public abstract String printHelp();

    /**
     * Prints a short  help.
     *
     * @return A short summary about this action
     */
    public abstract String actionName();


}
