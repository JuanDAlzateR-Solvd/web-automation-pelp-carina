package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.wait.WaitUtil;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent extends AbstractUIObject {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WaitUtil waitUtil;

    protected BaseComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        this.waitUtil = new WaitUtil(getDriver());
    }

    public boolean isComponentVisible() {
        return getUiLoadedMarker().isVisible();
    }

    protected boolean isInViewport(ExtendedWebElement element, String elementName) {
        logger.info("Checking if element [{}] is in viewport", elementName);

        String script = """
                    var elem = arguments[0],
                        box = elem.getBoundingClientRect();
                    return (
                        box.top >= 0 &&
                        box.left >= 0 &&
                        box.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
                        box.right <= (window.innerWidth || document.documentElement.clientWidth)
                    );
                """;

        boolean isVisible = Boolean.TRUE.equals(
                (Boolean) ((JavascriptExecutor) driver).executeScript(script, element.getElement())
        );

        logger.info("Element [{}] is {}in viewport", elementName, isVisible ? "" : "not ");
        return isVisible;
    }

}
