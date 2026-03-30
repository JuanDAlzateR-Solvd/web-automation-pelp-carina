package com.solvd.carina.webautomation.components.modals.common;

import com.solvd.carina.webautomation.actions.UIActions;
import com.solvd.carina.webautomation.wait.Timeouts;
import com.solvd.carina.webautomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseModal extends AbstractUIObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WaitUtil waitUtil;
    protected final UIActions uIActions;

    protected BaseModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.waitUtil = new WaitUtil(getDriver());
        this.uIActions = new UIActions(getDriver());
        setUiLoadedMarker(getModalTitle());
    }

    protected abstract ExtendedWebElement getModalContainer();

    protected abstract ExtendedWebElement getModalTitle();

    protected abstract ExtendedWebElement getCloseButton();

    public void waitUntilModalOpened() {
        logger.debug("Waiting for modal [{}] to open", getClass().getSimpleName());
        getModalContainer().assertElementPresent(Timeouts.MEDIUM);
        waitUntilModalReady();
    }

    protected void waitUntilModalReady() {
        logger.debug("Waiting for modal [{}] to become ready", getClass().getSimpleName());
        waitUtil.waitForElementVisible(getModalContainer().getElement(), getModalContainer().getName());
        waitUtil.waitForElementVisible(getModalTitle().getElement(), getModalTitle().getName());
        waitUtil.waitForElementClickable(getCloseButton().getElement(), getCloseButton().getName());
    }

    public void closeModal() {
        logger.debug("Closing modal [{}]", getClass().getSimpleName());
        waitUntilModalOpened();
        uIActions.clickUsingBestStrategy(getCloseButton());
        waitUntilModalClosed();
    }

    protected void waitUntilModalClosed() {
        boolean isClosed = waitUtil.waitUntilTrueSafely(
                driver -> !getModalContainer().isVisible(1),
                Timeouts.MEDIUM
        );

        if (!isClosed) {
            throw new IllegalStateException("Failed to close modal: " + getClass().getSimpleName());
        }
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

    protected String getInputValue(ExtendedWebElement element) {
        return element.getElement().getDomProperty("value");
    }
}
