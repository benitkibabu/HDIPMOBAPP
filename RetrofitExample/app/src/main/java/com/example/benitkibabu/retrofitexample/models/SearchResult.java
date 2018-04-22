package com.example.benitkibabu.retrofitexample.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Benit Kibabu on 27/05/2017.
 */

public class SearchResult {
    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
