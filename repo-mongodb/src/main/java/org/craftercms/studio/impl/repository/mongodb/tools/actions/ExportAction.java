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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.ActionContext;

public class ExportAction extends AbstractAction {

    @Override
    public boolean responseTo(final String action) {
        return "export".equals(action);
    }

    @Override
    public void run(final ActionContext context, final String[] args) throws StudioException {
        if (args.length == 2) {

            String parentID = context.getPathService().getItemIdByPath("INTERNAL", "INTERNAL", args[0]);
            if (parentID == null) {
                context.getOut().println("Path " + args[0] + " Not Found");
                return;
            }
            Tree<Item> toExport = context.getContentService().getChildren("Internal", "Internal", parentID, -1, null);
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(args[1])))) {

                final ZipEntry testEntry = new ZipEntry("TestingOfAzip.txt");
                testEntry.setMethod(ZipEntry.DEFLATED);
                zipOutputStream.putNextEntry(testEntry);
                zipOutputStream.write("Hello World".getBytes(Charset.forName("UTF-8")));
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
            } catch (IOException ex) {
                context.getOut().printf("Unable to export due %s", ex.getMessage());
            }
            context.getOut().printf("File %s was sucessfuly written \n", args[1]);
        } else {
            context.getOut().println("Usage: export pathToExport pathToSafeFile");
        }
    }

    private void buildZip(final TreeNode<Item> treeNode, final ZipEntry entry,) {

    }

    @Override
    public String printHelp() {
        return "Export given path to a Zipfile on the given location";
    }

    @Override
    public String actionName() {
        return "export";
    }
}
