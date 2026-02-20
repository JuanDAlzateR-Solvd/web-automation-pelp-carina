package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ProductGrid extends BaseComponent {

    @FindBy(css = "#tbodyid")
    private ExtendedWebElement productGridContainer;

    @FindBy(css = ".pagination #next2")
    private ExtendedWebElement nextButton;

    @FindBy(css = "#tbodyid .card-title")
    private List<ExtendedWebElement> productElements;

    @FindBy(css = "#tbodyid .card-img-top")
    private List<ExtendedWebElement> imageLocator;


    public ProductGrid(WebDriver driver) {
        super(driver);
    }

    protected ExtendedWebElement getComponentLoadedIndicator() {
        return imageLocator.get(0);
    }

    public List<ExtendedWebElement> getProductElements() {
        return productElements;
    }

    public List<String> getProductTitles() {
        List<String> productsList = new ArrayList<>();
        for (ExtendedWebElement product : getProductElements()) {
            productsList.add(product.getText());
        }
        return productsList;
    }

    public boolean nextButtonIsClickable() {
        return nextButton.isClickable();
    }

    public void clickNextButton() {
        click(nextButton, "Next Button");
    }

    public void clickNextButtonIfPossible(HomePage.MenuItem category) {
        if (nextButtonIsClickable() && category != HomePage.MenuItem.MONITORS) {
            //demoblaze.com has a bug, when click on category monitors it shows the next button, even thought it shouldn't.
            clickNextButton();
        }
    }

    public String getTextOf(ExtendedWebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product, productName);
    }

    public void clickProduct(ExtendedWebElement product) {
        String productName = getProductName(product);
        click(product, productName);
    }

    public String getProductName(ExtendedWebElement product) {
        return getText(product).split("\n")[0];
    }

    public ExtendedWebElement getProductGridContainer() {
        return productGridContainer;
    }

    public ExtendedWebElement getProductByIndex(int productIndex) {
        List<ExtendedWebElement> products = getProductElements();
        ExtendedWebElement product = products.get(productIndex);
        logger.info(getTextOf(product));
        return product;
    }

}
