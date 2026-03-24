package com.solvd.carina.webAutomation.components.modals.common;

import com.solvd.carina.webAutomation.components.BaseComponent;
import com.solvd.carina.webAutomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.*;

public abstract class BaseModal extends BaseComponent {

    private final ModalDomHelper modalDomHelper;
    private final ModalCloser modalCloser;

    protected BaseModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.modalDomHelper = new ModalDomHelper(driver);
        this.modalCloser = new ModalCloser(this, waitUtil, modalDomHelper);
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
        getModalContainer().assertElementPresent(Timeouts.MEDIUM);
        getModalTitle().assertElementPresent(Timeouts.SHORT);
        waitUntilModalReadyForClosing();
    }

    protected void waitUntilModalReadyForClosing() {
        logger.debug("Waiting for modal [{}] to be ready for closing", getClass().getSimpleName());

        waitVisible(getModalContainer());
        waitVisible(getModalTitle());
        waitClickable(getCloseButton());
    }

    public void closeModal() {
        modalCloser.close();
    }

    /**
     * Explicit recovery hook.
     * Use only when the UI gets stuck and the test cannot continue otherwise.
     */
    public void recoverFromStuckModal() {
        modalCloser.recover();
    }

    protected void waitUntilModalFullyClosed() {
        modalCloser.waitUntilFullyClosed();
    }

    private boolean isModalContainerVisible() {
        try {
            return getModalContainer().isVisible(1);
        } catch (Exception e) {
            logger.debug("Could not determine modal container visibility for [{}]: {}",
                    getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    /**
     * Single source of truth for the "closed" state.
     */
    protected boolean isModalFullyClosed() {
        return !isModalContainerVisible()
                && !modalDomHelper.isAnyModalVisible()
                && !modalDomHelper.isAnyBackdropPresent();
    }

    public boolean isModalOpened() {
        return getModalContainer().isElementPresent();
    }

    public boolean isModalVisible() {
        return getModalTitle().isVisible();
    }

    public boolean isModalOpened(long timeout) {
        return getModalContainer().isElementPresent(timeout);
    }

    public boolean isModalVisible(long timeout) {
        return getModalTitle().isVisible(timeout);
    }

    private void waitVisible(ExtendedWebElement element) {
        waitUtil.waitForElementVisible(element.getElement(), element.getName());
    }

    private void waitClickable(ExtendedWebElement element) {
        waitUtil.waitForElementClickable(element.getElement(), element.getName());
    }

    protected String getInputValue(ExtendedWebElement element) {
        return element.getElement().getDomProperty("value");
    }

}
