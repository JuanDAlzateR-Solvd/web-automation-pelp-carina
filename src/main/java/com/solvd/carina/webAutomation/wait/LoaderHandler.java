package com.solvd.carina.webAutomation.wait;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoaderHandler.class);
    private final WebDriver driver;
    private final By loaderLocator;

    public LoaderHandler(WebDriver driver, By loaderLocator) {
        this.driver = driver;
        this.loaderLocator = loaderLocator;
    }

    public void waitForLoaderToDisappear(int timeout) {

        WebElement loaderElement = driver.findElement(loaderLocator);
        ExtendedWebElement loader =
                new ExtendedWebElement(loaderElement,"loader");

        if(loaderElement==null){
            logger.info("Loader element not found, skipping wait until loader disappears");
            return;
        }

        if (loader.isElementNotPresent(2)) {
            return;
        }

        loader.waitUntilElementDisappear(timeout);
    }

}
