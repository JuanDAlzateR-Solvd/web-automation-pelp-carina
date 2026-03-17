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

public class CartTest extends BaseTest {//implements IAbstractTest
    private static final Logger logger =
            LoggerFactory.getLogger(CartTest.class);

    @Test(testName = "Add Product to Cart - Task3 TC-002",
            description = "choose the first product from a category and add it to cart, then verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyAddFirstProductToCart(HomePage.Category category) {
        HomePage homePage = openHomePage();

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductName(0);

        CartPage cartPage =  productGrid.addProductToCart(0).goToCartPage();

        cartPage.waitUntilCartShowsProducts();

        Assert.assertTrue(cartPage.containsProduct(productName), "Product was not added to cart");

        Assert.assertFalse(cartPage.getTotalPrice().isEmpty(), "Total price is empty");

    }

    @Test(testName = "Delete Product from Cart - Task3 TC-003",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyDeleteProductFromCart(HomePage.Category category) {
        HomePage homePage = openHomePage();
        SoftAssert sa = new SoftAssert();

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductName(0);

        CartPage cartPage =  productGrid.addProductToCart(0).goToCartPage();

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
        HomePage homePage = openHomePage();
        ShoppingFlow shoppingFlow = new ShoppingFlow(getDriver());

        shoppingFlow.addRandomProductsToCart(5);

        SoftAssert sa = new SoftAssert();

        CartPage cartPage = shoppingFlow.goToCartPage();

        cartPage.waitUntilCartShowsProducts();
        int initialSize = cartPage.getProductCount();
        sa.assertTrue(initialSize > 0, "Expected cart to have products, but it was empty");

        cartPage.emptyShoppingCart();

        int finalSize = cartPage.getProductCount();
        sa.assertEquals(finalSize, 0, "Expected cart to be empty");

        sa.assertAll();
    }

    //Data Providers
    @DataProvider(name = "Category MenuItem Provider")
    public Object[][] homePageMenuItem() {
        return Arrays.stream(HomePage.Category.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);
    }

}
