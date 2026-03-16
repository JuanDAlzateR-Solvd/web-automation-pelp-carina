package com.solvd.carina.webAutomation.pages.android;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.HomePageBase;
import com.solvd.carina.webAutomation.pages.common.HomePageCategory;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

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

    private final Map<HomePageCategory, ExtendedWebElement> menuButtons;

    public HomePage(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                HomePageCategory.PHONES, phonesButton,
                HomePageCategory.LAPTOPS, laptopsButton,
                HomePageCategory.MONITORS, monitorsButton
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

    @Override
    public void click(HomePageCategory item) {
        menuButtons.get(item).click();
    }

    //Test flow methods
    @Override
    public ProductGrid getProductGrid() {
        return productGrid;
    }

    @Override
    public Footer getFooter() {
        return footer;
    }

    @Override
    public ProductGrid selectCategory(HomePageCategory item) {
        click(item);
        waitUntilPageIsReady();
        return productGrid;
    }

}
