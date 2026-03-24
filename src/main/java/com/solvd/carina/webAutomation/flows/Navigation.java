package com.solvd.carina.webAutomation.flows;

import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import com.zebrunner.carina.utils.mobile.IMobileUtils;

public class Navigation implements IAbstractTest, IMobileUtils {

    private final WebDriver driver;
    private final TopMenu topMenu;

    public Navigation(WebDriver driver, TopMenu topMenu) {
        this.driver = driver;
        this.topMenu = topMenu;
    }

    public LogInModal openLogInModal() {
        topMenu.clickLogIn();

        LogInModal modal = new LogInModal(driver, driver);
        modal.waitUntilModalOpened();

        return modal;
    }

    public SignUpModal openSignUpModal() {
        topMenu.clickSignUp();

        SignUpModal modal = new SignUpModal(driver, driver);
        modal.waitUntilModalOpened();

        return modal;
    }

    public ContactModal openContactModal() {
        topMenu.clickContact();

        ContactModal modal = new ContactModal(driver, driver);
        modal.waitUntilModalOpened();

        return modal;
    }

    public AboutUsModal openAboutUsModal() {
        topMenu.clickAboutUs();

        AboutUsModal modal = new AboutUsModal(driver, driver);
        modal.waitUntilModalOpened();

        return modal;
    }

    public CartPage goToCartPage() {
        topMenu.clickCart();

        return new CartPage(driver);
    }

    public HomePage goToHomePage() {
        topMenu.clickHome();

        return new HomePage(driver);
    }

    // ==========================
    // Static methods
    // ==========================

    public static HomePage openHomePage(WebDriver driver) {

        HomePage page = new HomePage(driver);
        page.open();

        return page;
    }

}
