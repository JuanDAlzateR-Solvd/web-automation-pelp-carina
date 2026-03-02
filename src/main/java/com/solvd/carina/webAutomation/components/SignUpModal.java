package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends BaseModal {

    @FindBy(id = "signInModal")
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

    public SignUpModal(WebDriver driver) {
        super(driver,driver);
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
        closeButton.waitUntilElementDisappear(5);
//        closeButton.isVisible();
    }
    public void typeUsername(String username) {
        usernameInput.type(username);
    }
    public void typePassword(String password) {
        passwordInput.type(password);
    }

}
