package com.benit.kibabu.firebasedbexample;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Benit Kibabu on 12/04/2018.
 */
@IgnoreExtraProperties
public class Restaurant {
    private String name;
    private String UID;
    public Restaurant() {
    }

    public Restaurant(String name, String UID) {
        this.name = name;
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
