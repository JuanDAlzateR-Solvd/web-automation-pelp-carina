package com.solvd.carina.webAutomation.navigation;

import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;

public class PageNavigator {

    public static HomePage openHomePage(WebDriver driver) {

        HomePage page = new HomePage(driver);
        page.open();
        page.waitUntilPageIsReady();

        return page;
    }

}