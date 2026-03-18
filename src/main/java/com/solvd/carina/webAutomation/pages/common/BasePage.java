package com.solvd.carina.webAutomation.pages.common;

import com.solvd.carina.webAutomation.wait.LoaderHandler;
import com.solvd.carina.webAutomation.wait.Timeouts;
import com.solvd.carina.webAutomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

public abstract class BasePage extends AbstractPage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    protected LoaderHandler loaderHandler;

    protected final WaitUtil waitUtil;

    protected BasePage(WebDriver driver) {
        super(driver);

        this.waitUtil=new WaitUtil(getDriver());
        this.loaderHandler =
                new LoaderHandler(driver, LOADER);

        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);

        logger.debug("Page created | Thread: {} | Driver: {}",
                Thread.currentThread().threadId(),
                System.identityHashCode(driver));
    }

    public void waitUntilPageIsReady(boolean waitForLoader) {
        logger.debug("Waiting for page: {}", getClass().getSimpleName());
        if(waitForLoader){
            loaderHandler.waitForLoaderToDisappear(20);
        }
        getUiLoadedMarker().assertElementPresent(Timeouts.MEDIUM);
    }

    public void waitUntilPageIsReady() {
        waitUntilPageIsReady(false);
    }

    public void acceptAlert() {
        getDriver().switchTo().alert().accept();
    }

}
