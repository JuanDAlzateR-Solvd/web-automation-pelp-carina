package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductGridItemComponent extends BaseComponent {

    @FindBy(css = ".card-title")
    private ExtendedWebElement labelTitle;

    @FindBy(css = "h5")
    private ExtendedWebElement price;

    @FindBy(css = ".card-text")
    private ExtendedWebElement description;

    @FindBy(css = ".card-img-top")
    private ExtendedWebElement imageIndicator;

    public ProductGridItemComponent(WebDriver driver, SearchContext root) {
        super(driver, root);
    }

    @Override
    public ExtendedWebElement getComponentLoadedIndicator() {
        return labelTitle;
    }

    public ExtendedWebElement getLabelTitle() {
        return labelTitle;
    }

    public String getText() {

        return getRootExtendedElement().getText(); //It doesn't work with root?  used title?.
    }

    public String getProductName() {
        return labelTitle.getText();
    }

    public ExtendedWebElement getPrice() {
        return price;
    }

    public ExtendedWebElement getDescription() {
        return description;
    }

    public void clickProduct() {
        waitUntilProductIsClickable();
        labelTitle.click();
    }

    public void waitUntilProductIsClickable() {
        waitUntilComponentIsReady();
        for (int i = 0; i < 5; i++) {
            if (!getLabelTitle().isClickable()) {
                logger.debug("Product is not clickable, trying again");
                getLabelTitle().assertElementPresent(2);
            }
        }
    }

}