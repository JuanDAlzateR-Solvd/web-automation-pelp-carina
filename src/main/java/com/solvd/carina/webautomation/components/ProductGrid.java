package com.solvd.carina.webautomation.components;

import com.solvd.carina.webautomation.pages.desktop.ProductPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class ProductGrid extends BaseComponent {

    @FindBy(css = ".pagination #next2")
    private ExtendedWebElement nextButton;

    @FindBy(css = ".col-lg-4")
    private List<ProductGridItemComponent> productItems;

    @FindBy(css = ".card-img-top.img-fluid")
    private List<ExtendedWebElement> imageIndicator;

    public ProductGrid(WebDriver driver, SearchContext root) {
        super(driver, root);
    }

    public List<ProductGridItemComponent> getProductComponents() {
        logger.debug("Found {} products in product grid", productItems.size());
        return productItems;
    }

    public List<String> getProductTitles() {
        return productItems.stream()
                .map(ProductGridItemComponent::getProductName)
                .toList();
    }

    public ProductGridItemComponent getProductByName(String productName) {
        return productItems.stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Product with name '" + productName + "'not found in grid"));
    }

    //Test flow methods

    public ProductPage openProduct(String productName) {
        ProductGridItemComponent product = getProductByName(productName);
        logger.info("Opening product {} from product grid", productName);
        product.clickProduct();
        return new ProductPage(driver);
    }

    public ProductPage addProductToCart(String productName) {
        ProductPage productPage = openProduct(productName);
        return productPage.addToCart();
    }

    public ProductGridItemComponent getProductRandomProduct() {
        int size = getProductCount();
        int randomIndex = new Random().nextInt(size);

        return getProductComponents().get(randomIndex);

    }

    public int getProductCount() {
        return productItems.size();
    }

    public String getFistProductName() {
        return getProductComponents().getFirst().getProductName();
    }

    public String getLastProductName() {
        return getProductComponents().getLast().getProductName();
    }

    public ProductPage openLastProduct() {
        return openProduct(getLastProductName());
    }

}
