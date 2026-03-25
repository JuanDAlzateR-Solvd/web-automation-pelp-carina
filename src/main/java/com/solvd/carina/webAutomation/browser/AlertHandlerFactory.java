package com.solvd.carina.webAutomation.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

public final class AlertHandlerFactory {

    private AlertHandlerFactory() {
    }

    public static AlertHandler create(WebDriver driver) {
        String browserName = getBrowserName(driver);

        if (browserName.contains("safari")) {
            return new SafariAlertHandler(driver);
        }

        return new DefaultAlertHandler(driver);
    }

    private static String getBrowserName(WebDriver driver) {
        if (driver instanceof HasCapabilities capabilitiesDriver) {
            Capabilities capabilities = capabilitiesDriver.getCapabilities();
            Object browserName = capabilities.getCapability("browserName");

            if (browserName != null) {
                return browserName.toString().toLowerCase();
            }
        }

        return "";
    }
}
