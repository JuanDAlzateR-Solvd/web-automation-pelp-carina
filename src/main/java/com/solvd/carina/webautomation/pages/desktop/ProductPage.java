package com.solvd.carina.webautomation.pages.desktop;

import com.solvd.carina.webautomation.components.TopMenu;
import com.solvd.carina.webautomation.pages.common.BaseTopMenuPage;
import com.solvd.carina.webautomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BaseTopMenuPage {

    private static final String PRODUCT_ADDED_ALERT = "Product Added";

    @FindBy(css = ".item.active")
    private ExtendedWebElement image;

    @FindBy(css = "#tbodyid .name")
    private ExtendedWebElement title;

    @FindBy(css = "#tbodyid .price-container")
    private ExtendedWebElement price;

    @FindBy(css = "#tbodyid #more-information")
    private ExtendedWebElement description;

    @FindBy(css = "a.btn.btn-success.btn-lg")
    private ExtendedWebElement addToCartButton;

    @FindBy(id = "myCarousel-2")
    private ExtendedWebElement imageLocator;

    @FindBy(css = ".navbar.navbar-toggleable-md.bg-inverse")
    private TopMenu topMenu;

    public ProductPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(imageLocator);
    }

    @Override
    protected TopMenu getTopMenu() {
        return topMenu;
    }

    public boolean isVisible(InfoItem item) {
        return switch (item) {
            case IMAGE -> image.isVisible();
            case TITLE -> title.isVisible();
            case PRICE -> price.isVisible();
            case DESCRIPTION -> description.isVisible();
        };
    }

    public void clickAddToCartButton() {
        addToCartButton.click();
    }

    public void acceptProductAddedAlert() {
        uIActions.acceptAlert(PRODUCT_ADDED_ALERT, Timeouts.SHORT);
    }

    public boolean isProductAddedAlertPresent() {
        return uIActions.isAlertPresent(PRODUCT_ADDED_ALERT, Timeouts.SHORT);
    }

    public enum InfoItem {
        IMAGE("Product Image"),
        TITLE("Product Title"),
        PRICE("Product Price"),
        DESCRIPTION("Product Description");

        private final String name;

        InfoItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //Test flow methods

    public ProductPage addToCart() {
        clickAddToCartButton();
        acceptProductAddedAlert();
        return this;
    }

    public boolean isInfoVisible() {
        for (InfoItem item : InfoItem.values()) {
            if (!isVisible(item)) {
                return false;
            }
        }
        return true;
    }

}
