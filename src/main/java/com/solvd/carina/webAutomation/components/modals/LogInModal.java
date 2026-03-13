package com.solvd.carina.webAutomation.components.modals;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LogInModal extends BaseModal {

    @FindBy(id = "logInModal")
    private ExtendedWebElement modalContainer;

    @FindBy(id = "logInModalLabel")
    private ExtendedWebElement labelTitle;

    @FindBy(css = "#logInModal button.btn.btn-primary")
    private ExtendedWebElement logInButton;

    @FindBy(css = "#logInModal button.btn.btn-secondary")
    private ExtendedWebElement closeButton;

    @FindBy(id = "loginusername")
    private ExtendedWebElement usernameInput;

    @FindBy(id = "loginpassword")
    private ExtendedWebElement passwordInput;

    public LogInModal(WebDriver driver, SearchContext searchContext) {
        super(driver,searchContext);
    }

    @Override
    protected ExtendedWebElement getComponentLoadedIndicator() {
        return labelTitle;
    }

    @Override
    protected ExtendedWebElement getModalContainer() {
        return modalContainer;
    }

    @Override
    protected ExtendedWebElement getCloseButton() {
        return closeButton;
    }

    @Override
    protected ExtendedWebElement getModalTitle() {
        return labelTitle;
    }

    public ExtendedWebElement getLabelTitle() {
        return labelTitle;
    }

    public void clickLogIn() {
        logInButton.click();
    }

    public void typeUsername(String username) {
        usernameInput.type(username);
    }

    public void typePassword(String password) {
        passwordInput.type(password);
    }

    public LogInModal logInWith(String username, String password) {
        typeUsername(username);
        typePassword(password);
        clickLogIn();
        return this;
    }

    public void acceptWrongPasswordAlert() {
        logger.info("accepting 'Wrong password' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public boolean isAlertPresent() {
        logger.info("checking 'Log in' Alert Present");
        try {
            waitUtil.waitForAlert();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public void closeModal() {
        //This method needs to be improved to reduce flakiness
        logger.debug("Closing modal [{}]", getClass().getSimpleName());
        pause(1);
        waitUntilCloseButtonIsClickable();
        getCloseButton().click();
        waitUntilModalClosed();
        cleanupBackdrops();
    }

}
