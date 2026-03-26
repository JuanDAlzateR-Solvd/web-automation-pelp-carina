package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.browser.AlertHandler;
import com.solvd.carina.webAutomation.browser.AlertHandlerFactory;
import com.solvd.carina.webAutomation.components.modals.common.BaseModal;
import com.solvd.carina.webAutomation.data.model.UserAccount;
import com.solvd.carina.webAutomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LogInModal extends BaseModal {

    @FindBy(css = "#logInModal .modal-content")
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

    private final String ALERT_TEXT = "Wrong password";

    private final AlertHandler alertHandler;

    public LogInModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.alertHandler = AlertHandlerFactory.create(driver);
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

    public LogInModal logInWith(UserAccount userAccount) {
        typeUsername(userAccount.getUsername());
        typePassword(userAccount.getDecryptedPassword());
        clickLogIn();
        return this;
    }

    public void acceptWrongPasswordAlert() {
        alertHandler.acceptAlert(ALERT_TEXT, Timeouts.SHORT);
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

}


