package com.solvd.carina.webautomation.data.service;

import com.solvd.carina.webautomation.data.model.User;
import com.solvd.carina.webautomation.data.model.UserAccount;
import com.zebrunner.carina.utils.R;

public class UserService {

    public User getUser() {
        return new User(
                R.TESTDATA.get("valid.name"),
                R.TESTDATA.get("valid.email")
        );
    }

    public UserAccount getUserAccount() {
        return new UserAccount(
                R.TESTDATA.get("credentials.user"),
                R.TESTDATA.get("credentials.password")
        );
    }

}
