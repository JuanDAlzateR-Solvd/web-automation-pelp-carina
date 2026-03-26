package com.solvd.carina.webAutomation.browser;

public interface AlertHandler {

    void acceptAlert(String alertName, int timeoutInSeconds);

    String getAlertTextAndAccept(String alertName, int timeoutInSeconds);

}
