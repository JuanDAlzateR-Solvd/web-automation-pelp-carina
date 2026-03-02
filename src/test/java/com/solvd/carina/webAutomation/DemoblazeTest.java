package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.*;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.navigation.PageNavigator;
import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class DemoblazeTest implements IAbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);

    @Test(testName = "Functionality of top menu modals", description = "verifies that home page loads, and top Menu modals works correctly")
    public void verifyTopMenuNavigation() {
        WebDriver driver = getDriver();
        HomePage homePage = PageNavigator.openHomePage(driver);

        TopMenu topMenu = homePage.getTopMenu();
        SoftAssert sa = new SoftAssert();

        logger.info("Testing Menu item: [Contact Modal]");
        ContactModal contactModal = topMenu.openContactModal();
        sa.assertTrue(contactModal.isModalVisible(), "Contact Modal should be visible");
        contactModal.closeModal();

        logger.info("Testing Menu item: [About Us Modal]");
        AboutUsModal aboutUsModal = topMenu.openAboutUsModal();
        sa.assertTrue(aboutUsModal.isModalVisible(), "About Us Modal should be visible");
        aboutUsModal.closeModal();

        logger.info("Testing Menu item: [Log In Modal]");
        LogInModal logInModal = topMenu.openLogInModal();
        sa.assertTrue(logInModal.isModalVisible(), "Log In Modal should be visible");
        logInModal.closeModal();

        logger.info("Testing Menu item: [Sign Up Modal]");
        SignUpModal signUpModal = topMenu.openSignUpModal();
        sa.assertTrue(signUpModal.isModalVisible(), "Sign Up Modal should be visible");
        signUpModal.closeModal();

        logger.info("Testing Menu item: [CartPage]");
        CartPage cartPage = topMenu.goToCartPage();
        sa.assertTrue(cartPage.isPageVisible(), "Cart Page should be visible");
        topMenu.goToHomePage();

        sa.assertAll();
    }


    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {
        WebDriver driver = getDriver();
        HomePage homePage = PageNavigator.openHomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(HomePage.Category.LAPTOPS);

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");
    }

}
