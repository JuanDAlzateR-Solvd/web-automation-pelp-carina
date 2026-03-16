package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.components.BaseComponent;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

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
//        waitUntilComponentIsReady();
        getModalContainer().assertElementPresent();
    }

    public void closeModal() {
        logger.debug("Closing modal [{}]", getClass().getSimpleName());
//        waitUntilComponentIsReady();
        waitUntilCloseButtonIsClickable();
        getCloseButton().click();
        waitUntilModalClosed();
        cleanupBackdrops();
    }

    public void waitUntilModalClosed() {
        ExtendedWebElement modal = getModalContainer();
        modal.waitUntilElementDisappear(15);
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
        } catch (Exception e) {
            logger.warn("Error waiting for backdrop: " + e.getMessage());
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
        for (int i = 0; i < 5; i++) {
            logger.debug("Close button is clickable {}",getCloseButton().isClickable()); //
            if (!getCloseButton().isClickable()) {
                logger.debug("Close button is not clickable, trying again");
                getCloseButton().assertElementPresent(2);
            }
        }
    }

    public boolean isModalOpened() {
        return getModalContainer().isElementPresent();
    }

    public boolean isModalVisible() {
        return getModalTitle().isVisible();
    }

}
