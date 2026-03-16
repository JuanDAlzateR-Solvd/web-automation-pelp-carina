package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.flows.ShoppingFlow;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.pages.desktop.ProductPage;
import com.zebrunner.carina.core.AbstractTest;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class HomeNavigationTest extends AbstractTest {//implements IAbstractTest
    private static final Logger logger =
            LoggerFactory.getLogger(HomeNavigationTest.class);

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) {
        R.CONFIG.put("browser", browser);
        logger.info("Running test on browser: {}", browser);
    }

    @Test(testName = "Functionality of top menu modals", description = "verifies that home page loads, and top Menu modals works correctly")
    public void verifyTopMenuNavigation() {
        WebDriver driver = getDriver();
        HomePage homePage = Navigation.openHomePage(driver);

        Navigation navigation = homePage.getNavigation();
        SoftAssert sa = new SoftAssert();

        logger.info("Testing Menu item: [Contact Modal]");
        ContactModal contactModal = navigation.openContactModal();
        sa.assertTrue(contactModal.isModalVisible(), "Contact Modal should be visible");
        contactModal.closeModal();

        logger.info("Testing Menu item: [About Us Modal]");
        AboutUsModal aboutUsModal = navigation.openAboutUsModal();
        sa.assertTrue(aboutUsModal.isModalVisible(), "About Us Modal should be visible");
        aboutUsModal.closeModal();

        logger.info("Testing Menu item: [Log In Modal]");
        LogInModal logInModal = navigation.openLogInModal();
        sa.assertTrue(logInModal.isModalVisible(), "Log In Modal should be visible");
        logInModal.closeModal();

        logger.info("Testing Menu item: [Sign Up Modal]");
        SignUpModal signUpModal = navigation.openSignUpModal();
        sa.assertTrue(signUpModal.isModalVisible(), "Sign Up Modal should be visible");
        signUpModal.closeModal();

        logger.info("Testing Menu item: [CartPage]");
        CartPage cartPage = navigation.goToCartPage();
        sa.assertTrue(cartPage.isPageVisible(), "Cart Page should be visible");
        cartPage.getNavigation().goToHomePage();

        sa.assertAll();
    }

    @Test(testName = "VerifyFooterInfo- Task3 TC-007",
            description = "verifies footer visibility and contact info on the home page")
    public void verifyFooterVisibilityAndInfo() {
        WebDriver driver = getDriver();

        HomePage homePage = Navigation.openHomePage(driver);
        Footer footer = homePage.getFooter();

        SoftAssert sa = new SoftAssert();

        sa.assertFalse(footer.isVisibleInScreen(), "Footer is visible in screen after load home page");

        footer.ensureVisible();

        sa.assertTrue(footer.isVisibleInScreen(), "Footer is not visible in screen at bottom of page");
        sa.assertTrue(footer.verifyFooterInfo(), "Footer info is not completely visible");

        sa.assertAll();
    }

}
