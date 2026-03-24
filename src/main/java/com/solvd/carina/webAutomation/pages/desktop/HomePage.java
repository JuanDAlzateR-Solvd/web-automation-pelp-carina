package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("Initializing HomePage DESKTOP with driver");
    }

}
