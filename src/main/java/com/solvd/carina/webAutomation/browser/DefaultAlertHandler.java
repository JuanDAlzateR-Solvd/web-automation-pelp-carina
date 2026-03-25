package com.solvd.carina.webAutomation.browser;

import com.solvd.carina.webAutomation.wait.WaitUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAlertHandler implements AlertHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAlertHandler.class);

    protected final WebDriver driver;
    protected final WaitUtil waitUtil;

    public DefaultAlertHandler(WebDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitUtil(driver);
    }

    @Override
    public void acceptAlert(String alertName, int timeoutInSeconds) {
        logger.info("Accepting '{}' alert using default strategy", alertName);

        Alert alert = waitUtil.waitForAlert();

        alert.accept();
    }
}
