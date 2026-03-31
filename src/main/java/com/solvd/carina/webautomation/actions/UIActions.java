package com.solvd.carina.webautomation.actions;

import com.solvd.carina.webautomation.browser.BrowserDetector;
import com.solvd.carina.webautomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UIActions {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final WebDriver driver;
    private final WaitUtil waitUtil;

    public UIActions(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver must not be null.");
        }
        this.driver = driver;
        this.waitUtil = new WaitUtil(driver);
    }

    public void scrollTo(ExtendedWebElement element) {
        validateElement(element);

        logger.info("Scrolling to element: {}", element.getName());
        element.scrollTo();

        if (!isInViewport(element, element.getName())) {
            logger.info("Element is not in viewport, scrolling to it");
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                        element.getElement()
                );
            } catch (StaleElementReferenceException e) {
                throw new IllegalStateException(
                        String.format("Element '%s' became stale while trying to scroll.", element.getName()),
                        e
                );
            }

        }

    }

    public void clickUsingBestStrategy(ExtendedWebElement element) {
        validateElement(element);

        String elementName = element.getName();
        if (BrowserDetector.isSafari(driver)) {
            logger.info("Clicking element [{}] using native Safari strategy", elementName);
            element.click();
            return;
        }

        logger.info("Clicking element [{}] using JavaScript strategy", elementName);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element.getElement());
        } catch (RuntimeException e) {
            logger.warn("JavaScript click failed for element [{}]: {}. Falling back to native click.",
                    elementName,
                    e.getMessage());
            element.click();
        }
    }

    public void acceptAlert(String alertName, int timeoutInSeconds) {
        Alert alert = waitForAlert(alertName, timeoutInSeconds);
        acceptAlert(alert, alertName);
    }

    public String getAlertText(String alertName, int timeoutInSeconds) {
        return waitForAlert(alertName, timeoutInSeconds).getText();
    }

    public String getAlertTextAndAccept(String alertName, int timeoutInSeconds) {
        Alert alert = waitForAlert(alertName, timeoutInSeconds);
        String alertText = alert.getText();
        acceptAlert(alert, alertName);
        return alertText;
    }

    public boolean isAlertPresent(String alertName, int timeoutInSeconds) {
        logger.debug("Checking whether '{}' alert is present", alertName);
        try {
            waitForAlert(alertName, timeoutInSeconds);
            return true;
        } catch (TimeoutException e) {
            logger.debug("'{}' alert was not present within {} seconds", alertName, timeoutInSeconds);
            return false;
        }
    }

    public boolean isInViewport(ExtendedWebElement element, String elementName) {
        logger.info("Checking if element [{}] is in viewport", elementName);

        String script = """
                    var elem = arguments[0],
                        box = elem.getBoundingClientRect();
                    return (
                        box.top >= 0 &&
                        box.left >= 0 &&
                        box.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
                        box.right <= (window.innerWidth || document.documentElement.clientWidth)
                    );
                """;

        boolean isVisible = Boolean.TRUE.equals(
                ((JavascriptExecutor) driver).executeScript(script, element.getElement())
        );

        logger.info("Element [{}] is {}in viewport", elementName, isVisible ? "" : "not ");
        return isVisible;
    }

    private void validateElement(ExtendedWebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Element must not be null.");
        }

        if (!element.isElementPresent(10)) {
            throw new IllegalStateException(
                    String.format("Element '%s' is not present in DOM.", element.getName()));
        }
    }

    private Alert waitForAlert(String alertName, int timeoutInSeconds) {
        logger.info("Waiting for '{}' alert", alertName);
        return waitUtil.waitForAlert(timeoutInSeconds);
    }

    private void acceptAlert(Alert alert, String alertName) {
        if (BrowserDetector.isSafari(driver)) {
            logger.info("Accepting '{}' alert using Safari mobile strategy", alertName);
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "mobile: alert",
                        Map.of("action", "accept")
                );
                return;
            } catch (Exception e) {
                logger.warn("Safari mobile alert strategy failed for '{}': {}. Falling back to Selenium alert.accept().",
                        alertName,
                        e.getMessage());
            }
        }

        logger.info("Accepting '{}' alert using Selenium alert.accept()", alertName);
        alert.accept();
    }

}
