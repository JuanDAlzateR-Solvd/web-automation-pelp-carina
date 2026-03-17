package com.solvd.carina.webAutomation;

import com.solvd.carina.webAutomation.components.modals.ContactModal;
import com.solvd.carina.webAutomation.components.modals.LogInModal;
import com.solvd.carina.webAutomation.data.model.User;
import com.solvd.carina.webAutomation.data.model.UserAccount;
import com.solvd.carina.webAutomation.data.service.UserService;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import com.solvd.carina.webAutomation.utils.CryptoUtils;
import com.zebrunner.carina.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UnitTest {
    private static final Logger logger =
            LoggerFactory.getLogger(UnitTest.class);

    @Test(testName = "Decrypt password test",
            description = "checks that password is decrypted correctly")
    public void testEncryption() {
        String encrypted =R.TESTDATA.get("credentials.password");
        Assert.assertEquals(CryptoUtils.decrypt(encrypted), "password");
    }

}
