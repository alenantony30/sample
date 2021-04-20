package com.example.moviebox.request;


import com.example.moviebox.model.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {


    @GET("/3/movie/{category}")
    Call<MovieResults> listOfNowPlaying(

            @Path("category")String category
            ,@Query("api_key")String apiKey
            ,@Query("language")String language
            ,@Query("page") String page
    );






}


