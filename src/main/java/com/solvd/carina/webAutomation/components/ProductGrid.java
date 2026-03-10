package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.pages.desktop.ProductPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class ProductGrid extends BaseComponent {

    @FindBy(css = ".pagination #next2")
    private ExtendedWebElement nextButton;

    @FindBy(css = ".col-lg-4")
    private List<ExtendedWebElement> productElements;

    @FindBy(css = ".card-img-top.img-fluid")
    private List<ExtendedWebElement> imageIndicator;

    public ProductGrid(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected ExtendedWebElement getComponentLoadedIndicator() {

        By locator = By.cssSelector(".card-img-top.img-fluid");
        waitUtil.waitForNumberOfElementsToBeMoreThan(locator, 0);

        return  findExtendedWebElements(locator).get(0);
    }

    public List<ProductGridItemComponent> getProductComponents() {

        logger.debug("Getting product components: found {} products", productElements.size());
        return productElements.stream()
                .map(el -> new ProductGridItemComponent(driver, el.getElement()))
                .toList();
    }

    public List<ExtendedWebElement> getProductElements() {
        return productElements;
    }

    public List<String> getProductTitles() {
        waitUntilComponentIsReady();

        List<ExtendedWebElement> titles =
                findExtendedWebElements(By.cssSelector(".card-title"));

        logger.info("Getting {} product titles", titles.size());

        return titles.stream()
                .map(ExtendedWebElement::getText)
                .toList();

    }

    public boolean isNextButtonClickable() {
        return nextButton.isElementPresent() && nextButton.isClickable();
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void clickNextButtonIfPossible(HomePage.Category category) {
        if (isNextButtonClickable() && category != HomePage.Category.MONITORS) {
            // Monitors category is expected to be a single page without pagination, so do not use the Next button for it.
            clickNextButton();
        }
    }

    public ExtendedWebElement getProductGridContainer() {
        return getRootExtendedElement();
    }

//    public ProductGridItemComponent getProductByIndex(int productIndex) {
//        List<ProductGridItemComponent> products = getProductComponents();
//        ProductGridItemComponent product = products.get(productIndex);
//        logger.debug("Getting product {} from product grid", productIndex);
//        return product;
//    }

    public ProductGridItemComponent getProductByIndex(int productIndex) {
        waitUntilComponentIsReady();

        List<ProductGridItemComponent> products = getProductComponents();
        int size = products.size();

        if (productIndex < 0 || productIndex >= size) {
            throw new IllegalArgumentException(
                    "Product index " + productIndex + " is out of bounds. Grid size: " + size);
        }

        logger.debug("Getting product {} from grid", productIndex);

        return products.get(productIndex);
    }

    //Test flow methods

    public ProductPage openProductByIndex(int index) {
        waitUntilComponentIsReady();
        ProductGridItemComponent product = getProductByIndex(index);
        logger.info("Opening product {} from product grid", index);
        product.clickProduct();
        return new ProductPage(driver);
    }

    public String getProductNameByIndex(int index) {
        ProductGridItemComponent product = getProductByIndex(index);
        return product.getProductName();
    }

    public int getProductCount() {
        waitUntilComponentIsReady();
        logger.info("Checking number of products in product grid");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<ExtendedWebElement> rows =
                findExtendedWebElements(By.cssSelector("#tbodyid .card-title"));

        int size = rows.size();
        logger.info("Product grid has {} products", size);

        return size;
    }

}
