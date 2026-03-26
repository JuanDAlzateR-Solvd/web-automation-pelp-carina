package com.solvd.carina.webAutomation.browser;

import com.solvd.carina.webAutomation.wait.WaitUtil;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class WindowManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WebDriver driver;
    private final WaitUtil waitUtil;

    public WindowManager(WebDriver driver) {
        this.driver = Objects.requireNonNull(driver, "Driver must not be null.");
        this.waitUtil = new WaitUtil(driver);
    }

    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return Collections.unmodifiableSet(driver.getWindowHandles());
    }

    public int getWindowCount() {
        return getWindowHandles().size();
    }

    public void switchToWindow(String windowHandle) {
        validateWindowHandle(windowHandle);

        LOGGER.info("Switching to window: {}", windowHandle);
        driver.switchTo().window(windowHandle);
    }

    /**
     * Waits for a new window/tab that did not exist before, switches to it, and returns its handle.
     */
    public String waitForNewWindowAndSwitch(Set<String> existingWindowHandles, Duration timeout) {
        validateWindowHandles(existingWindowHandles);
        validateTimeout(timeout);

        try {
            String newWindowHandle = waitUtil.waitUntil(webDriver -> Objects.requireNonNull(webDriver.getWindowHandles()
                    .stream()
                    .filter(handle -> !existingWindowHandles.contains(handle))
                    .findFirst()
                    .orElse(null)), (int) timeout.getSeconds());

            switchToWindow(newWindowHandle);
            return newWindowHandle;
        } catch (TimeoutException e) {
            throw new NoSuchWindowException(
                    "No new window/tab was found within timeout. Existing handles: " + existingWindowHandles, e
            );
        }
    }

    /**
     * Convenience method for the common case where there was only one original window.
     */
    public String waitForNewWindowAndSwitch(String originalWindowHandle, Duration timeout) {
        validateWindowHandle(originalWindowHandle);
        return waitForNewWindowAndSwitch(Set.of(originalWindowHandle), timeout);
    }

    public void switchToWindowByTitleContains(String partialTitle, Duration timeout) {
        if (partialTitle == null || partialTitle.isBlank()) {
            throw new IllegalArgumentException("Partial title must not be null or blank.");
        }
        validateTimeout(timeout);

        try {
            String matchingWindowHandle = waitUtil.waitUntil(webDriver -> {
                for (String handle : webDriver.getWindowHandles()) {
                    webDriver.switchTo().window(handle);
                    String title = webDriver.getTitle();

                    if (title != null && title.contains(partialTitle)) {
                        return handle;
                    }
                }
                return null;
            }, (int) timeout.getSeconds());

            LOGGER.info("Switched to window with title containing '{}' , handle: {}", partialTitle, matchingWindowHandle);
        } catch (TimeoutException e) {
            throw new NoSuchWindowException(
                    "No window found with title containing: " + partialTitle, e
            );
        }
    }

    public void closeCurrentWindowAndSwitchBack(String targetWindowHandle) {
        validateWindowHandle(targetWindowHandle);

        String currentHandle = driver.getWindowHandle();
        LOGGER.info("Closing current window: {}", currentHandle);

        driver.close();

        LOGGER.info("Switching back to window: {}", targetWindowHandle);
        driver.switchTo().window(targetWindowHandle);
    }

    private void validateWindowHandle(String windowHandle) {
        if (windowHandle == null || windowHandle.isBlank()) {
            throw new IllegalArgumentException("Window handle must not be null or blank.");
        }
    }

    private void validateWindowHandles(Set<String> windowHandles) {
        if (windowHandles == null || windowHandles.isEmpty()) {
            throw new IllegalArgumentException("Existing window handles must not be null or empty.");
        }
    }

    private void validateTimeout(Duration timeout) {
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("Timeout must be greater than zero.");
        }
    }

}