package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.components.BaseComponent;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.function.Function;

public abstract class BaseModal extends BaseComponent {

    private static final int CLOSE_ATTEMPTS = 3;
    private static final int DISAPPEAR_TIMEOUT_SECONDS = 2;

    @FindBy(css = ".modal-backdrop")
    private ExtendedWebElement backdrop;

    protected BaseModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(getModalTitle());
    }

    /**
     * Element that indicates modal is visible
     */
    protected abstract ExtendedWebElement getModalContainer();

    /**
     * Element that contains title label of the modal
     */
    protected abstract ExtendedWebElement getModalTitle();

    /**
     * Close button of the modal
     */
    protected abstract ExtendedWebElement getCloseButton();

    /* -----------------------------
       Modal lifecycle
     ----------------------------- */

    public void waitUntilModalOpened() {
        logger.debug("Waiting for modal [{}] to open", getClass().getSimpleName());
        getModalContainer().assertElementPresent(10);
        getModalTitle().assertElementPresent(5);
    }

    public void closeModal() {
        logger.debug("Closing modal [{}]", getClass().getSimpleName());
        waitUntilModalOpened();
        try {
            waitUntilCloseButtonIsClickable();
            clickCloseButton();
        } catch (Exception e) {
            logger.warn("Standard click failed, trying JS click to close modal: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getCloseButton().getElement());
        }
        waitUntilModalClosed();
    }

    public void waitUntilModalClosed() {
        getModalContainer().waitUntilElementDisappear(15);
        // Wait for the specific modal to not have the 'show' class anymore
        // or for the backdrop to disappear.
        // In Demoblaze/Bootstrap, the 'show' class is removed when the modal is hidden.
        waitUntilBackdropDisappear();
    }

    private void waitUntilBackdropDisappear() {
        // In Demoblaze, the backdrop could have different IDs or just be .modal-backdrop
        // We use generic wait to any blocking element disappear
        try {
            if (backdrop.isElementPresent(1)) {
                logger.debug("Waiting for modal backdrop to disappear");
                backdrop.waitUntilElementDisappear(10);
            }
            // If the modal or backdrop is still there (obscuring), force cleanup
            if (isAnyModalVisible() || isBackdropPresent()) {
                logger.debug("A modal/backdrop is still present, forcing cleanup");
                cleanupBackdrops();
                // Wait for animation to finish
                waitForBackdropToDisappearAfterCleanup();
            }
        } catch (Exception e) {
            logger.warn("Error waiting for backdrop: " + e.getMessage());
        }
    }

    private void waitForBackdropToDisappearAfterCleanup() {
        logger.debug("Waiting for backdrop to disappear after cleanup");
        if (backdrop.isElementPresent(1) && backdrop.isVisible()) {
            backdrop.waitUntilElementDisappear(10);
        }
    }

    private boolean isBackdropPresent() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return document.querySelectorAll('.modal-backdrop').length > 0;");
        } catch (Exception e) {
            logger.warn("Failed to check if backdrop is present with JS: " + e.getMessage());
            return false;
        }
    }

    private boolean isAnyModalVisible() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return document.querySelectorAll('.modal.show').length > 0;");
        } catch (Exception e) {
            logger.warn("Failed to check if any modal is visible with JS: " + e.getMessage());
            return false;
        }
    }

    public void cleanupBackdrops() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());" +
                    "document.body.classList.remove('modal-open');" +
                    "document.body.style.paddingRight = '';");
        } catch (Exception e) {
            logger.warn("Failed to cleanup backdrops with JS: " + e.getMessage());
        }
    }

    public void waitUntilCloseButtonIsClickable() {
        //method could be improved to run less flakily on firefox. Especially with the LogIn Modal.
        logger.debug("Waiting for close button to be clickable");
        waitUntilModalOpened();
        logger.debug("Modal {} is opened, checking if close button is clickable", getClass().getSimpleName());
        waitUtil.waitForElementClickable(getCloseButton().getElement(), getCloseButton().getName());
    }

    public boolean isModalOpened() {
        return getModalContainer().isElementPresent();
    }

    public boolean isModalVisible() {
        return getModalTitle().isVisible();
    }

    public void clickCloseButton() {

        for (int attempt = 1; attempt <= CLOSE_ATTEMPTS; attempt++) {
            ExtendedWebElement modalContainer = getModalContainer();
            logger.debug("Closing modal. Attempt {}/{}.", attempt, CLOSE_ATTEMPTS);

            if (!modalContainer.isVisible(1)) {
                logger.debug("Modal is already closed before attempt {}.", attempt);
                return;
            }

            getCloseButton().click();

            if (modalContainer.waitUntilElementDisappear(DISAPPEAR_TIMEOUT_SECONDS)) {
                logger.info("Modal closed successfully on attempt {}.", attempt);
                return;
            }

            logger.warn("Modal is still visible after close attempt {}.", attempt);
        }

        throw new IllegalStateException("Failed to close modal after " + CLOSE_ATTEMPTS + " attempts.");
    }

}
