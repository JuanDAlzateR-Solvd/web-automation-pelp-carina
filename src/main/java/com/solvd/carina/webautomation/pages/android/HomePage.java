package com.solvd.carina.webautomation.pages.android;

import com.solvd.carina.webautomation.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("Initializing HomePage ANDROID with driver");
    }

}
