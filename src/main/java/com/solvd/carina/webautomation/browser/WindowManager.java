package com.solvd.carina.webautomation.browser;

import com.zebrunner.carina.webdriver.DriverHelper;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class WindowManager extends DriverHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public WindowManager(WebDriver driver) {
        super(driver);
    }

    public String getCurrentWindowHandle() {
        return getDriver().getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return Collections.unmodifiableSet(getDriver().getWindowHandles());
    }

    public int getWindowCount() {
        return getWindowHandles().size();
    }

    public void switchToWindow(String windowHandle) {
        validateWindowHandle(windowHandle);

        LOGGER.info("Switching to window: {}", windowHandle);
        getDriver().switchTo().window(windowHandle);
    }

    /**
     * Waits for a new window/tab that did not exist before, switches to it, and returns its handle.
     */
    public String waitForNewWindowAndSwitch(Set<String> existingWindowHandles, Duration timeout) {
        validateWindowHandles(existingWindowHandles);
        validateTimeout(timeout);

        AtomicReference<String> newWindowHandleRef = new AtomicReference<>();

        boolean isNewWindowFound = waitUntil(driver -> {
            String newWindowHandle = findNewWindowHandle(existingWindowHandles);

            if (newWindowHandle == null) {
                return false;
            }

            newWindowHandleRef.set(newWindowHandle);
            return true;
        }, timeout.getSeconds());

        String newWindowHandle = newWindowHandleRef.get();

        if (!isNewWindowFound || newWindowHandle == null) {
            throw new NoSuchWindowException(
                    "No new window/tab was found within timeout. Existing handles: " + existingWindowHandles
            );
        }

        switchToWindow(newWindowHandle);
        return newWindowHandle;
    }

    private String findNewWindowHandle(Set<String> existingWindowHandles) {
        return getDriver().getWindowHandles()
                .stream()
                .filter(handle -> !existingWindowHandles.contains(handle))
                .findFirst()
                .orElse(null);
    }

    /**
     * Convenience method for the common case where there was only one original window.
     */
    public String waitForNewWindowAndSwitch(String originalWindowHandle, Duration timeout) {
        validateWindowHandle(originalWindowHandle);
        return waitForNewWindowAndSwitch(Set.of(originalWindowHandle), timeout);
    }

    public void switchToWindowByTitleContains(String partialTitle, Duration timeout) {
        validatePartialTitle(partialTitle);
        validateTimeout(timeout);

        String originalWindowHandle = getDriver().getWindowHandle();
        AtomicReference<String> matchingWindowHandleRef = new AtomicReference<>();

        boolean isWindowFound = waitUntil(driver -> {
            String matchingWindowHandle = findWindowHandleByTitleContains(partialTitle);

            if (matchingWindowHandle == null) {
                return false;
            }

            matchingWindowHandleRef.set(matchingWindowHandle);
            return true;
        }, timeout.getSeconds());

        String matchingWindowHandle = matchingWindowHandleRef.get();

        if (!isWindowFound || matchingWindowHandle == null) {
            getDriver().switchTo().window(originalWindowHandle);
            throw new NoSuchWindowException(
                    "No window found with title containing: " + partialTitle
            );
        }

        getDriver().switchTo().window(matchingWindowHandle);
        LOGGER.info("Switched to window with title containing '{}', handle: {}",
                partialTitle, matchingWindowHandle);
    }

    private String findWindowHandleByTitleContains(String partialTitle) {
        for (String handle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
            String title = getDriver().getTitle();

            if (title != null && title.contains(partialTitle)) {
                return handle;
            }
        }
        return null;
    }

    public void closeCurrentWindowAndSwitchBack(String targetWindowHandle) {
        validateWindowHandle(targetWindowHandle);

        String currentHandle = getDriver().getWindowHandle();
        LOGGER.info("Closing current window: {}", currentHandle);

        getDriver().close();

        LOGGER.info("Switching back to window: {}", targetWindowHandle);
        getDriver().switchTo().window(targetWindowHandle);
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

    private void validatePartialTitle(String partialTitle) {
        if (partialTitle == null || partialTitle.isBlank()) {
            throw new IllegalArgumentException("Partial title must not be null or blank.");
        }
    }

}