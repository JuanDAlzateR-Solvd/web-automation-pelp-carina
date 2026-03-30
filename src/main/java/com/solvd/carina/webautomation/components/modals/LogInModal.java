package com.solvd.carina.webautomation.components.modals;

import com.solvd.carina.webautomation.components.modals.common.BaseModal;
import com.solvd.carina.webautomation.data.model.UserAccount;
import com.solvd.carina.webautomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LogInModal extends BaseModal {

    private static final String WRONG_PASSWORD_ALERT = "Wrong password";

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

    public LogInModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
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
        uIActions.acceptAlert(WRONG_PASSWORD_ALERT, Timeouts.SHORT);
    }

    public boolean isWrongPasswordAlertPresent() {
        return uIActions.isAlertPresent(WRONG_PASSWORD_ALERT, Timeouts.SHORT);
    }

}

