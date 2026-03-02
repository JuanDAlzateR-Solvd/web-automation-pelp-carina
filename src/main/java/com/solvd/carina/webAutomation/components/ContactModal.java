package com.solvd.carina.webAutomation.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ContactModal extends BaseModal {

    @FindBy(id = "exampleModal")
    private ExtendedWebElement modalContainer;

    @FindBy(css = "#exampleModalLabel")
    private ExtendedWebElement labelTitle;

    @FindBy(css = "#recipient-email")
    private ExtendedWebElement emailInput;

    @FindBy(css = "#recipient-name")
    private ExtendedWebElement nameInput;

    @FindBy(css = "#message-text")
    private ExtendedWebElement messageInput;

    @FindBy(css = "#exampleModal button.btn.btn-primary")
    private ExtendedWebElement sendButton;

    @FindBy(css = "#exampleModal button.btn.btn-secondary")
    private ExtendedWebElement closeButton;

    public ContactModal(WebDriver driver) {
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

    public void acceptMessageAlert() {
        logger.info("accepting 'Thanks for message' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public ContactModal fillAndSubmitForm(String email, String name, String message) {
        typeEmail(email);
        typeName(name);
        typeMessage(message);
        clickSend();
        return this;
    }

    public void typeEmail(String email) {
        emailInput.type(email);
    }

    public void typeName(String name) {
        nameInput.type(name);
    }

    public void typeMessage(String message) {
        messageInput.type(message);
    }

    public void clickSend() {
        sendButton.click();
    }

    public void clickClose() {
       closeButton.click();
        closeButton.waitUntilElementDisappear(5);
//       closeButton.isVisible();
    }

}
