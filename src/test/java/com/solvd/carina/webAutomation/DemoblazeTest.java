package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.*;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.flows.ShoppingFlow;
import com.solvd.carina.webAutomation.navigation.PageNavigator;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.pages.desktop.ProductPage;
import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

    @Test(testName = "Product Search by Category - Task3 TC-001",
            description = "filters the products by a category, then verifies info from the last product of last page",
            dataProvider = "Category MenuItem Provider")
    public void verifyLastProductInfoForCategory(HomePage.Category category) {
        WebDriver driver = getDriver();

        HomePage homePage = PageNavigator.openHomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(category);

        int productIndex = productGrid.getProductCount() - 1;

        ProductPage productPage = productGrid
                .openProductByIndex(productIndex);

        Assert.assertTrue(productPage.isInfoVisible(), "Product Page should have all info visible");

    }

    @Test(testName = "Add Product to Cart - Task3 TC-002",
            description = "choose the first product from a category and add it to cart, then verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyAddFirstProductToCart(HomePage.Category category) {
        WebDriver driver = getDriver();

        HomePage homePage = PageNavigator.openHomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductNameByIndex(0);

        CartPage cartPage = productGrid
                .openProductByIndex(0)
                .addToCart()
                .getTopMenu()
                .goToCartPage();

        cartPage.waitUntilCartShowsProducts();

        Assert.assertTrue(cartPage.containsProduct(productName), "Product was not added to cart");

        Assert.assertFalse(cartPage.getTotalPrice().isEmpty(), "Total price is empty");

    }

    @Test(testName = "Delete Product from Cart - Task3 TC-003",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyDeleteProductFromCart(HomePage.Category category) {
        WebDriver driver = getDriver();
        HomePage homePage = PageNavigator.openHomePage(driver);
        SoftAssert sa = new SoftAssert();

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductNameByIndex(0);

        CartPage cartPage = productGrid
                .openProductByIndex(0)
                .addToCart()
                .getTopMenu()
                .goToCartPage();

        cartPage.waitUntilCartShowsProducts();

        sa.assertTrue(cartPage.containsProduct(productName),
                "Product was not added to cart");

        int initialSize = cartPage.getProductCount();

        cartPage.deleteProduct(productName);

        sa.assertEquals(
                cartPage.getProductCount(),
                initialSize - 1,
                "Product was not deleted"
        );

        sa.assertAll();
    }

    @Test(testName = "Empty Shopping Cart - Task3 TC-004",
            description = "add random products to the shopping cart, then empties the cart")
    public void verifyAllDeleteButtonsToEmptyShoppingCart() {
        WebDriver driver = getDriver();

        HomePage homePage = PageNavigator.openHomePage(driver);
        ShoppingFlow shoppingFlow = new ShoppingFlow(driver);

        List<String> productNames = shoppingFlow.addRandomProductsToCart(5);

        SoftAssert sa = new SoftAssert();
        CartPage cartPage = homePage.getTopMenu().goToCartPage();

        cartPage.waitUntilCartShowsProducts();

        int initialSize = cartPage.getProductCount();

        sa.assertFalse(initialSize == 0, "The shopping cart is empty");

        cartPage.emptyShoppingCart();
        logger.debug("finished empty shopping cart");

        int finalSize = cartPage.getProductCount();
        sa.assertTrue(finalSize == 0, "The shopping cart is not empty");
        logger.debug("finished checking shopping cart");

        sa.assertAll();
    }

    //Data Providers
    @DataProvider(name = "Category MenuItem Provider")
    public Object[][] homePageMenuItem() {
        return Arrays.stream(HomePage.Category.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "TopMenu Modal MenuItem Provider")
    public Object[][] modalMenuItem() {
        return new Object[][]{
                {TopMenu.MenuItem.CONTACT},
                {TopMenu.MenuItem.ABOUT_US},
                {TopMenu.MenuItem.LOG_IN},
                {TopMenu.MenuItem.SIGN_UP}
        };
    }
}
