package com.solvd.carina.webautomation;

import com.solvd.carina.webautomation.components.Footer;
import com.solvd.carina.webautomation.components.modals.*;
import com.solvd.carina.webautomation.components.modals.common.BaseModal;
import com.solvd.carina.webautomation.pages.common.HomePageBase;
import com.solvd.carina.webautomation.pages.desktop.CartPage;
import com.solvd.carina.webautomation.wait.Timeouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class HomeNavigationTest extends BaseTest {
    private static final Logger logger =
            LoggerFactory.getLogger(HomeNavigationTest.class);

    @Test(testName = "Functionality of top menu modals", description = "verifies that home page loads, and top Menu modals works correctly")
    public void verifyTopMenuNavigation() {
        HomePageBase homePage = openHomePage();
        SoftAssert sa = new SoftAssert();

        softAssertOpenAndCloseModal(homePage.openContactModal(), sa);

        softAssertOpenAndCloseModal(homePage.openAboutUsModal(), sa);

        softAssertOpenAndCloseModal(homePage.openLogInModal(), sa);

        softAssertOpenAndCloseModal(homePage.openSignUpModal(), sa);

        logger.info("Testing Menu item: [CartPage]");
        CartPage cartPage = homePage.goToCartPage();
        sa.assertTrue(cartPage.isPageOpened(), "Cart Page should be visible");
        cartPage.goToHomePage();

        sa.assertAll();
    }

    @Test(testName = "VerifyFooterInfo- Task3 TC-007",
            description = "verifies footer visibility and contact info on the home page")
    public void verifyFooterVisibilityAndInfo() {
        HomePageBase homePage = openHomePage();

        Footer footer = homePage.getFooter();

        SoftAssert sa = new SoftAssert();

        sa.assertFalse(footer.isVisibleInScreen(), "Footer is visible in screen after load home page");

        footer.ensureVisible();

        sa.assertTrue(footer.isVisibleInScreen(), "Footer is not visible in screen at bottom of page");
        sa.assertTrue(footer.isFooterInfoValid(), "Footer info is not completely visible");

        sa.assertAll();
    }


    @Test(testName = "Functionality of contact modal", description = "verifies that home page loads, and contact modal opens and closes correctly")
    public void verifyContactModalNavigation() {
        HomePageBase homePage = openHomePage();
        SoftAssert sa = new SoftAssert();

        ContactModal contactModal = (ContactModal) softAssertOpenAndCloseModal(homePage.openContactModal(), sa);

        sa.assertFalse(contactModal.isModalOpened(Timeouts.SHORT), "Contact Modal should not be opened after closing");
        sa.assertAll();
    }

    @Test(testName = "Functionality of log in modal", description = "verifies that home page loads, and log in modal opens and closes correctly")
    public void verifyLogInModalNavigation() {
        HomePageBase homePage = openHomePage();
        SoftAssert sa = new SoftAssert();

        LogInModal logInModal = (LogInModal) softAssertOpenAndCloseModal(homePage.openLogInModal(), sa);

        sa.assertFalse(logInModal.isModalOpened(Timeouts.SHORT), "Log In Modal should not be opened after closing");
        sa.assertAll();
    }

    public BaseModal softAssertOpenAndCloseModal(BaseModal modal, SoftAssert sa) {
        String modalName = modal.getClass().getSimpleName();
        logger.info("Testing Menu item: [{}]", modalName);

        sa.assertTrue(modal.isModalVisible(), modalName + " should be visible");
        modal.closeModal();
        return modal;
    }

}
