package com.solvd.carina.webAutomation.pages.common;

import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.wait.LoaderHandler;
import com.solvd.carina.webAutomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTopMenuPage extends BasePage {

    @FindBy(tagName="body")
    protected ExtendedWebElement body;

    protected BaseTopMenuPage(WebDriver driver) {
        super(driver);

    }

    protected abstract WebElement getTopMenuContainer();

    protected TopMenu getTopMenu() {
        return new TopMenu(driver, getTopMenuContainer());
    }

    public Navigation getNavigation() {
        Navigation navigation = new Navigation(driver, getTopMenu());
        return navigation;
    }
}