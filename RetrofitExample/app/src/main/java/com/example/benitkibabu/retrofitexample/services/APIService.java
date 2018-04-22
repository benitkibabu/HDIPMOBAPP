package com.example.benitkibabu.retrofitexample.services;

import com.example.benitkibabu.retrofitexample.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Benit Kibabu on 27/05/2017.
 */

public interface APIService {

    @GET("beer/random")
    Call<SearchResult> getRandomBeer(@Query("key") String apiKey, @Query("hasLabels") String hasLabel);
}
