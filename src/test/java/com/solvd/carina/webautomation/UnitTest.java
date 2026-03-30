package com.solvd.carina.webautomation;

import com.solvd.carina.webautomation.utils.CryptoUtils;
import com.zebrunner.carina.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitTest {
    private static final Logger logger =
            LoggerFactory.getLogger(UnitTest.class);

    @Test(testName = "Decrypt password test",
            description = "checks that password is decrypted correctly")
    public void testPasswordDecryption() {
        String encrypted = R.TESTDATA.get("credentials.password");
        Assert.assertEquals(CryptoUtils.decrypt(encrypted), "password");
    }

    @Test(testName = "Encrypt and Decrypt test",
            description = "checks that a message when is encrypted, it will get decrypted correctly")
    public void testEncryption() {
        String message = "Hello World!";
        logger.info("Message to encrypt: " + message);

        String encrypted = CryptoUtils.encryptWithFormat(message);
        logger.info("Encrypted message: " + encrypted);

        String decrypted = CryptoUtils.decrypt(encrypted);
        logger.info("Decrypted message: " + decrypted);

        Assert.assertEquals(decrypted, message);
    }

}
