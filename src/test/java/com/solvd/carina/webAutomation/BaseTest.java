package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class BaseTest implements IAbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(BaseTest.class);

    protected HomePage homePage;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) {
        R.CONFIG.put("browser", browser);
        logger.info("Running test on browser: {}", browser);
    }

    public HomePage openHomePage() {
        WebDriver driver = getDriver();
        return Navigation.openHomePage(driver);
    }

}
