package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class HomePage extends BasePage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private ExtendedWebElement phonesButton;
    @FindBy(css = ".list-group a[onclick*='notebook']")
    private ExtendedWebElement laptopsButton;
    @FindBy(css = ".list-group a[onclick*='monitor']")
    private ExtendedWebElement monitorsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, ExtendedWebElement> menuButtons = Map.of(
            MenuItem.PHONES, phonesButton,
            MenuItem.LAPTOPS, laptopsButton,
            MenuItem.MONITORS, monitorsButton
    );

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("#tbodyid .card-img-top.img-fluid");
    }

    public void clickButton(MenuItem item) {
        menuButtons.get(item).click();
    }

    public enum MenuItem {
        PHONES("Category Phones"),
        LAPTOPS("Category Laptops"),
        MONITORS("Category Monitors");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}