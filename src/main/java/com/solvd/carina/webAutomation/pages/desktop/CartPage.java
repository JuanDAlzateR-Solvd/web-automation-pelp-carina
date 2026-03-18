package com.solvd.carina.webAutomation.pages.desktop;

import com.solvd.carina.webAutomation.components.CartItemComponent;
import com.solvd.carina.webAutomation.components.TopMenu;
import com.solvd.carina.webAutomation.pages.common.BaseTopMenuPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class CartPage extends BaseTopMenuPage {

    @FindBy(id = "tbodyid")
    private ExtendedWebElement productGridContainer;

    @FindBy(id = "totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(css = "#tbodyid .success")
    private List<CartItemComponent> cartItemComponents;

    @FindBy(css = ".table-responsive")
    private ExtendedWebElement tableIndicator;

    @FindBy(css = ".navbar.navbar-toggleable-md.bg-inverse")
    private TopMenu topMenu;

    public CartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(tableIndicator);
    }

    @Override
    protected TopMenu getTopMenu() {
        return topMenu;
    }
    /* -----------------------------
        Cart items
     ----------------------------- */

    public List<CartItemComponent> getCartItemComponents() {
        waitUntilPageIsReady();
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));
        logger.debug("Getting cart item components: found {} products", cartItemComponents.size());
        return cartItemComponents;
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

        waitUntilCartSizeReduces(initialSize);
    }

    public void removeAllProductsFromCart() {
        final int maxAttempts = 20;
        int attempts = 0;

        while (!isCartEmpty()) {

            if (attempts >= maxAttempts) {
                throw new IllegalStateException(
                        "Failed to clear shopping cart after " + maxAttempts + " attempts."
                );
            }

            CartItemComponent item = getCartItemComponents().get(0);
            String productName = item.getTitle();

            logger.info("Deleting product '{}' from cart", productName);

            ExtendedWebElement rowElement = item.getRootExtendedElement();
            deleteProduct(productName);

            attempts++;
        }

        logger.info("Shopping cart successfully emptied");
    }

    /* -----------------------------
        Cart validations
     ----------------------------- */

    public boolean isProductInCart(String productName) {
        return getCartItemComponentByName(productName).isPresent();
    }

    public boolean isCartEmpty() {
        boolean empty = getCartItemComponents().isEmpty();
        logger.info("Cart empty: {}", empty);
        return empty;
    }

    public int getProductCount() {
        waitUntilPageIsReady();
        int size = getCartItemComponents().size();
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
        waitUntil(driver -> getCartItemComponents().size() != initialSize, 10);
    }

    private void waitUntilCartSizeReduces(int initialSize) {
        logger.debug("Waiting for cart size to change from {}", initialSize);
        waitUntil(driver -> getCartItemComponents().size() == (initialSize-1), 10);
    }


    public void waitUntilCartShowsProducts() {
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
