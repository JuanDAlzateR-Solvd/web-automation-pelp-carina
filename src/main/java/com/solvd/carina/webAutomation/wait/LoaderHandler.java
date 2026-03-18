package com.solvd.carina.webAutomation.wait;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoaderHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoaderHandler.class);
    private final WebDriver driver;
    private final By loaderLocator;

    public LoaderHandler(WebDriver driver, By loaderLocator) {
        this.driver = driver;
        this.loaderLocator = loaderLocator;
    }

    public void waitForLoaderToDisappear(int timeout) {

        List <WebElement> elements = driver.findElements(loaderLocator);
        if (elements.isEmpty()) {
            logger.info("Loader element not found, skipping wait until loader disappears");
            return; }

        ExtendedWebElement loader = new ExtendedWebElement(elements.get(0), "loader");

        if (loader.isElementNotPresent(Timeouts.SHORT)) {
            logger.debug("Loader is already not present, skipping wait");
            return;
        }

        logger.debug("Waiting up to {} seconds for loader to disappear", timeout);
        loader.waitUntilElementDisappear(timeout);
    }

}
