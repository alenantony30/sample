package com.example.moviebox.request;

import com.example.moviebox.model.MovieResults;
import com.example.moviebox.utils.Credentials;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;

    public static MovieRepository getInstance(){
        if (movieRepository == null){
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    private MovieApi movieApi;

    public MovieRepository(){
        movieApi = Servicey.createService(MovieApi.class);
    }

    public MutableLiveData<MovieResults> getMovies(String category, String page){
        MutableLiveData<MovieResults> moviesData = new MutableLiveData<>();
        movieApi.listOfNowPlaying(category, Credentials.API_KEY,"en-US",page).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call,
                                   Response<MovieResults> response) {
                if (response.isSuccessful()){
                    moviesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                moviesData.setValue(null);
            }
        });
        return moviesData;
    }


}
