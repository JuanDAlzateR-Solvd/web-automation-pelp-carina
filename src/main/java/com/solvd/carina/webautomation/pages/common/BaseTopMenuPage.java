package com.solvd.carina.webautomation.pages.common;

import com.solvd.carina.webautomation.components.TopMenu;
import com.solvd.carina.webautomation.components.modals.AboutUsModal;
import com.solvd.carina.webautomation.components.modals.ContactModal;
import com.solvd.carina.webautomation.components.modals.LogInModal;
import com.solvd.carina.webautomation.components.modals.SignUpModal;
import com.solvd.carina.webautomation.flows.Navigation;
import com.solvd.carina.webautomation.pages.desktop.CartPage;
import com.solvd.carina.webautomation.pages.desktop.HomePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class BaseTopMenuPage extends BasePage {

    @FindBy(tagName = "body")
    protected ExtendedWebElement body;

    protected BaseTopMenuPage(WebDriver driver) {
        super(driver);
    }

    protected abstract TopMenu getTopMenu();

    private Navigation getNavigation() {
        return new Navigation(driver, getTopMenu());
    }

    public LogInModal openLogInModal() {
        return getNavigation().openLogInModal();
    }

    public SignUpModal openSignUpModal() {
        return getNavigation().openSignUpModal();
    }

    public ContactModal openContactModal() {
        return getNavigation().openContactModal();
    }

    public AboutUsModal openAboutUsModal() {
        return getNavigation().openAboutUsModal();
    }

    public CartPage goToCartPage() {
        return getNavigation().goToCartPage();
    }

    public HomePage goToHomePage() {
        return getNavigation().goToHomePage();
    }

}
