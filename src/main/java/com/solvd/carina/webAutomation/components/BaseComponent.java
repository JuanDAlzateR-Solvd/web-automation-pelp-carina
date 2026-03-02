package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent extends AbstractUIObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WaitUtil waitUtil;

    protected BaseComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.waitUtil=new WaitUtil(getDriver());
    }

    protected BaseComponent(WebDriver driver) {
        super(driver);
        this.waitUtil=new WaitUtil(getDriver());
    }

    /**
     * Element that indicates the component is fully loaded
     */
    protected abstract ExtendedWebElement getComponentLoadedIndicator();

    public void waitUntilComponentIsReady() {
        logger.debug("Waiting for component [{}] to be ready", getClass().getSimpleName());
//        getComponentLoadedIndicator().isVisible(5);
    }

    public boolean isComponentVisible() {
        return getComponentLoadedIndicator().isVisible();
    }

}