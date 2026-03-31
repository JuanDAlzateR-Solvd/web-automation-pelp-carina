package com.solvd.carina.webautomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(css = "a[id='nava'] img")
    private ExtendedWebElement imageIndicator;

    public TopMenu(WebDriver driver, SearchContext root) {
        super(driver, root);
        setUiLoadedMarker(imageIndicator);
    }

    private void jsClick(ExtendedWebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element.getElement());
    }

    private void clickMenuItem(ExtendedWebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            logger.warn("Regular click failed, using JS click for {}", element, e);
            jsClick(element);
        }
    }

    public void clickCart() {
        clickMenuItem(cartButton);
    }

    public void clickHome() {
        clickMenuItem(homeButton);
    }

    public void clickAboutUs() {
        clickMenuItem(aboutUsButton);
    }

    public void clickSignUp() {
        clickMenuItem(signUpButton);
    }

    public void clickContact() {
        clickMenuItem(contactButton);
    }

    public void clickLogIn() {
        clickMenuItem(logInButton);
    }

}
