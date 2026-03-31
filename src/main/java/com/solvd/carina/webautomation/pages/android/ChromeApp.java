package com.solvd.carina.webautomation.pages.android;

import com.solvd.carina.webautomation.pages.common.NativeAppBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = NativeAppBase.class)
public class ChromeApp extends NativeAppBase {

    @FindBy(id = "com.android.chrome:id/optional_toolbar_button")
    private ExtendedWebElement newTabButton;

    public ChromeApp(WebDriver driver) {
        super(driver);
        logger.info("Initializing ChromeApp View");
    }

    public void clickNewTab() {
        newTabButton.click();
    }

}
