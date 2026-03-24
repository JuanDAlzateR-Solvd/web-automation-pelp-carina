package com.solvd.carina.webAutomation.actions;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UIActions {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final WebDriver driver;

    public UIActions(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver must not be null.");
        }
        this.driver = driver;

    }

    public void scrollTo(ExtendedWebElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Element must not be null.");
        }

        if (!element.isElementPresent(10)) {
            throw new IllegalStateException(
                    String.format("Element '%s' is not present in DOM.", element.getName()));
        }

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
                (Boolean) ((JavascriptExecutor) driver).executeScript(script, element.getElement())
        );

        logger.info("Element [{}] is {}in viewport", elementName, isVisible ? "" : "not ");
        return isVisible;
    }

}
