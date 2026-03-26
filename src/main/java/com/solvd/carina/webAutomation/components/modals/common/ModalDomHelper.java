package com.solvd.carina.webAutomation.components.modals.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModalDomHelper {

    private static final Logger logger = LoggerFactory.getLogger(ModalDomHelper.class);

    private final WebDriver driver;

    public ModalDomHelper(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAnyModalVisible() {
        return executeBooleanScript(
                "return document.querySelectorAll('.modal.show').length > 0;"
        );
    }

    public boolean isAnyBackdropPresent() {
        return executeBooleanScript(
                "return document.querySelectorAll('.modal-backdrop').length > 0;"
        );
    }

    public void clickWithJs(Object element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Recovery method.
     * Do not use this as part of the standard successful close flow.
     */
    public void cleanupBackdrops() {
        logger.warn("Running modal backdrop cleanup as recovery action.");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());" +
                            "document.body.classList.remove('modal-open');" +
                            "document.body.style.removeProperty('padding-right');"
            );
        } catch (Exception e) {
            logger.warn("Failed to cleanup modal backdrops with JS: {}", e.getMessage());
        }
    }

    private boolean executeBooleanScript(String script) {
        try {
            Object result = ((JavascriptExecutor) driver).executeScript(script);
            return result instanceof Boolean && (Boolean) result;
        } catch (Exception e) {
            logger.warn("Failed to execute modal DOM check: {}", e.getMessage());
            return false;
        }
    }

}
