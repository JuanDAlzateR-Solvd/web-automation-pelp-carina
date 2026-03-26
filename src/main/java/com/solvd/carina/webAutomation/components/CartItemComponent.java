package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CartItemComponent extends BaseComponent {

    @FindBy(css = "td:nth-child(1) img")
    private ExtendedWebElement imageIndicator;

    @FindBy(css = "td:nth-child(2)")
    private ExtendedWebElement titleCell;

    @FindBy(css = "td:nth-child(3)")
    private ExtendedWebElement priceCell;

    @FindBy(css = "td:nth-child(4) a[onclick*='deleteItem']")
    private ExtendedWebElement deleteButton;

    public CartItemComponent(WebDriver driver, SearchContext root) {
        super(driver, root);
        setUiLoadedMarker(imageIndicator);
    }

    public String getTitle() {
        return titleCell.getText();
    }

    public String getPrice() {
        return priceCell.getText();
    }

    public String getRootText() {
        return this.getRootExtendedElement().getText();
    }

    public void deleteProduct() {
        deleteButton.click();
    }

}


