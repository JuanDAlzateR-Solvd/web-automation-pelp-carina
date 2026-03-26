package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.BaseTopMenuPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BaseTopMenuPage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private ExtendedWebElement phonesButton;

    @FindBy(css = ".list-group a[onclick*='notebook']")
    private ExtendedWebElement laptopsButton;

    @FindBy(css = ".list-group a[onclick*='monitor']")
    private ExtendedWebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")// "#tbodyid .card-img-top.img-fluid"
    private List<ExtendedWebElement> imageIndicator;

    @FindBy(id = "contcont")
    private ProductGrid productGrid;

    @FindBy(id = "fotcont")
    private Footer footer;

    @FindBy(id = "narvbarx")
    private TopMenu topMenu;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public HomePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(phonesButton);
    }

    @Override
    public void waitUntilPageIsReady() {
        logger.debug("Waiting for home page to load imageIndicator. Initial size: {}", imageIndicator.size());

        waitUntil(driver -> !imageIndicator.isEmpty(), 30);

        boolean isPresent = imageIndicator.get(0).isElementPresent();

        logger.debug("imageIndicator is {} present", isPresent ? "" : "not ");
    }

    @Override
    protected TopMenu getTopMenu() {
        return topMenu;
    }

    public void click(Category item) {
        switch (item) {
            case PHONES -> phonesButton.click();
            case LAPTOPS -> laptopsButton.click();
            case MONITORS -> monitorsButton.click();
        }
    }

    public enum Category {
        PHONES("Category Phones"),
        LAPTOPS("Category Laptops"),
        MONITORS("Category Monitors");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //Test flow methods

    public ProductGrid getProductGrid() {
        return productGrid;
    }

    public Footer getFooter() {
        return footer;
    }

    public ProductGrid selectCategory(Category item) {
        click(item);
        waitUntilPageIsReady();
        return productGrid;
    }

}
