package com.solvd.carina.webautomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductGridItemComponent extends BaseComponent {

    @FindBy(css = ".card-title")
    private ExtendedWebElement productTitle;

    @FindBy(css = ".card-title a")
    private ExtendedWebElement productLink;

    @FindBy(css = "h5")
    private ExtendedWebElement productPrice;

    @FindBy(css = ".card-text")
    private ExtendedWebElement productDescription;

    @FindBy(css = ".card-img-top")
    private ExtendedWebElement imageIndicator;

    public ProductGridItemComponent(WebDriver driver, SearchContext root) {
        super(driver, root);
        setUiLoadedMarker(productTitle);
    }

    private ExtendedWebElement getProductTitle() {
        return productTitle;
    }

    public String getText() {
        return getRootExtendedElement().getText();
    }

    public String getProductName() {
        return productTitle.getText();
    }

    public ExtendedWebElement getProductPrice() {
        return productPrice;
    }

    public ExtendedWebElement getProductDescription() {
        return productDescription;
    }

    public void clickProduct() {
        waitUntilProductIsClickable();
        productLink.click();
    }

    public void waitUntilProductIsClickable() {
        logger.debug("Waiting for product title to become clickable");
        waitUtil.waitForElementClickable(productLink.getElement(), productLink.getName());
    }

}
