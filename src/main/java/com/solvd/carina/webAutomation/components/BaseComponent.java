package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.actions.UIActions;
import com.solvd.carina.webAutomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent extends AbstractUIObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final WaitUtil waitUtil;

    protected final UIActions uIActions;

    protected BaseComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.waitUtil = new WaitUtil(getDriver());
        this.uIActions = new UIActions(getDriver());
    }

    public boolean isComponentVisible() {
        return getUiLoadedMarker().isVisible();
    }

}
