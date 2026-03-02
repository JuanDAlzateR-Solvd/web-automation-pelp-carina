package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartItemComponent extends BaseComponent {

    @FindBy(css = "a[onclick*='deleteItem']")
    private ExtendedWebElement deleteButton;

    @FindBy(css = "td") // ".card-text"
    private List<ExtendedWebElement> tableDataList;

    @FindBy(css = "img")
    private ExtendedWebElement imageIndicator;

    public CartItemComponent(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    public ExtendedWebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public String getTitle() {
        return getText(DataItem.TITLE);
    }

    public String getPrice() {
        return getText(DataItem.PRICE);
    }

    public String getText() {
        return this.getRootExtendedElement().getText();
    }

    public void deleteProduct() {
        deleteButton.click();
    }

    protected String getText(DataItem item) {
        return getElementFrom(item).getText();
    }

    protected ExtendedWebElement getElementFrom(DataItem item) {
        return tableDataList.get(item.getColumnIndex());
    }

    public enum DataItem {
        PICTURE("Product Picture", 0),
        TITLE("Product Title", 1),
        PRICE("Product Price", 2),
        DELETE("Product Delete", 3);

        private final String name;
        private final int columnIndex;

        DataItem(String name, int lineIndex) {
            this.name = name;
            this.columnIndex = lineIndex;
        }

        public String getName() {
            return name;
        }

        public int getColumnIndex() {
            return columnIndex;
        }
    }

}