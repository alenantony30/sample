package com.example.moviebox;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCaller {


    @GET("/3/movie/{category}")
    Call<MovieResults> listOfNowPlaying(

            @Path("category")String category
            ,@Query("api_key")String apiKey
            ,@Query("language")String language
            ,@Query("page") String page
    );






}


