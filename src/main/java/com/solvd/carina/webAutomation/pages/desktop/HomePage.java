package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

public class HomePage extends BasePage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private ExtendedWebElement phonesButton;

    @FindBy(css = ".list-group a[onclick*='notebook']")
    private ExtendedWebElement laptopsButton;

    @FindBy(css = ".list-group a[onclick*='monitor']")
    private ExtendedWebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top")
    private List<ExtendedWebElement> imageLocator;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, ExtendedWebElement> menuButtons = Map.of(
            MenuItem.PHONES, phonesButton,
            MenuItem.LAPTOPS, laptopsButton,
            MenuItem.MONITORS, monitorsButton
    );

    protected ExtendedWebElement getPageLoadedIndicator() {
        return imageLocator.get(0);
    }

    public void printLoc() {

        for (ExtendedWebElement img : imageLocator) {
            logger.info("Class: [{}]", img.getAttribute("class"));
        }

    } //#tbodyid .card-img-top.img-fluid

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