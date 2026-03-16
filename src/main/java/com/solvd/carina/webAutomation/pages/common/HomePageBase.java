package com.solvd.carina.webAutomation.pages.common;


import com.solvd.carina.webAutomation.components.Footer;
import com.solvd.carina.webAutomation.components.ProductGrid;
import com.solvd.carina.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;

public abstract class HomePageBase extends BaseTopMenuPage {

    public HomePageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void click(HomePageCategory item);

    public abstract ProductGrid getProductGrid();

    public abstract Footer getFooter();

    public abstract ProductGrid selectCategory(HomePageCategory item);

}