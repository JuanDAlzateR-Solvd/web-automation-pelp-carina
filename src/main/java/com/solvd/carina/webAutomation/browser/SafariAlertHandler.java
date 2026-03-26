package com.solvd.carina.webAutomation.browser;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SafariAlertHandler extends DefaultAlertHandler {
    //For some reason, the first safari alert takes too long to be accepted. The other alerts are accepted immediately.
    private static final Logger logger = LoggerFactory.getLogger(SafariAlertHandler.class);

    private static final int SAFARI_SHORT_TIMEOUT_SECONDS = 2;
    private static final int SAFARI_RETRY_TIMEOUT_SECONDS = 5;

    public SafariAlertHandler(WebDriver driver) {
        super(driver);
    }

    @Override
    public void acceptAlert(String alertName, int timeoutInSeconds) {
        acceptAlert3(alertName, timeoutInSeconds);
    }

    @Override
    public String getAlertTextAndAccept(String alertName, int timeoutInSeconds) {
        Alert alert = waitUtil.waitForAlert(timeoutInSeconds);
        String alertText = alert.getText();
        acceptAlert(alertName, timeoutInSeconds);
        return alertText;
    }

    public void acceptAlert2(String alertName, int timeoutInSeconds) {
        logger.info("Accepting '{}' alert using Safari strategy", alertName);

        try {
            acceptWithTimeout(alertName, SAFARI_SHORT_TIMEOUT_SECONDS);
            return;
        } catch (Exception e) {
            logger.warn("Safari short alert strategy failed for '{}': {}", alertName, e.getMessage());
        }

        try {
            acceptWithTimeout(alertName, SAFARI_RETRY_TIMEOUT_SECONDS);
            return;
        } catch (Exception e) {
            logger.warn("Safari retry alert strategy failed for '{}': {}", alertName, e.getMessage());
        }

        throw new IllegalStateException("Failed to accept Safari alert: " + alertName);
    }

    //acceptalert3 it's faster than acceptalert2
    public void acceptAlert3(String alertName, int timeoutInSeconds) {
        logger.info("Accepting '{}' alert using Safari strategy", alertName);
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: alert",
                    Map.of("action", "accept")
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to accept Safari alert: " + alertName, e);
        }
        logger.info("Safari alert '{}' accepted", alertName);
    }

    private void acceptWithTimeout(String alertName, int timeoutInSeconds) {
        long waitStart = System.currentTimeMillis();
        Alert alert = waitUtil.waitForAlert(timeoutInSeconds);
        logger.info("Safari alert '{}' found after {} ms", alertName, System.currentTimeMillis() - waitStart);

        long acceptStart = System.currentTimeMillis();
        alert.accept();
        logger.info("Safari alert '{}' accepted after {} ms", alertName, System.currentTimeMillis() - acceptStart);
    }


}
