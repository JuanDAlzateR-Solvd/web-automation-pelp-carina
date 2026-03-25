package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.modals.AboutUsModal;
import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.browser.WindowManager;
import com.solvd.carina.webAutomation.mobile.ContextManager;
import com.solvd.carina.webAutomation.pages.android.ChromeApp;
import com.solvd.carina.webAutomation.pages.common.HomePageBase;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.wait.Timeouts;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Set;

public class SwitchContextTest extends BaseTest implements IMobileUtils {
    private static final Logger logger =
            LoggerFactory.getLogger(SwitchContextTest.class);
    private static final Duration NEW_TAB_TIMEOUT = Duration.ofSeconds(Timeouts.MEDIUM);

    @Test(testName = "Switch Context test",
            description = "Fills contact form, opens about us modal in new tab, then comes back to original tab and verifies contact info is still in the form ")
    public void context() {
        HomePageBase homePage = openHomePage();
        ContextManager contextManager = new ContextManager();
        WindowManager windowManager = new WindowManager(getDriver());

        SoftAssert sa = new SoftAssert();

        String email = "Tab1@Tab1.com";
        String name = "This is Tab1";
        String message = "Still Tab1";

        ContactModal contactModal = homePage.openContactModal()
                .fillForm(email, name, message);

        sa.assertTrue(contactModal.isModalVisible(), "Contact modal should be visible");

        String originalWindow = windowManager.getCurrentWindowHandle();

        HomePage homePage2 = openHomePageInNewTab(contextManager, windowManager);

        AboutUsModal aboutUsModal = homePage2.openAboutUsModal();
        sa.assertTrue(aboutUsModal.isModalVisible(), "About us modal should be visible");

        windowManager.switchToWindow(originalWindow);

        sa.assertTrue(contactModal.isModalVisible(), "Contact modal should be visible");
        sa.assertEquals(contactModal.getEmailValue(), email, "Email value does not match");
        sa.assertEquals(contactModal.getNameValue(), name, "Name value does not match");
        sa.assertEquals(contactModal.getMessageValue(), message, "Message value does not match");

        sa.assertAll();
    }

    private HomePage openHomePageInNewTab(ContextManager contextManager, WindowManager windowManager) {
        logger.info("Current context: {}", contextManager.getCurrentContext());
        logger.info("Available contexts: {}", contextManager.getContextHandles());

        Set<String> existingWindowHandles = windowManager.getWindowHandles();
        logger.info("Original window handle: {}", windowManager.getCurrentWindowHandle());

        contextManager.switchMobileContext(ContextManager.View.NATIVE);

        ChromeApp chromeApp = new ChromeApp(getDriver());
        chromeApp.clickNewTab();

        contextManager.switchMobileContext(ContextManager.View.CHROME_BROWSER);

        String newWindowHandle = windowManager.waitForNewWindowAndSwitch(existingWindowHandles, NEW_TAB_TIMEOUT);
        logger.info("Switched to new tab: {}", newWindowHandle);

        getDriver().get(R.CONFIG.get("url"));
        return new HomePage(getDriver());
    }

}
