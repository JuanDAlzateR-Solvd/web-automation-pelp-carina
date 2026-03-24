package com.solvd.carina.webAutomation.utils;

import com.zebrunner.carina.crypto.Algorithm;
import com.zebrunner.carina.crypto.CryptoTool;
import com.zebrunner.carina.crypto.CryptoToolBuilder;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.config.EncryptorConfiguration;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.LoggerFactory;

public class CryptoUtils {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    private static final Dotenv dotenv = Dotenv.load();

    private static final CryptoTool cryptoTool = CryptoToolBuilder.builder()
            .chooseAlgorithm(Algorithm.AES_ECB_PKCS5_PADDING)
            .setKey(dotenv.get("CRYPTO_KEY_VALUE"))
            .build();

    private static final String CRYPTO_PATTERN = Configuration.get(EncryptorConfiguration.Parameter.CRYPTO_PATTERN).orElse("");

    public static String encrypt(String plainText) {
        return cryptoTool.encrypt(plainText);
    }

    public static String encryptWithFormat(String plainText) {
        return "{crypt:" + encrypt(plainText) + "}";
    }

    public static String decrypt(String encryptedText) {
        logger.debug("used key: " + dotenv.get("CRYPTO_KEY_VALUE"));
        return cryptoTool.decrypt(encryptedText, CRYPTO_PATTERN);
    }

}
