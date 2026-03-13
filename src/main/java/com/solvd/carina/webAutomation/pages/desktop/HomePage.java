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
import java.util.Map;

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
    protected ExtendedWebElement getPageLoadedIndicator() {

        logger.debug("Waiting for home page to load imageIndicator. Initial size: {}", imageIndicator.size());

        waitUntil(driver -> !imageIndicator.isEmpty(), 10);

        return imageIndicator.get(0);
    }

    @Override
    protected TopMenu getTopMenu() {
        return topMenu;
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
