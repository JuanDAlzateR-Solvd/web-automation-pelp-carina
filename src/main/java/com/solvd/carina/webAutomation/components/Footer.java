package com.solvd.carina.webAutomation.components;

import com.solvd.carina.webAutomation.utils.StringUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Footer extends BaseComponent {

    @FindBy(css = "#fotcont div[class*='col-sm-3'] .caption")
    private ExtendedWebElement contactInfo;

    @FindBy(css = "#fotcont img")
    private ExtendedWebElement imageLocator;

    public Footer(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setUiLoadedMarker(imageLocator);
    }

    public String[] getContactInfoText() {
        logger.debug(contactInfo.getText());
        return Arrays.stream(contactInfo.getText().split("\n"))
                .map(String::trim)
                .toArray(String[]::new);
    }

    public FooterInfo getInfo() {
        Map<InfoItem, String> infoMap = getFooterInfoMap();

        String address = infoMap.getOrDefault(InfoItem.ADDRESS, "");
        String phone = infoMap.getOrDefault(InfoItem.PHONE, "");
        String email = infoMap.getOrDefault(InfoItem.EMAIL, "");

        return new FooterInfo(address, phone, email);
    }

    private Map<InfoItem, String> getFooterInfoMap() {
        return Arrays.stream(contactInfo.getText().split("\n"))
                .map(String::trim)
                .filter(line -> line.contains(":"))
                .map(line -> line.split(":", 2))
                .collect(Collectors.toMap(
                        arr -> Arrays.stream(InfoItem.values())
                                .filter(i -> i.getLabel().equals(arr[0].trim()))
                                .findFirst()
                                .orElse(null),
                        arr -> arr[1].trim())
                );
    }

    public boolean isVisibleInScreen() {
        return uIActions.isInViewport(contactInfo, "contact info");
    }

    private boolean isAddressValid(String address) {
        return !address.isBlank();
    }

    private boolean isPhoneValid(String phone) {
        return StringUtils.isValidPhone(phone);
    }

    private boolean isEmailValid(String email) {
        return StringUtils.isValidEmail(email);
    }

    public boolean isFooterInfoValid() {
        FooterInfo info = getInfo();
        String address = info.getAddress();
        String phone = info.getPhone();
        String email = info.getEmail();

        boolean validAddress = isAddressValid(address);
        boolean validPhone = isPhoneValid(phone);
        boolean validEmail = isEmailValid(email);

        if (!validAddress) {
            logger.error("Invalid Address: {}", address);
        }
        if (!validPhone) {
            logger.error("Invalid Phone: {}", phone);
        }
        if (!validEmail) {
            logger.error("Invalid Email: {}", email);
        }
        return validAddress && validPhone && validEmail;
    }

    public void ensureVisible() {
        logger.info("Scrolling to contact info");
        uIActions.scrollTo(contactInfo);
        //Use the scroll method from HomePage?
    }

    public enum InfoItem {
        ADDRESS("Address"),
        PHONE("Phone"),
        EMAIL("Email");

        private final String label;

        InfoItem(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class FooterInfo {
        private String address;
        private String phone;
        private String email;

        public FooterInfo(String address, String phone, String email) {
            this.address = address;
            this.phone = phone;
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

}
