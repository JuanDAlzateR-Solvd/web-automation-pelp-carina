package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent extends AbstractUIObject {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public BaseComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
    public BaseComponent(WebDriver driver) {
        super(driver);
    }

    public void click(ExtendedWebElement element,String elementName) {
        logger.info("Clicking on element [{}]", elementName);
        element.click();
    }

    public String getText(ExtendedWebElement element,String elementName) {
        logger.info("Getting text from element [{}]", elementName);
        return element.getText();
    }
    public String getText(ExtendedWebElement element) {
        logger.info("Getting text from element [{}]", element.getName());
        return element.getText();
    }
}
