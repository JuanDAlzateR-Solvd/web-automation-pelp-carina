package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.flows.ShoppingFlow;
import com.solvd.carina.webAutomation.pages.common.HomePageCategory;
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

import static com.solvd.carina.webAutomation.pages.common.HomePageCategory.LAPTOPS;

public class CatalogTest extends BaseTest {
    private static final Logger logger =
            LoggerFactory.getLogger(CatalogTest.class);

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {
        HomePage homePage = openHomePage();

        ProductGrid productGrid = homePage.selectCategory(LAPTOPS);

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");
    }

    @Test(testName = "Product Search by Category - Task3 TC-001",
            description = "filters the products by a category, then verifies info from the last product of last page",
            dataProvider = "Category MenuItem Provider")
    public void verifyLastProductInfoForCategory(HomePageCategory category) {
        HomePage homePage = openHomePage();

        ProductPage productPage = homePage
                .selectCategory(category)
                .openLastProduct();

        Assert.assertTrue(productPage.isInfoVisible(), "Product Page should have all info visible");
    }

    //Data Providers
    @DataProvider(name = "Category MenuItem Provider")
    public Object[][] homePageMenuItem() {
        return Arrays.stream(HomePageCategory.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);
    }

}
