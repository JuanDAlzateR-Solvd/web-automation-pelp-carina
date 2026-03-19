package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends BaseModal {

    @FindBy(css = "#signInModal .modal-content")
    private ExtendedWebElement modalContainer;

    @FindBy(id = "signInModalLabel")
    private ExtendedWebElement labelTitle;

    @FindBy(css = "#signInModal button.btn.btn-primary")
    private ExtendedWebElement signInButton;

    @FindBy(css = "#signInModal button.btn.btn-secondary")
    private ExtendedWebElement closeButton;

    @FindBy(id = "sign-username")
    private ExtendedWebElement usernameInput;

    @FindBy(id = "sign-password")
    private ExtendedWebElement passwordInput;

    public SignUpModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    protected ExtendedWebElement getModalContainer() {
        return modalContainer;
    }

    @Override
    protected ExtendedWebElement getModalTitle() {
        return labelTitle;
    }

    @Override
    protected ExtendedWebElement getCloseButton() {
        return closeButton;
    }

    public ExtendedWebElement getLabelTitle() {
        return labelTitle;
    }

    public void clickSignIn() {
        signInButton.click();
    }

    public void clickClose() {
        closeButton.click();
        closeButton.waitUntilElementDisappear(Timeouts.SHORT);
    }

    public void typeUsername(String username) {
        usernameInput.type(username);
    }

    public void typePassword(String password) {
        passwordInput.type(password);
    }

}
