package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.components.CartItemComponent;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.BasePage;
import com.solvd.carina.webAutomation.pages.common.BaseTopMenuPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class CartPage extends BaseTopMenuPage {

    @FindBy(id = "tbodyid")
    private ExtendedWebElement productGridContainer;

    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(css = "#tbodyid .success")
    private List<ExtendedWebElement> tableRows;

    @FindBy(css = ".table-responsive")
    private ExtendedWebElement tableIndicator;

    @FindBy(css = ".navbar.navbar-toggleable-md.bg-inverse")
    private ExtendedWebElement topMenuContainer;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ExtendedWebElement getPageLoadedIndicator() {
        return tableIndicator;
    }

    @Override
    protected WebElement getTopMenuContainer() {
        return topMenuContainer.getElement();
    }
    /* -----------------------------
        Cart items
     ----------------------------- */

    public List<CartItemComponent> getCartItemComponents() {

        waitUntilPageIsReady();

        logger.debug("Building cart item components. Found {} rows", tableRows.size());

        return tableRows.stream()
                .map(row -> new CartItemComponent(driver, row.getElement()))
                .toList();
    }

    public Optional<CartItemComponent> getCartItemComponentByName(String productName) {
        waitUntilPageIsReady();

        List<CartItemComponent> cartItems = getCartItemComponents();

        logger.info("Getting cart item components. Found {} items", cartItems.size());

        return cartItems.stream()
                .filter(ci -> ci.getTitle().equalsIgnoreCase(productName))
                .findFirst();
    }

    /* -----------------------------
        Cart actions
     ----------------------------- */

    public void deleteProduct(String productName) {

        logger.info("Deleting product [{}] from cart", productName);

        CartItemComponent item = getCartItemComponentByName(productName)
                .orElseThrow(() ->
                        new RuntimeException("Product not found in cart: " + productName));

        int initialSize = getProductCount();

        item.deleteProduct();

        waitUntilCartSizeChanges(initialSize);
    }

    public void emptyShoppingCart() {

        logger.info("Emptying shopping cart");

        int initialSize = getProductCount();
        logger.info("Initial cart size: {}", initialSize);

        for (int i = initialSize; i > 0; i--) {
            deleteProduct(getCartItemComponents().get(0).getTitle());
            if(i>1){
                waitUntilCartShowsProducts();
            }else {
                waitUntilPageIsReady();
            }
        }

        //Final Empty cart, if some products were not deleted.
        while (!isCartEmpty()) {
            CartItemComponent item = getCartItemComponents().get(0);
            deleteProduct(item.getTitle());
            waitUntilPageIsReady();
        }
    }

    /* -----------------------------
        Cart validations
     ----------------------------- */

    public boolean containsProduct(String productName) {
        return getCartItemComponentByName(productName).isPresent();
    }

    public boolean isCartEmpty() {

        waitUntilPageIsReady();

        boolean empty = tableRows.isEmpty();

        logger.info("Cart empty: {}", empty);

        return empty;
    }

    public int getProductCount() {

        waitUntilPageIsReady();

        int size = tableRows.size();

        logger.info("Cart contains {} products", size);

        return size;
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    /* -----------------------------
        Waits
     ----------------------------- */

    private void waitUntilCartSizeChanges(int initialSize) {

        logger.debug("Waiting for cart size to change from {}", initialSize);

        waitUntil(driver -> tableRows.size() != initialSize, 10);
    }

    public void waitUntilCartShowsProducts() {
        waitUntilPageIsReady();
        waitUntilCartSizeChanges(0);
    }

    /* -----------------------------
        Utilities
     ----------------------------- */

    public void printProductsInCart() {

        logger.info("Products in cart:");

        getCartItemComponents()
                .forEach(item -> logger.info(item.getTitle()));
    }

    /* -----------------------------
        Components
     ----------------------------- */

}
