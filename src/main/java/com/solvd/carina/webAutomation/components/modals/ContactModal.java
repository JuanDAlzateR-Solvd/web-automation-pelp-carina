package com.solvd.carina.webAutomation.components.modals;

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

    public ContactModal(WebDriver driver, SearchContext searchContext) {
        super(driver,searchContext);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", sendButton.getElement());
    }

    public void acceptMessageAlert() {
        logger.info("accepting 'Thanks for message' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public String getAlertText() {
        Alert alert = waitUtil.waitForAlert();
        return alert.getText();
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
        closeButton.waitUntilElementDisappear(5);
    }

}
