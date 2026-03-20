package com.solvd.carina.webAutomation.flows;

import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
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
                .goToHomePage();

        return productName;
    }

    public String addRandomProductToCart() {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();
        homePage.waitUntilPageIsReady(); //Necessary to ensure the page is fully loaded before interacting with elements

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

    private List<String> getUniqueAvailableProducts(ProductGrid productGrid, List<String> addedProducts) {
        List<String> products = getAvailableProducts(productGrid);
        return products
                .stream()
                .filter(p -> !addedProducts.contains(p))
                .toList();
    }

    public String addUniqueRandomProductToCart(List<String> addedProducts) {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();
        homePage.waitUntilPageIsReady(); //Necessary to ensure the page is fully loaded before interacting with elements

        List<String> availableProducts = getUniqueAvailableProducts(productGrid, addedProducts);

        int size = availableProducts.size();

        int randomIndex = new Random().nextInt(size);

        String productName = availableProducts.get(randomIndex);
        int gridIndex = productGrid.getProductIndex(productName);
        return addProductToCart(gridIndex);
    }

    public List<String> addUniqueRandomProductsToCart(int numberOfProducts) {
        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = homePage.getProductGrid();

        int availableProductsCount = getAvailableProducts(productGrid).size();

        if (availableProductsCount == 0 || numberOfProducts > availableProductsCount) {
            throw new RuntimeException("Not enough available products to add to cart");
        }

        List<String> productNames = new ArrayList<>();

        for (int i = 0; i < numberOfProducts; i++) {
            String addedProduct = addUniqueRandomProductToCart(productNames);
            productNames.add(addedProduct);
        }
        return productNames;
    }

    private List<String> getExcludedProducts() {
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
    private List<String> getAvailableProducts(ProductGrid productGrid) {
        List<String> excludedProducts = getExcludedProducts();
        return productGrid.getProductTitles()
                .stream()
                .filter(p -> !excludedProducts.contains(p))
                .toList();
    }

    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    public CartPage goToCartPage() {
        return getHomePage().goToCartPage();
    }

}
