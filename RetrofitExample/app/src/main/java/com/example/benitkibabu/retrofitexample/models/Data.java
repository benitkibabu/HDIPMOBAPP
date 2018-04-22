package com.example.benitkibabu.retrofitexample.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Benit Kibabu on 27/05/2017.
 */

public class Data {
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("labels")
    Label label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
