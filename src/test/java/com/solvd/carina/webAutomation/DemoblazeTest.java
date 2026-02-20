package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.*;
import com.solvd.carina.webAutomation.components.ProductGrid;
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

    @Test(testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void verifyTopMenuNavigation() {
        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);

        homePage.open();
//        homePage.waitUntilPageIsReady();
        SoftAssert sa = new SoftAssert();

        Arrays.stream(TopMenu.MenuItem.values())
                .forEach(menuItem -> {
                    topMenu.isVisible(menuItem);
                    topMenu.clickButton(menuItem);
                    sa.assertTrue(topMenu.isVisible(menuItem));
                    topMenu.clickCloseButton(menuItem);
                    homePage.waitUntilPageIsReady();
                });

        sa.assertAll();
    }

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        homePage.open();

        homePage.clickButton(HomePage.MenuItem.LAPTOPS);

        homePage.waitUntilPageIsReady();

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");

    }

}
