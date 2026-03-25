package com.solvd.carina.webAutomation.pages.ios;

import com.solvd.carina.webAutomation.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("Initializing HomePage iOS with driver");
    }

    @Override
    public void waitUntilPageIsReady() {


        waitUtil.waitForPresenceOfElementLocated(By.id("fotcont"));
        super.waitUntilPageIsReady();

    }
}
