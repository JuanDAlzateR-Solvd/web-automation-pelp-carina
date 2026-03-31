package com.solvd.carina.webautomation.pages.common;

public enum HomePageCategory {
    PHONES("Category Phones"),
    LAPTOPS("Category Laptops"),
    MONITORS("Category Monitors");

    private final String name;

    HomePageCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
