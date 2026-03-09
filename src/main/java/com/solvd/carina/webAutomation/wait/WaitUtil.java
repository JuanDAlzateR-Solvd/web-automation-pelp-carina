package com.solvd.carina.webAutomation.wait;


import com.zebrunner.carina.utils.config.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

public class WaitUtil {

    private static final Logger logger = LoggerFactory.getLogger(WaitUtil.class);

    private final WebDriver driver;
    private final int defaultTimeout;

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
        this.defaultTimeout = Configuration.get("explicit_timeout")
                .map(Integer::parseInt)
                .orElse(10);
        logger.debug("WaitUtil default timeout is set to: {}", this.defaultTimeout);
    }

    private WebDriverWait buildWait(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    private WebDriverWait buildWait() {
        return buildWait(defaultTimeout);
    }

    // ==========================
    // ELEMENT WAITS
    // ==========================

    public void waitForElementVisible(WebElement element, String elementName) {
        logger.debug("Waiting for element to be visible: {}", elementName);
        buildWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickable(WebElement element, String elementName) {
        logger.debug("Waiting for element to be clickable: {}", elementName);
        buildWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibility(WebElement element, String elementName) {
        logger.debug("Waiting for element to be invisible: {}", elementName);
        buildWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForInvisibilityOfElementLocated(By locator, String elementName) {
        logger.debug("Waiting for element to be invisible: {}", elementName);
        buildWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForPresenceOfElementLocated(By locator) {
        logger.debug("Waiting for presence of element located by {}", locator);
        return buildWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForNumberOfElementsToBeMoreThan(By locator, int number) {
        logger.debug("Waiting for number of elements located by {} to be more than {}", locator, number);
        buildWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, number));
    }

    public void waitForNumberOfElementsToBe(By locator, int number) {
        logger.debug("Waiting for number of elements located by {} to be {}", locator, number);
        buildWait().until(ExpectedConditions.numberOfElementsToBe(locator, number));
    }

    // ==========================
    // ALERTS
    // ==========================

    public Alert waitForAlert() {
        logger.debug("Waiting for alert to be present");
        return buildWait().until(ExpectedConditions.alertIsPresent());

    }

    // ==========================
    // PAGE LOAD
    // ==========================

    public void waitForPageLoad() {
        logger.debug("Waiting for page load to complete");
        buildWait().until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    /**
     * Pauses using Thread sleep. Use only for debug code, not for test implementation.
     *
     * @param milliseconds int number of milliseconds to pause*
     */
    @Deprecated
    public void debugPause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}