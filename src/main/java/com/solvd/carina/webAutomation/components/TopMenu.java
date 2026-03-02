package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @FindBy(css = "a[id='nava'] img")
    private ExtendedWebElement imageIndicator;

    private final Map<MenuItem, ExtendedWebElement> menuButtons;

    private final Map<MenuItem, ExtendedWebElement> closeButtons;

    public TopMenu(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                MenuItem.HOME, homeButton,
                MenuItem.CONTACT, contactButton,
                MenuItem.ABOUT_US, aboutUsButton,
                MenuItem.CART, cartButton,
                MenuItem.LOG_IN, logInButton,
                MenuItem.SIGN_UP, signUpButton
        );
        closeButtons = Map.of(
                MenuItem.CONTACT, contactCloseButton,
                MenuItem.ABOUT_US, aboutUsCloseButton,
                MenuItem.LOG_IN, logInCloseButton,
                MenuItem.SIGN_UP, signUpCloseButton
        );
    }

    @Override
    protected ExtendedWebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public void click(MenuItem item) {
//       menuButtons.get(item).isClickable();
       menuButtons.get(item).click();
    }

    public void clickClose(MenuItem item) {
        ExtendedWebElement closeButton = closeButtons.get(item);
        if (closeButton != null) {
           closeButton.click();
        }
    }

    public boolean isVisible(MenuItem item) {
        boolean result = false;

        if (closeButtons.containsKey(item)) {
            result = closeButtons.get(item).isVisible(10);
        } else {
            switch (item) {
                case HOME -> result = new HomePage(driver).isPageVisible();
                case CART -> result = new CartPage(driver).isPageVisible();
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

    //Test flow methods
    public CartPage goToCartPage() {
        click(MenuItem.CART);
        CartPage cartPage = new CartPage(driver);
        cartPage.waitUntilPageIsReady();//just added
//        cartPage.waitUntilCartLoadsProducts();
        return cartPage;
    }

    public HomePage goToHomePage() {
        click(MenuItem.HOME);
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageIsReady();
        return homePage;
    }

    public AboutUsModal openAboutUsModal() {
        click(MenuItem.ABOUT_US);
        AboutUsModal aboutUsModal = new AboutUsModal(driver);
        aboutUsModal.waitUntilModalOpened();
        return aboutUsModal;
    }

    public SignUpModal openSignUpModal() {
        click(MenuItem.SIGN_UP);
        SignUpModal signUpModal = new SignUpModal(driver);
        signUpModal.waitUntilModalOpened();

        return signUpModal;
    }

    public ContactModal openContactModal() {
        click(MenuItem.CONTACT);
        ContactModal contactModal = new ContactModal(driver);
        contactModal.waitUntilModalOpened();
        return contactModal;
    }

    public LogInModal openLogInModal() {
        click(MenuItem.LOG_IN);
        LogInModal logInModal = new LogInModal(driver);
        logInModal.waitUntilModalOpened();
        return logInModal;
    }


}
