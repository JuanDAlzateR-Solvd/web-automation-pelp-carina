package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.BaseTopMenuPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BaseTopMenuPage {

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
        logger.info("accepting 'Product Added' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public boolean isProductAddedAlertPresent() {
        logger.info("checking 'Product Added' Alert Present");
        try {
            waitUtil.waitForAlert();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
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
