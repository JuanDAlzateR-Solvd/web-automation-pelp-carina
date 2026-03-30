package com.solvd.carina.webautomation.pages.common;

import com.solvd.carina.webautomation.actions.UIActions;
import com.solvd.carina.webautomation.wait.LoaderHandler;
import com.solvd.carina.webautomation.wait.Timeouts;
import com.solvd.carina.webautomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage extends AbstractPage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    protected LoaderHandler loaderHandler;

    protected final WaitUtil waitUtil;

    protected final UIActions uIActions;

    protected BasePage(WebDriver driver) {
        super(driver);

        this.waitUtil = new WaitUtil(getDriver());
        this.loaderHandler =
                new LoaderHandler(driver, LOADER);
        this.uIActions = new UIActions(getDriver());

        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);

        logger.debug("Page created | Thread: {} | Driver: {}",
                Thread.currentThread().getName(),
                System.identityHashCode(driver));
    }

    public void waitUntilPageIsReady(boolean waitForLoader) {
        logger.debug("Waiting for page: {}", getClass().getSimpleName());
        if (waitForLoader) {
            loaderHandler.waitForLoaderToDisappear(Timeouts.MEDIUM);
        }
        getUiLoadedMarker().assertElementPresent(Timeouts.MEDIUM);
    }

    public void waitUntilPageIsReady() {
        waitUntilPageIsReady(false);
    }

    @Override
    public void acceptAlert() {
        uIActions.acceptAlert("Browser alert", Timeouts.SHORT);
    }

}
