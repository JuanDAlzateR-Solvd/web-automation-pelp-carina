package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class BaseModal extends BaseComponent {

    @FindBy(css = ".modal-backdrop")
    private ExtendedWebElement backdrop;

    protected BaseModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
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
        waitUntilComponentIsReady();
        getModalContainer().assertElementPresent();
    }

    public void closeModal() {

        logger.debug("Closing modal [{}]", getClass().getSimpleName());

        getCloseButton().click();

        waitUntilModalClosed();
    }

    public void waitUntilModalClosed() {

        ExtendedWebElement modal = getModalContainer();

        if (modal.isElementPresent(2)) {
            modal.waitUntilElementDisappear(10);
        }

        waitUntilBackdropDisappear();
    }

    private void waitUntilBackdropDisappear() {

        if (backdrop.isElementPresent(2)) {

            logger.debug("Waiting for modal backdrop to disappear");

            backdrop.waitUntilElementDisappear(10);
        }
    }



    public boolean isModalOpened() {
        return getModalContainer().isElementPresent();
    }

    public boolean isModalVisible() {
        return getModalTitle().isVisible();
    }

}
