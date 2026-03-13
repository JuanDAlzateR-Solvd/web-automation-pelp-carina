package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    }

    @Override
    public ExtendedWebElement getComponentLoadedIndicator() {
        return productTitle;
    }

    private ExtendedWebElement getProductTitle() {

        return productTitle;
    }

    public String getText() {

        return getRootExtendedElement().getText(); //It doesn't work with root?  used title?.
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
        waitUntilComponentIsReady();
        for (int i = 0; i < 5; i++) {
            if (!getProductTitle().isClickable()) {
                logger.debug("Product is not clickable, trying again");
                getProductTitle().assertElementPresent(2);
            }
        }
    }

}
