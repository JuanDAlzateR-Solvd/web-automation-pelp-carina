package com.solvd.carina.webAutomation.pages.common;

import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class BaseTopMenuPage extends BasePage {

    @FindBy(tagName="body")
    protected ExtendedWebElement body;

    protected BaseTopMenuPage(WebDriver driver) {
        super(driver);
    }

    protected abstract TopMenu getTopMenu();

    public Navigation getNavigation() {
        return new Navigation(driver, getTopMenu());
    }

}
