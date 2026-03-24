package com.solvd.carina.webAutomation.pages.common;


import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class NativeAppBase extends AbstractPage implements IMobileUtils {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public NativeAppBase(WebDriver driver) {
        super(driver);
    }

    public abstract void clickNewTab();

}