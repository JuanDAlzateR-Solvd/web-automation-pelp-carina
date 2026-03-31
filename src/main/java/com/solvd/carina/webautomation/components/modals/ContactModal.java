package com.solvd.carina.webautomation.components.modals;

import com.solvd.carina.webautomation.components.modals.common.BaseModal;
import com.solvd.carina.webautomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ContactModal extends BaseModal {

    private static final String MESSAGE_SENT_ALERT = "Thanks for message";

    @FindBy(css = "#exampleModal .modal-content")
    private ExtendedWebElement modalContainer;

    @FindBy(id = "exampleModalLabel")
    private ExtendedWebElement labelTitle;

    @FindBy(id = "recipient-email")
    private ExtendedWebElement emailInput;

    @FindBy(id = "recipient-name")
    private ExtendedWebElement nameInput;

    @FindBy(id = "message-text")
    private ExtendedWebElement messageInput;

    @FindBy(css = "#exampleModal button.btn.btn-primary")
    private ExtendedWebElement sendButton;

    @FindBy(css = "#exampleModal button.btn.btn-secondary")
    private ExtendedWebElement closeButton;

    public ContactModal(WebDriver driver, SearchContext searchContext) {
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

    public ContactModal fillAndSubmitForm(String email, String name, String message) {
        fillForm(email, name, message);
        clickSend();
        return this;
    }

    public ContactModal fillForm(String email, String name, String message) {
        typeEmail(email);
        typeName(name);
        typeMessage(message);
        return this;
    }

    public String fillSubmitAndAcceptAlert(String email, String name, String message) {
        fillForm(email, name, message);
        clickSend();
        return getMessageAlertTextAndAccept();
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
        uIActions.clickUsingBestStrategy(sendButton);
    }

    public void acceptMessageAlert() {
        uIActions.acceptAlert(MESSAGE_SENT_ALERT, Timeouts.SHORT);
    }

    public String getMessageAlertText() {
        return uIActions.getAlertText(MESSAGE_SENT_ALERT, Timeouts.SHORT);
    }

    public String getMessageAlertTextAndAccept() {
        return uIActions.getAlertTextAndAccept(MESSAGE_SENT_ALERT, Timeouts.SHORT);
    }

    public boolean isMessageAlertPresent() {
        return uIActions.isAlertPresent(MESSAGE_SENT_ALERT, Timeouts.SHORT);
    }

    public void clickClose() {
        closeButton.click();
        closeButton.waitUntilElementDisappear(Timeouts.SHORT);
    }

    public String getEmailValue() {
        return getInputValue(emailInput);
    }

    public String getNameValue() {
        return getInputValue(nameInput);
    }

    public String getMessageValue() {
        return getInputValue(messageInput);
    }

}
