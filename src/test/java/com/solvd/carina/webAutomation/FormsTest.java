package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.data.model.User;
import com.solvd.carina.webAutomation.data.model.UserAccount;
import com.solvd.carina.webAutomation.data.service.UserService;
import com.solvd.carina.webAutomation.pages.common.HomePageBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class FormsTest extends BaseTest {
    private static final Logger logger =
            LoggerFactory.getLogger(FormsTest.class);

    @Test(testName = "Fill Contact Form - Task3 TC-005",
            description = "click on contact, then fills the form and sends it")
    public void verifyFillInfoInContactFormAndSend() {
        HomePageBase homePage = openHomePage();
        ContactModal contactModal = homePage.openContactModal();

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(contactModal.isModalVisible(), "Contact modal is not visible");

        User user = new UserService().getUser();

        String alertText = contactModal.fillSubmitAndAcceptAlert(user.getEmail(),
                user.getName(),
                "This is a test message");

        logger.info("alert text: {}", alertText);
        sa.assertNotNull(alertText, "Alert text should not be null");

        sa.assertAll();
    }

    @Test(testName = "Log In with wrong credentials - Task3 TC-006",
            description = "click on log in, then fills the form and click log in button")
    public void verifyLogInAttemptWithWrongCredentials() {
        HomePageBase homePage = openHomePage();
        LogInModal logInModal = homePage.openLogInModal();

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(logInModal.isModalVisible(), "Log In modal is not visible");

        UserAccount userAccount = new UserService().getUserAccount();

        logInModal.logInWith(userAccount);
        sa.assertTrue(logInModal.isAlertPresent(), "Alert should be present after submitting form");
        logInModal.acceptWrongPasswordAlert();

        sa.assertAll();
    }

    @Test(testName = "Open and Fill Contact Form",
            description = "verifies that contact form fills with info correctly")
    public void verifyFillInfoInContactForm() {
        HomePageBase homePage = openHomePage();
        ContactModal contactModal = homePage.openContactModal();

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(contactModal.isModalVisible(), "Contact modal is not visible");

        User user = new UserService().getUser();

        contactModal.fillForm(user.getEmail(), user.getName(), "This is a test message");

        sa.assertEquals(contactModal.getEmailValue(), user.getEmail(), "Email value does not match");
        sa.assertEquals(contactModal.getNameValue(), user.getName(), "Name value does not match");
        sa.assertEquals(contactModal.getMessageValue(), "This is a test message", "Message value does not match");

        sa.assertAll();
    }
}
