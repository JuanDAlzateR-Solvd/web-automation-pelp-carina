package com.solvd.carina.webAutomation.pages.common;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage extends AbstractPage {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public BasePage(WebDriver driver) {
        super(driver);

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );
    }

    protected abstract By getPageLoadedIndicator();

    public void click(ExtendedWebElement element) {
        click(element, element.getName());
    }

    public void click(ExtendedWebElement element, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
        scrollTo(element);
        element.click();
    }

    protected void type(ExtendedWebElement element, String elementName, String text) {
        logger.info("Typing on element [{}]", elementName);
        scrollTo(element);
//        element.clear();
        element.type(text);
    }

    protected String getText(ExtendedWebElement element) {
        return getText(element, element.getName());
    }

    protected String getText(ExtendedWebElement element, String elementName) {
        logger.info("Getting text from element [{}]", elementName);
        scrollTo(element);
        return element.getText();
    }

    protected boolean isVisible(ExtendedWebElement element) {
        return isVisible(element, element.getName());
    }

    protected boolean isVisible(ExtendedWebElement element, String elementName) {
        logger.info("Checking if visibility of element [{}]", elementName);
        boolean isVisible = false;
        isVisible = element.isVisible();
        if (isVisible) {
            logger.info("Element [{}] is visible", elementName);
            return true;
        }else  {
            logger.info("Element [{}] is not visible", elementName);
            return false;
        }
    }

    protected boolean isInViewport(ExtendedWebElement element, String elementName) {
        logger.info("Checking if element is in Viewport [{}]", elementName);
        Boolean isInViewport = (Boolean) ((JavascriptExecutor) driver)
                .executeScript(
                        "var elem = arguments[0],                 " +
                                "  box = elem.getBoundingClientRect();    " +
                                "return (                                 " +
                                "  box.top >= 0 &&                        " +
                                "  box.left >= 0 &&                       " +
                                "  box.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                "  box.right <= (window.innerWidth || document.documentElement.clientWidth)       " +
                                ");",
                        element);
        String aux = Boolean.TRUE.equals(isInViewport) ? "" : "not";
        logger.info("Element [{}] is " + aux + " in Viewport", elementName);
        return Boolean.TRUE.equals(isInViewport);
    }

    protected boolean isClickable(ExtendedWebElement element) {
        return isClickable(element, element.getName());
    }

    protected boolean isClickable(ExtendedWebElement element, String elementName) {

        logger.info("Checking if clickable on element [{}]", elementName);
        try {
            element.isClickable();
            logger.info("Element [{}] is clickable", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", elementName);
            return false;
        }
    }

//    public void waitUntilPageIsReady() {
//        logger.info("Waiting for the page to load");
//        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        pageWait.until(driver ->
//                ((JavascriptExecutor) driver)
//                        .executeScript("return document.readyState")
//                        .equals("complete")
//        );
//
//        pageWait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
//        pageWait.until(ExpectedConditions.visibilityOfElementLocated(getPageLoadedIndicator()));
//        logger.info("The page is ready");
//    }

    protected void scrollTo(ExtendedWebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    /**
     * Pauses using Thread sleep. Use only for debug code, not for test implementation.
     *
     * @param milliseconds int number of milliseconds to pause     *
     */
    public void debugPause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAlertPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
