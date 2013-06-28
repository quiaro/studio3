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

import com.thoughtworks.selenium.Selenium;
import org.junit.Rule;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Base class for all Selenium base test.
 */
public abstract class AbstractIntegrationTest{

    private final static String WEB_TESTING_PROPERTIES="webTesting.properties";
    protected Selenium selenium;
    protected WebDriver driver;
    protected String baseUrl;
    protected StringBuffer verificationErrors = new StringBuffer();
    protected static DesiredCapabilities desiredCapabilities;
    protected Properties testProperties;

    /**
     * Because it has to be (Junit)
     */
    @Rule
    public WebIntegrationTestRule webIntegrationTestRule;

    public AbstractIntegrationTest() throws Exception{
        testProperties = new Properties();
        testProperties.load(AbstractIntegrationTest.class.getClassLoader().getResourceAsStream(WEB_TESTING_PROPERTIES));
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        driver = new FirefoxDriver(desiredCapabilities);
        baseUrl = testProperties.getProperty("test.base.url");
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(testProperties.getProperty("test.timeout")), TimeUnit.SECONDS);
        webIntegrationTestRule = new WebIntegrationTestRule(testProperties.getProperty("test.screenshotOutputPath"),driver);
    }

}
