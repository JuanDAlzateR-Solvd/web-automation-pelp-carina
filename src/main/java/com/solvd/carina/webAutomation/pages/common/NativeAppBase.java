package com.solvd.carina.webAutomation.pages.common;

import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NativeAppBase extends AbstractPage implements IMobileUtils {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public NativeAppBase(WebDriver driver) {
        super(driver);
    }

    public abstract void clickNewTab();

}