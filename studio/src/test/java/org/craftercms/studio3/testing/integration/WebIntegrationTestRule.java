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

package org.craftercms.studio3.testing.integration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class WebIntegrationTestRule extends TestWatcher {

    private String screenshotOutputFolder;
    private WebDriver driver;


    public WebIntegrationTestRule(String screenshotOutputFolder, WebDriver driver) {
        this.screenshotOutputFolder = screenshotOutputFolder;
        this.driver = driver;
    }

    @Override
    public void failed(Throwable e, Description description) {

        try {
            File shoot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(shoot, new File(screenshotOutputFolder +
                    File.separator + description.getMethodName() + ".png"));
        } catch (IOException ex) {
            System.out.println("Unable to save screenshot");
        }

    }

    @Override
    protected void finished(Description description) {
        driver.quit();
    }
}
