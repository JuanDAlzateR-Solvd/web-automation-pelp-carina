package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

public class HomePage extends BasePage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private ExtendedWebElement phonesButton;

    @FindBy(css = ".list-group a[onclick*='notebook']")
    private ExtendedWebElement laptopsButton;

    @FindBy(css = ".list-group a[onclick*='monitor']")
    private ExtendedWebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")// "#tbodyid .card-img-top.img-fluid"
    private List<ExtendedWebElement> imageIndicator;

    @FindBy(css = "#contcont")
    private ExtendedWebElement productGridContainer;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    private final Map<Category, ExtendedWebElement> menuButtons;

    public HomePage(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                Category.PHONES, phonesButton,
                Category.LAPTOPS, laptopsButton,
                Category.MONITORS, monitorsButton
        );
    }

    @Override
    public ExtendedWebElement getPageLoadedIndicator() {
        if (imageIndicator.isEmpty()) {
            logger.info("No images found, returning productGridContainer");
            // Return container to avoid IndexOutOfBounds
            return productGridContainer;
        }
        return imageIndicator.get(0);
    }

    public void click(Category item) {
        menuButtons.get(item).click();
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
        return new ProductGrid(driver, productGridContainer.getElement());
    }

    public TopMenu getTopMenu() {
        return new TopMenu(driver);
    }

    public Footer getFooter() {
        return new Footer(driver);
    }

    public ProductGrid selectCategory(Category item) {
        click(item);
        waitUntilPageIsReady();
        return new ProductGrid(driver, productGridContainer.getElement());
    }

}