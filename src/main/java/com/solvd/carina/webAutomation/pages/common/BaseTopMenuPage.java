package com.solvd.carina.webAutomation.pages.common;

import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
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
