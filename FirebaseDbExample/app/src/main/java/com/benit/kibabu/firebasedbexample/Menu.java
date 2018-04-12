package com.benit.kibabu.firebasedbexample;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Benit Kibabu on 12/04/2018.
 */
@IgnoreExtraProperties
public class Menu {
    private String name;
    private String RName;
    private double price;

    public Menu() {
    }

    public Menu(String name, String RName, double price) {
        this.name = name;
        this.RName = RName;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRName() {
        return RName;
    }

    public void setRName(String Rname) {
        this.RName = Rname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
