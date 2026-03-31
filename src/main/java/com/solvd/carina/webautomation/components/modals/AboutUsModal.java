package com.solvd.carina.webautomation.components.modals;

import com.solvd.carina.webautomation.components.modals.common.BaseModal;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AboutUsModal extends BaseModal {

    @FindBy(css = "#videoModal .modal-content")
    private ExtendedWebElement modalContainer;

    @FindBy(id = "videoModalLabel")
    private ExtendedWebElement labelTitle;

    @FindBy(css = "#videoModal button.btn.btn-secondary")
    private ExtendedWebElement closeButton;

    @FindBy(css = "#videoModal .close")
    private ExtendedWebElement exitButton;

    public AboutUsModal(WebDriver driver, SearchContext searchContext) {
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

}
