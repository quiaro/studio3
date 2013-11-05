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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.tools.AbstractAction;
import org.craftercms.studio.impl.repository.mongodb.tools.RepoShellContext;

/**
 * Imports from a given Zipfile content
 */
public class ImportAction extends AbstractAction {

    @Override
    public boolean responseTo(final String action) {
        return "import".equalsIgnoreCase(action);
    }

    @Override
    public void run(final RepoShellContext context, final String[] args) throws StudioException {
        if (args.length == 2) {

            String parentID = context.getPathService().getItemIdByPath("INTERNAL", "INTERNAL", args[0]);
            if (parentID == null) {
                context.getOut().println("Path " + args[0] + " Not Found");
                return;
            }
            Tree<Item> toExport = context.getContentService().getChildren("Internal", "Internal", parentID, -1, null);
            try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(args[1])))) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {

                    if (entryIsFolder(entry)) {
                        Item item = new Item();
                        String folderName = getFileName(entry.getName());
                        item.setFileName(folderName);
                        item.setLabel(folderName);
                        context.getOut().printf("Importing %s \n", folderName);
                        context.getContentService().create("INTERNAL", "INTERNAL", args[0] + entry.getName(), item);
                    } else {
                        Item item = new Item();
                        String fileName = getFileName(entry.getName());
                        item.setFileName(fileName);
                        item.setLabel(fileName);
                        InputStream input = readZipInput(zipInputStream);
                        context.getOut().printf("Importing %s \n", fileName);
                        context.getContentService().create("INTERNAL", "INTERNAL", args[0] + fileFolder(entry.getName
                            ()), item, input);
                    }
                }
            } catch (IOException ex) {
                context.getOut().printf("Unable to export due %s", ex.getMessage());
            }
            context.getOut().printf("File %s was successfully imported \n", args[1]);
        } else {
            context.getOut().println("Usage: export pathToExport pathToSafeFile");
        }
    }

    /**
     * Gets the File Folder path base on the path
     * @param path Full path of the file
     * @return the Files Folder
     */
    private String  fileFolder(final String path) {
        return new File(path).getParent();
    }

    /**
     * Reads the ZipEntry and returns it as a InputStream (ByteArrayInputStream)
     * @param zipInputStream ZipStream to be read.
     * @return A InputStream with the entry.
     * @throws IOException If zipInputStream could not be read or Return Input could be write.
     */
    private InputStream readZipInput(final ZipInputStream zipInputStream) throws IOException {
        byte[] buffer = new byte[2048];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (zipInputStream.read(buffer) != -1) {
            out.write(buffer);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Checks if the ZipEntry represents a folder.
     * @param entry Entry To check.
     * @return True if entry is a folder (Ends with /)
     *         False otherwise.
     */
    private boolean entryIsFolder(final ZipEntry entry) {
        return entry.getName().endsWith(File.separator);
    }

    /**
     * Gets the file path for the given full path.
     * @param path to be check
     * @return File or Folder path.
     */
    private String getFileName(final String path) {
        return new File(path).getName();
    }

    @Override
    public String printHelp() {
        return "Imports content from a zip File";
    }

    @Override
    public String actionName() {
        return "import";
    }
}
