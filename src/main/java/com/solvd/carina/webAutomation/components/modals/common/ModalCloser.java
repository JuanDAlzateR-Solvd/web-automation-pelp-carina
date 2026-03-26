package com.solvd.carina.webAutomation.components.modals.common;

import com.solvd.carina.webAutomation.wait.WaitUtil;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ModalCloser {

    private static final Logger logger = LoggerFactory.getLogger(ModalCloser.class);

    private static final int CLOSE_ATTEMPTS = 3;
    private static final int CLOSE_STATE_TIMEOUT_SECONDS = 10;
    private static final int PER_ATTEMPT_CLOSE_TIMEOUT_SECONDS = 3;
    private static final int RECOVERY_TIMEOUT_SECONDS = 5;

    private final BaseModal modal;
    private final WaitUtil waitUtil;
    private final ModalDomHelper modalDomHelper;

    ModalCloser(BaseModal modal, WaitUtil waitUtil, ModalDomHelper modalDomHelper) {
        this.modal = modal;
        this.waitUtil = waitUtil;
        this.modalDomHelper = modalDomHelper;
    }

    void close() {
        logger.debug("Closing modal [{}]", modal.getClass().getSimpleName());

        modal.waitUntilModalOpened();

        boolean closed = tryCloseNormally();

        if (!closed) {
            logger.warn("Normal close failed for modal [{}]. Trying JS fallback.",
                    modal.getClass().getSimpleName());
            closed = tryCloseWithJs();
        }

        if (!closed) {
            logger.error("Modal [{}] could not be closed via UI. Applying recovery strategy.",
                    modal.getClass().getSimpleName());
            modalDomHelper.cleanupBackdrops();
            closed = waitUntilClosedStateSafely(RECOVERY_TIMEOUT_SECONDS);
        }

        if (!closed) {
            throw new IllegalStateException("Failed to close modal: " + modal.getClass().getSimpleName());
        }

        waitUntilFullyClosed();
    }

    void recover() {
        logger.warn("Recovering from stuck modal [{}]", modal.getClass().getSimpleName());
        modalDomHelper.cleanupBackdrops();
        waitUntilFullyClosed();
    }

    void waitUntilFullyClosed() {
        logger.debug("Waiting until modal [{}] is fully closed", modal.getClass().getSimpleName());

        try {
            waitUtil.waitUntilTrue(driver -> modal.isModalFullyClosed(), CLOSE_STATE_TIMEOUT_SECONDS);
        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "Modal did not reach fully closed state: " + modal.getClass().getSimpleName(), e);
        }
    }

    private boolean tryCloseNormally() {
        for (int attempt = 1; attempt <= CLOSE_ATTEMPTS; attempt++) {
            logger.debug("Trying normal close for modal [{}]. Attempt {}/{}",
                    modal.getClass().getSimpleName(), attempt, CLOSE_ATTEMPTS);

            if (modal.isModalFullyClosed()) {
                logger.debug("Modal [{}] is already closed before attempt {}.",
                        modal.getClass().getSimpleName(), attempt);
                return true;
            }

            try {
                modal.getCloseButton().click();

                if (waitUntilClosedStateSafely(PER_ATTEMPT_CLOSE_TIMEOUT_SECONDS)) {
                    logger.info("Modal [{}] closed successfully on attempt {}.",
                            modal.getClass().getSimpleName(), attempt);
                    return true;
                }

                logger.warn("Modal [{}] is still open after close attempt {}.",
                        modal.getClass().getSimpleName(), attempt);
            } catch (ElementNotInteractableException | StaleElementReferenceException e) {
                logger.warn("Normal close attempt {} failed for modal [{}]: {}",
                        attempt, modal.getClass().getSimpleName(), e.getMessage());
            }
        }

        return false;
    }

    private boolean tryCloseWithJs() {
        try {
            modalDomHelper.clickWithJs(modal.getCloseButton().getElement());
            return waitUntilClosedStateSafely(PER_ATTEMPT_CLOSE_TIMEOUT_SECONDS);
        } catch (Exception e) {
            logger.warn("JS fallback close failed for modal [{}]: {}",
                    modal.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    private boolean waitUntilClosedStateSafely(int timeoutInSeconds) {
        return waitUtil.waitUntilTrueSafely(driver -> modal.isModalFullyClosed(), timeoutInSeconds);
    }

}
