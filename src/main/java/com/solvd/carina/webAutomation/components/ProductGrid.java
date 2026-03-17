package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.pages.desktop.ProductPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductGrid extends BaseComponent {

    @FindBy(css = ".pagination #next2")
    private ExtendedWebElement nextButton;

    @FindBy(css = ".col-lg-4")
    private List<ProductGridItemComponent> productItems;

    @FindBy(css = ".card-img-top.img-fluid")
    private List<ExtendedWebElement> imageIndicator;

    public ProductGrid(WebDriver driver, SearchContext root) {
        super(driver, root);
//        setUiLoadedMarker(productItems.get(0).getRootExtendedElement()); //works without set marker
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

    public ProductGridItemComponent getProduct(int productIndex) {

        int size = productItems.size();

        if (productIndex < 0 || productIndex >= size) {
            throw new IllegalArgumentException(
                    "Product index " + productIndex + " is out of bounds. Grid size: " + size);
        }

        logger.debug("Getting product {} from grid", productIndex);

        return productItems.get(productIndex);
    }

    //Test flow methods

    public ProductPage openProduct(int index) {
        ProductGridItemComponent product = getProduct(index);
        logger.info("Opening product {} from product grid", index);
        product.clickProduct();
        return new ProductPage(driver);
    }

    public ProductPage openLastProduct() {
        int productIndex = getProductCount() - 1;
       return openProduct(productIndex);
    }

    public ProductPage addProductToCart(int index) {
        ProductPage productPage=openProduct(index);
        productPage.waitUntilPageIsReady();
        return productPage.addToCart();
    }

    public String getProductName(int index) {
        ProductGridItemComponent product = getProduct(index);
        return product.getProductName();
    }

    public int getProductCount() {
        return productItems.size();
    }




}
