package com.example.benitkibabu.retrofitexample.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Benit Kibabu on 27/05/2017.
 */

public class Label {
    @SerializedName("icon")
    String icon;
    @SerializedName("medium")
    String medium;
    @SerializedName("large")
    String large;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}
