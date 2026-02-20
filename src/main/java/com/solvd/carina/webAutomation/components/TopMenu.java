package com.solvd.carina.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class TopMenu extends BaseComponent {

    @FindBy(css = "a.nav-link[href='index.html']")
    private ExtendedWebElement homeButton;

    @FindBy(css = "a.nav-link[data-target='#exampleModal']")
    private ExtendedWebElement contactButton;

    @FindBy(css = "a.nav-link[data-target='#videoModal']")
    private ExtendedWebElement aboutUsButton;

    @FindBy(css = "a.nav-link[href='cart.html']")
    private ExtendedWebElement cartButton;

    @FindBy(css = "a.nav-link#login2")
    private ExtendedWebElement logInButton;

    @FindBy(css = "a.nav-link#signin2")
    private ExtendedWebElement signUpButton;

    @FindBy(css = "#exampleModal .close")
    private ExtendedWebElement contactCloseButton;

    @FindBy(css = "#videoModal .close")
    private ExtendedWebElement aboutUsCloseButton;

    @FindBy(css = "#logInModal .close")
    private ExtendedWebElement logInCloseButton;

    @FindBy(css = "#signInModal .close")
    private ExtendedWebElement signUpCloseButton;

    @FindBy(css = "#nava img")
    private ExtendedWebElement imageLocator;

    public TopMenu(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, ExtendedWebElement> menuButtons = Map.of(
            MenuItem.HOME, homeButton,
            MenuItem.CONTACT, contactButton,
            MenuItem.ABOUT_US, aboutUsButton,
            MenuItem.CART, cartButton,
            MenuItem.LOG_IN, logInButton,
            MenuItem.SIGN_UP, signUpButton
    );

    private final Map<MenuItem, ExtendedWebElement> closeButtons = Map.of(
            MenuItem.CONTACT, contactCloseButton,
            MenuItem.ABOUT_US, aboutUsCloseButton,
            MenuItem.LOG_IN, logInCloseButton,
            MenuItem.SIGN_UP, signUpCloseButton
    );

    @Override
    protected ExtendedWebElement getPageLoadedIndicator() {
        return imageLocator;
    }

    public void clickButton(MenuItem item) {
        click(menuButtons.get(item), item.name);
    }

    public void clickCloseButton(MenuItem item) {
        if (closeButtons.containsKey(item)) {
            click(closeButtons.get(item),
                    item.name.substring(4) + " Close Button");
        }
    }

    public boolean isVisible(MenuItem item) {
        boolean result = false;

        if (closeButtons.containsKey(item)) {
            result = closeButtons.get(item).isVisible(10);
        } else {
            switch (item) {
                case HOME -> result = driver.getCurrentUrl().contains("index.html");
                case CART -> result = driver.getCurrentUrl().contains("cart.html");
            }
        }

        return result;
    }

    public enum MenuItem {
        HOME("Top Menu Home"),
        CONTACT("Top Menu Contact"),
        ABOUT_US("Top Menu About Us"),
        CART("Top Menu Cart"),
        LOG_IN("Top Menu Log In"),
        SIGN_UP("Top Menu Sign Up");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
