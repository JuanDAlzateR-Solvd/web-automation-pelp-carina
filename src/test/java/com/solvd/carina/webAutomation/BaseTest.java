package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.components.modals.SignUpModal;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.flows.ShoppingFlow;
import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.solvd.carina.webAutomation.pages.common.BaseTopMenuPage;
import com.solvd.carina.webAutomation.pages.desktop.CartPage;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.pages.desktop.ProductPage;
import com.zebrunner.carina.core.AbstractTest;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class BaseTest extends AbstractTest {//implements IAbstractTest
    private static final Logger logger =
            LoggerFactory.getLogger(BaseTest.class);

    protected HomePage homePage;
//    protected Navigation navigation;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) {
        R.CONFIG.put("browser", browser);
        logger.info("Running test on browser: {}", browser);

//        homePage = Navigation.openHomePage(getDriver());
//        navigation = homePage.getNavigation();
    }

    public HomePage openHomePage() {
        WebDriver driver = getDriver();
        return Navigation.openHomePage(driver);
    }

}
