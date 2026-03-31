package com.solvd.carina.webautomation.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

public final class BrowserDetector {

    private static final String SAFARI_BROWSER_NAME = "safari";

    private BrowserDetector() {
    }

    public static boolean isSafari(WebDriver driver) {
        return getBrowserName(driver).contains(SAFARI_BROWSER_NAME);
    }

    public static String getBrowserName(WebDriver driver) {
        if (driver instanceof HasCapabilities capabilitiesDriver) {
            Capabilities capabilities = capabilitiesDriver.getCapabilities();
            Object browserName = capabilities.getCapability("browserName");

            if (browserName != null) {
                return browserName.toString().trim().toLowerCase();
            }
        }

        return "";
    }

}

