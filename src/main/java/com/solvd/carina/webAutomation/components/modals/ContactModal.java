package com.solvd.carina.webAutomation.components.modals;

import com.solvd.carina.webAutomation.browser.AlertHandler;
import com.solvd.carina.webAutomation.browser.AlertHandlerFactory;
import com.solvd.carina.webAutomation.components.modals.common.BaseModal;
import com.solvd.carina.webAutomation.wait.Timeouts;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class ContactModal extends BaseModal {

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

    private final AlertHandler alertHandler;

    private final String ALERT_TEXT = "Thanks for message";

    public ContactModal(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.alertHandler = AlertHandlerFactory.create(driver);
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
        return getAlertTextAndAccept();
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
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", sendButton.getElement());
        sendButton.click();
    }

    public void acceptMessageAlert() {
        alertHandler.acceptAlert(ALERT_TEXT , Timeouts.SHORT);
    }

    public String getAlertText() {
        Alert alert = waitUtil.waitForAlert();
        return alert.getText();
    }

    public String getAlertTextAndAccept() {
        return alertHandler.getAlertTextAndAccept(ALERT_TEXT, Timeouts.SHORT);
    }

    public boolean isAlertPresent() {
        logger.info("checking 'Thanks for Message' Alert Present");
        try {
            waitUtil.waitForAlert();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
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
