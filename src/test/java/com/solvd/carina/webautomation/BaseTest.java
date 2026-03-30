package com.solvd.carina.webautomation;

import com.solvd.carina.webautomation.pages.common.HomePageBase;
import com.solvd.carina.webautomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class BaseTest implements IAbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(BaseTest.class);

    private static final Dotenv dotenv = Dotenv.load();

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("safari") String browser) {
        R.CONFIG.put("browser", browser);
        logger.info("Running test on browser: {}", browser);

        String udid = R.CONFIG.get("capabilities.udid");

        if (udid == "${DEVICE_UDID}") {
            R.CONFIG.put("capabilities.udid", dotenv.get("DEVICE_UDID"));
        }
    }

    public HomePageBase openHomePage() {
        HomePageBase homePageBase = initPage(getDriver(), HomePageBase.class);
        homePageBase.open();
        return homePageBase;
    }

}
