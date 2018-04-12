package com.benit.kibabu.firebasedbexample;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Benit Kibabu on 12/04/2018.
 */
@IgnoreExtraProperties
public class User {
    private String UID;
    private String name;

    public User(){

    }
    public User(String UID, String name) {
        this.UID = UID;
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
