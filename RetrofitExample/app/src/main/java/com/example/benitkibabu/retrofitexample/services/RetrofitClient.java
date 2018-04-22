package com.example.benitkibabu.retrofitexample.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Benit Kibabu on 27/05/2017.
 */

public class RetrofitClient {
    public static Retrofit getClient(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
