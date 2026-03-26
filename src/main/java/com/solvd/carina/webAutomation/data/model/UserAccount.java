package com.solvd.carina.webAutomation.data.model;

import com.solvd.carina.webAutomation.utils.CryptoUtils;

public class UserAccount {
    public String username;
    public String password;

    public UserAccount(String name, String email) {
        this.username = name;
        this.password = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedPassword() {
        return CryptoUtils.decrypt(password);
    }

}
