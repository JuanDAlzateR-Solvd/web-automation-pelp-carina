package com.solvd.carina.webautomation.flows;

import com.solvd.carina.webautomation.components.TopMenu;
import com.solvd.carina.webautomation.components.modals.AboutUsModal;
import com.solvd.carina.webautomation.components.modals.ContactModal;
import com.solvd.carina.webautomation.components.modals.LogInModal;
import com.solvd.carina.webautomation.components.modals.SignUpModal;
import com.solvd.carina.webautomation.pages.desktop.CartPage;
import com.solvd.carina.webautomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Navigation implements IAbstractTest, IMobileUtils {
    protected static final Logger logger = LoggerFactory.getLogger(Navigation.class);

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
