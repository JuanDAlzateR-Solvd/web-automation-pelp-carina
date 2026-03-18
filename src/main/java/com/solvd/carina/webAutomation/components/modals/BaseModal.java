package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.components.BaseComponent;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.function.Function;

public abstract class BaseModal extends BaseComponent {

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
        ExtendedWebElement modal = getModalContainer();
        // Check if modal container is still present
        if (modal.isElementPresent(1)) {
            logger.debug("Modal container is present, waiting for it to disappear");
            modal.waitUntilElementDisappear(15);
        }
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
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        } catch (Exception e) {
            logger.warn("Error waiting for backdrop: " + e.getMessage());
        }
    }

    private boolean isBackdropPresent() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return document.querySelectorAll('.modal-backdrop').length > 0;");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isAnyModalVisible() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return document.querySelectorAll('.modal.show').length > 0;");
        } catch (Exception e) {
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
        getCloseButton().isVisible(1);
        waitUtil.waitForElementClickable(getCloseButton().getElement(), getCloseButton().getName());

        logger.info("Close button is clickable {}",getCloseButton().isClickable()); //
        if (!getCloseButton().isClickable()) {
            logger.info("Close button is not clickable, trying again");
            getCloseButton().assertElementPresent(2);

            Function<ExtendedWebElement, Boolean> clickable = ExtendedWebElement::isClickable;

            waitUtil.waitUntilTrue(driver -> clickable.apply(getCloseButton()));

        }

    }

    public boolean isModalOpened() {
        return getModalContainer().isElementPresent();
    }

    public boolean isModalVisible() {
        return getModalTitle().isVisible();
    }

    public void clickCloseButton() {
        getCloseButton().click();
        getModalContainer().waitUntilElementDisappear(3);
        if (getModalTitle().isVisible(3)) {
            getCloseButton().click();
        }
    }

}
