package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.flows.Navigation;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.zebrunner.carina.core.AbstractTest;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class FormsTest extends BaseTest {//implements IAbstractTest
    private static final Logger logger =
            LoggerFactory.getLogger(FormsTest.class);

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("firefox") String browser) {
        R.CONFIG.put("browser", browser);
        logger.info("Running test on browser: {}", browser);
    }

    @Test(testName = "Fill Contact Form - Task3 TC-005",
            description = "click on contact, then fills the form and sends it")
    public void verifyFillInfoInContactFormAndSend() {
         HomePage homePage = openHomePage();
        ContactModal contactModal = homePage.openContactModal();

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(contactModal.isModalVisible(), "Contact modal is not visible");

        contactModal.fillAndSubmitForm("example@email.com",
                "Example Name",
                "This is a test message");

        logger.info("alert {}", contactModal.getAlertText());

        sa.assertTrue(contactModal.isAlertPresent(), "Alert should be present after submitting form");
        logger.info("finished checking alert");

        contactModal.acceptMessageAlert();

        sa.assertAll();
    }

    @Test(testName = "Log In with wrong credentials - Task3 TC-006",
            description = "click on log in, then fills the form and click log in button")
    public void verifyLogInAttemptWithWrongCredentials() {
        HomePage homePage = openHomePage();
        LogInModal logInModal = homePage.openLogInModal();

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(logInModal.isModalVisible(), "Log In modal is not visible");

        logInModal.logInWith("example@email.com", "Example Password");
        sa.assertTrue(logInModal.isAlertPresent(), "Alert should be present after submitting form");
        logInModal.acceptWrongPasswordAlert();

        sa.assertAll();
    }

}
