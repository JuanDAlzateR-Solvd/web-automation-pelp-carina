package com.solvd.carina.webAutomation.data.service;

import com.solvd.carina.webAutomation.data.model.User;
import com.zebrunner.carina.utils.R;

public class UserService {

    public User getUser() {
        return new User(
                R.TESTDATA.get("valid.name"),
                R.TESTDATA.get("valid.email")
        );
    }

}
