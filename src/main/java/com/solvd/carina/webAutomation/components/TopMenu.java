package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
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

    public void clickCart() {
       cartButton.click();
    }

    public void clickHome() {
        homeButton.click();
    }

    public void clickAboutUs() {
        aboutUsButton.click();
    }

    public void clickSignUp() {
      signUpButton.click();
    }

    public void clickContact() {
       contactButton.click();
    }

    public void clickLogIn() {
       logInButton.click();
    }

}
