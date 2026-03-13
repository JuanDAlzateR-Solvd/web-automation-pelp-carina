package com.solvd.carina.webAutomation.flows;

import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.utils.config.Configuration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShoppingFlow {

    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingFlow.class);

    public ShoppingFlow(WebDriver driver) {
        this.driver = driver;
    }

    public String addProductToCart(int productIndex) {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();

        String productName = productGrid.getProductName(productIndex);

        //Identified a bug specific for some products (e.g. Nexus 6).
        List<String> availableProducts = getAvailableProducts(productGrid);

        if (availableProducts.isEmpty()) {
            throw new RuntimeException("No products available to add to cart");
        }

        if (!availableProducts.contains(productName)) {
            logger.debug("Product [{}] not found in available products. Selecting first available product.", productName);
            productIndex = availableProducts.indexOf(availableProducts.get(0));
        }

        productGrid
                .openProduct(productIndex)
                .addToCart()
                .getNavigation()
                .goToHomePage();

        return productName;
    }

    public String addRandomProductToCart() {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();

        int size = productGrid.getProductCount();
        int randomIndex = new Random().nextInt(size);

        return addProductToCart(randomIndex);
    }

    public List<String> addRandomProductsToCart(int numberOfProducts) {
        List<String> productNames = new ArrayList<>();

        for (int i = 0; i < numberOfProducts; i++) {
            productNames.add(addRandomProductToCart());
        }
        return productNames;
    }

    private  List<String> getExcludedProducts() {
        String excludedProductsConfig = Configuration.get("excluded_products").orElse(null);
        List<String> excludedProducts = excludedProductsConfig != null
                ? Arrays.stream(excludedProductsConfig.split(","))
                .map(String::trim)
                .toList()
                : List.of();
        return excludedProducts;
    }


    /**
     * Retrieves a list of product titles that are available in the given product grid
     * after filtering out excluded products.
     *
     * @param productGrid the product grid containing the list of all products
     * @return a list of product titles that are not included in the excluded products list
     */
    private  List<String> getAvailableProducts(ProductGrid productGrid) {
        List<String> excludedProducts =getExcludedProducts();
        List<String> availableProducts = productGrid.getProductTitles()
                .stream()
                .filter(p -> !excludedProducts.contains(p))
                .toList();
        return availableProducts;
    }

}
